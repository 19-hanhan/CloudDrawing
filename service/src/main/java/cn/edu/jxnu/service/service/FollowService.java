package cn.edu.jxnu.service.service;

import cn.edu.jxnu.service.entity.User;
import cn.edu.jxnu.service.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FollowService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    public void follow(int userId, int targetId) {
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String followKey = RedisKeyUtil.getFollowKey(userId);
                String fansKey = RedisKeyUtil.getFansKey(targetId);

                operations.multi();

                operations.opsForZSet().add(followKey, targetId, System.currentTimeMillis());
                operations.opsForZSet().add(fansKey, userId, System.currentTimeMillis());

                return operations.exec();
            }
        });
    }

    public void unfollow(int userId, int targetId) {
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String followKey = RedisKeyUtil.getFollowKey(userId);
                String fansKey = RedisKeyUtil.getFansKey(targetId);

                operations.multi();

                operations.opsForZSet().remove(followKey, targetId);
                operations.opsForZSet().remove(fansKey, userId);

                return operations.exec();
            }
        });
    }

    // 查询关注的实体数量
    public long findfollowCount(int userId) {
        String followKey = RedisKeyUtil.getFollowKey(userId);
        return redisTemplate.opsForZSet().zCard(followKey);
    }

    // 查询实体的粉丝数量
    public long findFansCount(int targetId) {
        String fansKey = RedisKeyUtil.getFansKey(targetId);
        return redisTemplate.opsForZSet().zCard(fansKey);
    }

    // 查询当前用户是否已关注该实体
    public boolean hasfollow(int userId, int targetId) {
        String followKey = RedisKeyUtil.getFollowKey(userId);
        return redisTemplate.opsForZSet().score(followKey, targetId) != null;
    }

    // 查询某用户关注的人
    public List<Map<String, Object>> findfollow(int userId, int offset, int limit) {
        String followeeKey = RedisKeyUtil.getFollowKey(userId);
        Set<Integer> targetIds = redisTemplate.opsForZSet().reverseRange(followeeKey, offset, offset + limit - 1);

        if (targetIds == null) {
            return null;
        }

        List<Map<String, Object>> list = new ArrayList<>();
        for (Integer targetId : targetIds) {
            Map<String, Object> map = new HashMap<>();
            User user = userService.findUserById(targetId);
            map.put("user", user);
            Double score = redisTemplate.opsForZSet().score(followeeKey, targetId);
            map.put("followTime", new Date(score.longValue()));
            list.add(map);
        }

        return list;
    }

    // 查询某用户的粉丝
    public List<Map<String, Object>> findFans(int userId, int offset, int limit) {
        String followerKey = RedisKeyUtil.getFansKey(userId);
        Set<Integer> targetIds = redisTemplate.opsForZSet().reverseRange(followerKey, offset, offset + limit - 1);

        if (targetIds == null) {
            return null;
        }

        List<Map<String, Object>> list = new ArrayList<>();
        for (Integer targetId : targetIds) {
            Map<String, Object> map = new HashMap<>();
            User user = userService.findUserById(targetId);
            map.put("user", user);
            Double score = redisTemplate.opsForZSet().score(followerKey, targetId);
            map.put("fansTime", new Date(score.longValue()));
            list.add(map);
        }

        return list;
    }

}
