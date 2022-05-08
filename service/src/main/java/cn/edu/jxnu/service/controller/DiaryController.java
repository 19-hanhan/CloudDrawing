package cn.edu.jxnu.service.controller;

import cn.edu.jxnu.service.entity.Diary;
import cn.edu.jxnu.service.entity.Event;
import cn.edu.jxnu.service.entity.User;
import cn.edu.jxnu.service.event.EventProducer;
import cn.edu.jxnu.service.service.DiaryService;
import cn.edu.jxnu.service.service.ElasticsearchService;
import cn.edu.jxnu.service.service.LikeService;
import cn.edu.jxnu.service.service.UserService;
import cn.edu.jxnu.service.util.HostHolder;
import cn.edu.jxnu.service.util.RedisKeyUtil;
import cn.edu.jxnu.service.util.ServerConstant;
import cn.edu.jxnu.service.util.ServerUtil;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/diary")
public class DiaryController implements ServerConstant {

    private static final Logger logger = LoggerFactory.getLogger(DiaryController.class);

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${service.path.upload}")
    private String uploadPath;

    @Value("${service.path.domain}")
    private String domain;

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Value("${caffeine.posts.max-size}")
    private int maxSize;

    @Value("${caffeine.posts.expire-seconds}")
    private int expireSeconds;

    // 日记列表缓存
    private LoadingCache<String, List<Diary>> diaryListCache;

    // 日记总数缓存
    private LoadingCache<Integer, Integer> diaryRowsCache;

    @PostConstruct
    public void init() {
        // 初始化日记列表缓存
        diaryListCache = Caffeine.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expireSeconds, TimeUnit.SECONDS)
                .build(new CacheLoader<String, List<Diary>>() {
                    @Override
                    public @Nullable List<Diary> load(@NonNull String s) throws Exception {
                        if (StringUtils.isBlank(s)) {
                            throw new IllegalArgumentException("初始化日记缓存错误!");
                        }

                        String[] params = s.split(":");
                        if (params == null || params.length != 2) {
                            throw new IllegalArgumentException("初始化日记缓存错误!");
                        }
                        int current = Integer.parseInt(params[0]);
                        int limit = Integer.parseInt(params[1]);

                        return diaryService.findDiarys(0, current * limit, limit, 1);
                    }
                });

