package cn.edu.jxnu.service.controller;

import cn.edu.jxnu.service.entity.Comment;
import cn.edu.jxnu.service.entity.Diary;
import cn.edu.jxnu.service.entity.Event;
import cn.edu.jxnu.service.entity.User;
import cn.edu.jxnu.service.event.EventProducer;
import cn.edu.jxnu.service.service.CommentService;
import cn.edu.jxnu.service.service.DiaryService;
import cn.edu.jxnu.service.service.LikeService;
import cn.edu.jxnu.service.service.UserService;
import cn.edu.jxnu.service.util.HostHolder;
import cn.edu.jxnu.service.util.RedisKeyUtil;
import cn.edu.jxnu.service.util.ServerConstant;
import cn.edu.jxnu.service.util.ServerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/comment")
@ResponseBody
public class CommentController implements ServerConstant {

    @Autowired
    private CommentService commentService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DiaryService diaryService;


    @GetMapping("/get_comment")
    public String getComment(int diaryId) {

        List<Comment> commentList = commentService.findCommentByDiaryId(diaryId);

        List<Map<String, Object>> comments = new ArrayList<>();

        if (commentList != null) {
            for (Comment comment : commentList) {
                Map<String, Object> map = new HashMap<>();
                comment.setLikeNum((int) likeService.getCommentLikeCount(comment.getId()));
                map.put("comment", comment);
                map.put("user", userService.findUserById(comment.getUserId()));
                comments.add(map);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("comments", comments);

        return ServerUtil.getJSONString(200, "获取成功", result);
    }

    @PostMapping("/delete_comment")
    public String deleteComment(int commentId) {

        Comment comment = commentService.findCommentById(commentId);

        if (comment == null || comment.getExist() == 0) {
            throw new IllegalArgumentException("评论不存在!");
        }

        if (comment.getUserId() != hostHolder.getUser().getId()) {
            throw new IllegalArgumentException("无法删除其他人的评论!");
        }

        commentService.deleteComment(commentId);

        // 计算帖子分数
        String redisKey = RedisKeyUtil.getDiaryScoreKey();
        redisTemplate.opsForSet().add(redisKey, comment.getDiaryId());

        return ServerUtil.getJSONString(200, "删除评论成功!");
    }

    @PostMapping("/add_comment")
    public String addComment(int diaryId, String comment, int parentId) {

        Diary diary = diaryService.findDiaryById(diaryId);

        if (diary == null) {
            throw new IllegalArgumentException("日记号不存在!");
        }

        Comment commentObj = new Comment();
        commentObj.setDiaryId(diaryId);
        commentObj.setUserId(hostHolder.getUser().getId());
        commentObj.setParentId(parentId);
        commentObj.setContent(comment);
        commentObj.setCommentTime(new Date());
        commentObj.setExist(1);
        commentObj.setLikeNum(0);
        commentService.addComment(commentObj);

        User target = null;

        if (parentId == 0) {

            target = userService.findUserById(diaryService.findDiaryById(diaryId).getUserId());

        } else {
            Comment comment1 = commentService.findCommentById(parentId);

            if (comment1 == null) {
                throw new IllegalArgumentException("评论的对象不存在了!");
            }

            target = userService.findUserById(comment1.getUserId());
        }

        if (target == null) {
            throw new IllegalArgumentException("评论的用户不存在!");
        }

        // 添加评论事件
        Event event = new Event().setTopic(TOPIC_COMMENT)
                .setUserId(hostHolder.getUser().getId())
                .setEntityId(diaryId)
                .setEntityUserId(target.getId())
                .setData("content", comment);
        eventProducer.fireEvent(event);

        // 计算帖子分数
        String redisKey = RedisKeyUtil.getDiaryScoreKey();
        redisTemplate.opsForSet().add(redisKey, diaryId);

        Map<String, Object> result = new HashMap<>();
        result.put("comment", commentObj);

        return ServerUtil.getJSONString(200, "添加评论成功!", result);
    }

}
