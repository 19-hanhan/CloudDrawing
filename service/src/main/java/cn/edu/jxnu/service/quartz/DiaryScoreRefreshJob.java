package cn.edu.jxnu.service.quartz;

import cn.edu.jxnu.service.entity.Diary;
import cn.edu.jxnu.service.service.DiaryService;
import cn.edu.jxnu.service.service.ElasticsearchService;
import cn.edu.jxnu.service.service.LikeService;
import cn.edu.jxnu.service.util.RedisKeyUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DiaryScoreRefreshJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(DiaryScoreRefreshJob.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private ElasticsearchService elasticsearchService;

    private static final Date epoch;

    static {
        try {
            epoch = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-09-01 00:00:00");
        } catch (ParseException e) {
            throw new RuntimeException("初始化云绘纪元失败!");
        }
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String redisKey = RedisKeyUtil.getDiaryScoreKey();
        BoundSetOperations operations = redisTemplate.boundSetOps(redisKey);

        if (operations.size() == 0) {
            logger.info("[任务取消] 没有需要刷新分数的日记!");
            return;
        }

        logger.info("[任务开始] 正在刷新日记分数: size= " + operations.size());
        while (operations.size() > 0) {
            this.refresh((Integer) operations.pop());
        }
        logger.info("[任务结束] 日记分数刷新完毕!");

    }

    private void refresh(int diaryId) {

        Diary diary = diaryService.findDiaryById(diaryId);
        if (diary == null) {
            logger.error("日记不存在: id = " + diaryId);
            return;
        }

        int commentCount = diary.getCommentNum();
        long likeCount = likeService.getDiaryLikeCount(diaryId);

        // 计算权重
        double w = commentCount * 10 + likeCount * 2;
        double score = Math.log10(Math.max(w, 1)) + (diary.getCreateTime().getTime() - epoch.getTime()) / (1000 * 3600 * 24);
        diaryService.updateScore(diaryId, score);
        diary.setHot(score);

        // 同步搜索数据
        elasticsearchService.saveDiary(diary);
    }
}
