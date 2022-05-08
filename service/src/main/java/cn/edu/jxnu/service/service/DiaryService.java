package cn.edu.jxnu.service.service;

import cn.edu.jxnu.service.dao.DiaryMapper;
import cn.edu.jxnu.service.entity.Diary;
import cn.edu.jxnu.service.util.RedisKeyUtil;
import cn.edu.jxnu.service.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class DiaryService {

    @Autowired
    private DiaryMapper diaryMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Autowired
    private RedisTemplate redisTemplate;

    public List<Diary> findDiarys(int userId, int offset, int limit, int orderMode) {
        return diaryMapper.selectDiarys(userId, offset, limit, orderMode);
    }

    public int findDiaryCount(int userId) {
        return diaryMapper.selectDiaryCount(userId);
    }

    public int deleteDiary(int diaryId) {
        int rows = diaryMapper.updateExist(diaryId, 0);
        String redisKey = RedisKeyUtil.getDiaryPhotoKey(diaryId);
        redisTemplate.delete(redisKey);
        return rows;
    }

    public int addDiary(Diary diary) {
        if (diary == null) {
            throw new IllegalArgumentException("添加帖子参数不能为空!");
        }

        // 过滤敏感词
        diary.setContent(sensitiveFilter.filter(diary.getContent()));
        // 转义HTML标签
        diary.setContent(HtmlUtils.htmlEscape(diary.getContent()));

        return diaryMapper.insertDiary(diary);
    }

    public Diary findDiaryById(int id) {
        return diaryMapper.selectDiaryById(id);
    }

    public int updateScore(int id, double score) {
        return diaryMapper.updateScore(id, score);
    }

    public int updateCommentCount(int id, int count) {
        return diaryMapper.updateCommentCount(id, count);
    }

    public void addImage(int diaryId, String fileName) {
        String redisKey = RedisKeyUtil.getDiaryPhotoKey(diaryId);
        redisTemplate.opsForList().leftPush(redisKey, fileName);
    }

    public List<String> findImages(int diaryId) {
        String redisKey = RedisKeyUtil.getDiaryPhotoKey(diaryId);
        List<String> list = redisTemplate.opsForList().range(redisKey, 0, -1);
        return list;
    }

}
