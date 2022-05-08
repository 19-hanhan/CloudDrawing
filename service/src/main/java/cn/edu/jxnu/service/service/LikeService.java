package cn.edu.jxnu.service.service;

import cn.edu.jxnu.service.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private RedisTemplate redisTemplate;

    // 查询某评论获得的点赞
    public long getCommentLikeCount(int commentId) {
        String commentLikeKey = RedisKeyUtil.getLikeCommentKey(commentId);
        return redisTemplate.opsForSet().size(commentLikeKey);
    }

    // 查询某日记获得的点赞
    public long getDiaryLikeCount(int diaryId) {
        String diaryLikeKey = RedisKeyUtil.getLikeDiaryKey(diaryId);
        return redisTemplate.opsForSet().size(diaryLikeKey);
    }

    // 点赞日记
    public void likeDiary(int userId, int diaryId) {
        int code = 0;
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String diaryLikeKey = RedisKeyUtil.getLikeDiaryKey(diaryId);
                boolean isMember = redisTemplate.opsForSet().isMember(diaryLikeKey, userId);

                operations.multi();
                if (isMember) {
                    operations.opsForSet().remove(diaryLikeKey, userId);
                } else {
                    operations.opsForSet().add(diaryLikeKey, userId);
                }
                return operations.exec();
            }
        });
    }

    // 点赞评论
    public void likeComment(int userId, int commentId) {
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String commentLikeKey = RedisKeyUtil.getLikeCommentKey(commentId);
                boolean isMember = redisTemplate.opsForSet().isMember(commentLikeKey, userId);

                operations.multi();
                if (isMember) {
                    operations.opsForSet().remove(commentLikeKey, userId);
                } else {
                    operations.opsForSet().add(commentLikeKey, userId);
                }
                return operations.exec();
            }
        });
    }

    // 查询某人对日记的点赞状态
    public int findDiaryLikeStatus(int userId, int diaryId) {
        String diaryLikeKey = RedisKeyUtil.getLikeDiaryKey(diaryId);
        return redisTemplate.opsForSet().isMember(diaryLikeKey, userId) ? 1 : 0;
    }

    // 查询某人对评论的点赞状态
    public int findCommentLikeStatus(int userId, int diaryId) {
        String commentLikeKey = RedisKeyUtil.getLikeDiaryKey(diaryId);
        return redisTemplate.opsForSet().isMember(commentLikeKey, userId) ? 1 : 0;
    }

}