        // 初始化日记总数缓存
        diaryRowsCache = Caffeine.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expireSeconds, TimeUnit.SECONDS)
                .build(new CacheLoader<Integer, Integer>() {
                    @Override
                    public @Nullable Integer load(@NonNull Integer integer) throws Exception {
                        return diaryService.findDiaryCount(integer);
                    }
                });
    }

    @GetMapping("/all_diary")
    @ResponseBody
    public String getAllDiary(@RequestParam(value = "userId", defaultValue = "0") int userId, @RequestParam("pageNumber") int current, @RequestParam("pageSize") int limit, @RequestParam(defaultValue = "0") int orderMode) {
        List<Diary> list = null;
        int totalRows = 0;
        if (userId == 0 && orderMode == 1) {
            list = diaryListCache.get(current + ":" + limit);
        } else {
            list = diaryService.findDiarys(userId, current * limit, limit, orderMode);
        }


        totalRows = diaryRowsCache.get(userId);


        List<Map<String, Object>> diaries = new ArrayList<>();

        if (list != null) {
            for (Diary diary : list) {
                Map<String, Object> map = new HashMap<>();
                diary.setLikeNum((int) likeService.getDiaryLikeCount(diary.getId()));
                map.put("diary", diary);
                User user = userService.findUserById(diary.getUserId());
                map.put("user", user);
                List<String> imgs = diaryService.findImages(diary.getId());
                if (imgs != null) {
                    for (int i = 0; i < imgs.size(); i++) {
                        imgs.set(i, domain + "/diary/photo/" + imgs.get(i));
                    }
                }
                map.put("imgs", imgs);
                diaries.add(map);
            }
        }

        Map<String, Object> result = new HashMap<>();
        int totalPages = (totalRows + (limit - 1)) / limit;
        result.put("totalPages", totalPages);
        result.put("diaries", diaries);
        return ServerUtil.getJSONString(200, null, result);
    }

    @GetMapping("/one_diary")
    @ResponseBody
    public String getOneDiary(int diaryId) {
        Diary diary = diaryService.findDiaryById(diaryId);
        if (diary == null) {
            throw new IllegalArgumentException("日记号不存在!");
        }
        Map<String, Object> result = new HashMap<>();
        result.put("diary", diary);
        result.put("user", userService.findUserById(diary.getUserId()));

        List<String> imgs = diaryService.findImages(diary.getId());
        if (imgs != null) {
            for (int i = 0; i < imgs.size(); i++) {
                imgs.set(i, domain + "/diary/photo/" + imgs.get(i));
            }
        }
        result.put("imgs", imgs);

        return ServerUtil.getJSONString(200, "获取日记成功!", result);
    }

    @GetMapping("/photo/{fileName}")
    public void getDiaryPhoto(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        // 服务器存放路径
        fileName = uploadPath + "/" + fileName;
        String suffix = fileName.substring(fileName.lastIndexOf("."));

        response.setContentType("image/" + suffix);
        try (FileInputStream fis = new FileInputStream(fileName); OutputStream os = response.getOutputStream();) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer);
            }
        } catch (IOException e) {
            logger.error("读取日记图片失败: " + e.getMessage());
        }
    }

    @PostMapping("/click_like")
    @ResponseBody
    public String like(int diaryId) {
        likeService.likeDiary(hostHolder.getUser().getId(), diaryId);

        int isMember = likeService.findDiaryLikeStatus(hostHolder.getUser().getId(), diaryId);

        // 计算帖子分数
        String redisKey = RedisKeyUtil.getDiaryScoreKey();
        redisTemplate.opsForSet().add(redisKey, diaryId);

        if (isMember == 1) {
            return ServerUtil.getJSONString(200, "点赞成功!");
        } else {
            return ServerUtil.getJSONString(100, "取消点赞!");
        }
    }

    @PostMapping("/delete_diary")
    @ResponseBody
    public String deleteDiary(int diaryId) {

        Diary diary = diaryService.findDiaryById(diaryId);

        if (diary == null || diary.getExist() == 0) {
            throw new IllegalArgumentException("日记不存在!");
        }

        if (diary.getUserId() != hostHolder.getUser().getId()) {
            throw new IllegalArgumentException("无法删除其他人的日记!");
        }
        diaryService.deleteDiary(diaryId);

        User user = hostHolder.getUser();

        // 触发删帖事件
        Event event = new Event().setTopic(TOPIC_DELETE)
                .setUserId(user.getId())
                .setEntityId(diaryId);
        eventProducer.fireEvent(event);

        return ServerUtil.getJSONString(200, "删除日记成功");
    }

    @PostMapping("/add_diary")
    @ResponseBody
    public String addDiary(String content, MultipartFile[] imgs) {

        User user = hostHolder.getUser();

        Diary diary = new Diary();
        diary.setUserId(user.getId());
        diary.setExist(1);
        diary.setCreateTime(new Date());
        diary.setContent(content);
        diaryService.addDiary(diary);

        if (imgs != null) {
            for (int i = 0; i < imgs.length; i++) {
                MultipartFile image = imgs[i];
                String fileName = image.getOriginalFilename();
                String suffix = fileName.substring(fileName.lastIndexOf("."));
                if (StringUtils.isBlank(suffix)) {
                    return ServerUtil.getJSONString(405, "文件格式不正确!");
                }

                fileName = ServerUtil.generateUUID() + suffix;
                File dest = new File(uploadPath + "/" + fileName);
                try {
                    image.transferTo(dest);
                    diaryService.addImage(diary.getId(), fileName);
                } catch (IOException e) {
                    logger.error("上传日记附属图片失败: " + e.getMessage());
                    throw new RuntimeException("上传日记附属图片失败,服务器发生异常!", e);
                }
            }

        }

        // 触发发帖事件
        Event event = new Event().setTopic(TOPIC_PUBLISH)
                .setUserId(user.getId())
                .setEntityId(diary.getId());
        eventProducer.fireEvent(event);

        // 计算帖子分数
        String redisKey = RedisKeyUtil.getDiaryScoreKey();
        redisTemplate.opsForSet().add(redisKey, diary.getId());

        return ServerUtil.getJSONString(200, "发布成功!");
    }

    @GetMapping("/search_diary")
    @ResponseBody
    public String searchDiary(@RequestParam(value = "userId", defaultValue = "0") int userId, @RequestParam("pageNumber") int current, @RequestParam("pageSize") int limit, String keyword) {
        if (StringUtils.isBlank(keyword)) {
            throw new IllegalArgumentException("搜索关键字不能为空");
        }
        Page<Diary> list = elasticsearchService.searchDiscussPost(userId, keyword, current, limit);

        List<Map<String, Object>> diaries = new ArrayList<>();

        if (list != null) {
            for (Diary diary : list) {
                Map<String, Object> map = new HashMap<>();
                diary.setLikeNum((int) likeService.getDiaryLikeCount(diary.getId()));
                map.put("diary", diary);
                User user = userService.findUserById(diary.getUserId());
                map.put("user", user);
                List<String> imgs = diaryService.findImages(diary.getId());
                if (imgs != null) {
                    for (int i = 0; i < imgs.size(); i++) {
                        imgs.set(i, domain + "/diary/photo/" + imgs.get(i));
                    }
                }
                map.put("imgs", imgs);
                diaries.add(map);
            }
        }

        Map<String, Object> result = new HashMap<>();
        int totalPages = (int) (((list == null ? 0 : list.getTotalElements()) + (limit - 1)) / limit);
        result.put("totalPages", totalPages);
        result.put("diaries", diaries);

        // 增加搜索记录
        elasticsearchService.countSearchKeyword(keyword);

        return ServerUtil.getJSONString(200, null, result);
    }

    @GetMapping("/hot_keyword")
    @ResponseBody
    public String getHotSearchKeyword() {
        Map<String, Object> hotKeyword = elasticsearchService.getHotSearchKeyword();

        Map<String, Object> result = new HashMap<>();
        result.put("hotKeyword", hotKeyword);

        return ServerUtil.getJSONString(200, "获取热搜关键字成功!", result);
    }
}
