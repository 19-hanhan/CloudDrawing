package cn.edu.jxnu.service.service;

import cn.edu.jxnu.service.dao.UserMapper;
import cn.edu.jxnu.service.entity.LoginToken;
import cn.edu.jxnu.service.entity.User;
import cn.edu.jxnu.service.util.RedisKeyUtil;
import cn.edu.jxnu.service.util.ServerConstant;
import cn.edu.jxnu.service.util.ServerUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserService implements ServerConstant {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    public User findUserById(int id) {
//        return userMapper.selectById(id);
        User user = getCache(id);
        if (user == null) {
            user = initCache(id);
        }
        return user;
    }

    public User findUserByEmail(String email) {
        return userMapper.selectByEmail(email);
    }

    public LoginToken findLoginToken(String token) {
        //        return loginTokenMapper.selectByToken(token);
        String redisKey = RedisKeyUtil.getTokenKey(token);
        return (LoginToken) redisTemplate.opsForValue().get(redisKey);
    }

    public Map<String, Object> loginByEmail(String email, String password, long expiredSeconds) {
        Map<String, Object> map = new HashMap<>();

        if (StringUtils.isBlank(email)) {
            map.put("usernameMsg", "邮箱不能为空!");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("passwordMsg", "密码不能为空!");
            return map;
        }

        User user = userMapper.selectByEmail(email);
        if (user == null) {
            map.put("usernameMsg", "该账号不存在!");
            return map;
        }

        password = ServerUtil.md5(password + user.getSalt());
        if (!user.getPassword().equals(password)) {
            map.put("passwordMsg", "密码不正确!");
            return map;
        }

        LoginToken loginToken = new LoginToken();
        loginToken.setUserId(user.getId());
        loginToken.setToken(ServerUtil.generateUUID());
        loginToken.setStatus(1);
        loginToken.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));

        String redisKey = RedisKeyUtil.getTokenKey(loginToken.getToken());
        redisTemplate.opsForValue().set(redisKey, loginToken);

        map.put("token", loginToken.getToken());
        return map;
    }

    public void logout(String token) {
        String redisKey = RedisKeyUtil.getTokenKey(token);
        LoginToken loginToken = (LoginToken) redisTemplate.opsForValue().get(redisKey);
        loginToken.setStatus(0);
        redisTemplate.opsForValue().set(redisKey, loginToken);
    }

    public int updateAvatar(int userId, String headerUrl) {
        int rows = userMapper.updateAvatar(userId, headerUrl);
        clearCache(userId);
        return rows;
    }

    public int updatePassword(int userId, String password) {
        int rows = userMapper.updatePassword(userId, password);
        clearCache(userId);
        return rows;
    }

    public int updateNickname(int userId, String nickname) {
        int rows = userMapper.updateNickname(userId, nickname);
        clearCache(userId);
        return rows;
    }

    public int delectUser(int id) {
        int rows = userMapper.updateExist(id, 0);
        clearCache(id);
        return rows;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(int userId) {
        User user = this.findUserById(userId);
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return AUTHORITY_USER;
            }
        });
        return list;
    }

    public int addUser(User user) {
        return userMapper.insertUser(user);
    }

    public List<String> findUserPhoto(int userId) {
        String redisKey = RedisKeyUtil.getUserPhotoKey(userId);
        Set set = redisTemplate.opsForZSet().range(redisKey, 0, -1);
        List<String> list = new ArrayList<>();
        set.forEach(it -> list.add((String) it));
        return list;
    }

    // 1.优先从缓存中取值
    private User getCache(int userId) {
        String redisKey = RedisKeyUtil.getUserKey(userId);
        return (User) redisTemplate.opsForValue().get(redisKey);
    }

    // 2.取不到值的时候初始化缓存数据
    private User initCache(int userId) {
        User user = userMapper.selectById(userId);
        String redisKey = RedisKeyUtil.getUserKey(userId);
        redisTemplate.opsForValue().set(redisKey, user, 3600, TimeUnit.SECONDS);
        return user;
    }

    // 3.数据变更时清除缓存数据
    private void clearCache(int userId) {
        String redisKey = RedisKeyUtil.getUserKey(userId);
        redisTemplate.delete(redisKey);
    }

}
