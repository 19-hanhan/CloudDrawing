package cn.edu.jxnu.service;

import cn.edu.jxnu.service.entity.User;
import cn.edu.jxnu.service.util.RedisKeyUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ServiceApplication.class)
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testInsertKey() {
        User user = new User();
        redisTemplate.opsForValue().set("test:student", user);
    }

    @Test
    public void testDeleteKey() {
        redisTemplate.delete("test:user12343");
    }

    @Test
    public void testFindUserPhoto() {
        String redisKey = RedisKeyUtil.getUserPhotoKey(3);
        redisTemplate.opsForZSet().add(redisKey, "1031edad14f740d4ae03559d1ab6b3f0.jpg", new Date().getTime());

        Set list = redisTemplate.opsForZSet().range(redisKey, 0, -1);
        List<String> a = new ArrayList<>();
        list.forEach(tmp -> a.add((String) tmp));
        System.out.println(a);
    }

    @Test
    public void testInsertKeyword1() {
        String redisKey = RedisKeyUtil.getLocationKeywordKey(1);

        redisTemplate.opsForZSet().add(redisKey, "底蕴", 110);
        redisTemplate.opsForZSet().add(redisKey, "文化", 100);
        redisTemplate.opsForZSet().add(redisKey, "夜景", 99);
        redisTemplate.opsForZSet().add(redisKey, "四大楼阁", 88);
        redisTemplate.opsForZSet().add(redisKey, "课文", 77);
        redisTemplate.opsForZSet().add(redisKey, "诗词", 66);
        redisTemplate.opsForZSet().add(redisKey, "口味地道", 64);
        redisTemplate.opsForZSet().add(redisKey, "交通便利", 70);
        redisTemplate.opsForZSet().add(redisKey, "风景秀美", 60);
        redisTemplate.opsForZSet().add(redisKey, "丰富", 50);

//        BoundZSetOperations boundZSetOperations = redisTemplate.boundZSetOps(redisKey);
        System.out.println(redisTemplate.opsForZSet().reverseRange(redisKey, 0, -1));
    }

    @Test
    public void testInsertKeyword2() {
        String redisKey = RedisKeyUtil.getLocationKeywordKey(2);

        redisTemplate.opsForZSet().add(redisKey, "喷泉", 110);
        redisTemplate.opsForZSet().add(redisKey, "赣江", 100);
        redisTemplate.opsForZSet().add(redisKey, "夜景", 99);
        redisTemplate.opsForZSet().add(redisKey, "休闲", 88);
        redisTemplate.opsForZSet().add(redisKey, "音乐", 77);
        redisTemplate.opsForZSet().add(redisKey, "灯光", 66);
        redisTemplate.opsForZSet().add(redisKey, "公园", 64);
        redisTemplate.opsForZSet().add(redisKey, "交通便利", 70);
        redisTemplate.opsForZSet().add(redisKey, "风景秀美", 60);
        redisTemplate.opsForZSet().add(redisKey, "美食", 50);

//        BoundZSetOperations boundZSetOperations = redisTemplate.boundZSetOps(redisKey);
        System.out.println(redisTemplate.opsForZSet().reverseRange(redisKey, 0, -1));
    }

    @Test
    public void testInsertKeyword3() {
        String redisKey = RedisKeyUtil.getLocationKeywordKey(3);

        redisTemplate.opsForZSet().add(redisKey, "红色文化", 110);
        redisTemplate.opsForZSet().add(redisKey, "解放军", 100);
        redisTemplate.opsForZSet().add(redisKey, "免费", 99);
        redisTemplate.opsForZSet().add(redisKey, "南昌起义", 95);
        redisTemplate.opsForZSet().add(redisKey, "纪念馆", 88);
        redisTemplate.opsForZSet().add(redisKey, "图片", 77);
        redisTemplate.opsForZSet().add(redisKey, "文物", 66);
        redisTemplate.opsForZSet().add(redisKey, "限流", 64);
        redisTemplate.opsForZSet().add(redisKey, "交通便利", 70);
        redisTemplate.opsForZSet().add(redisKey, "教育", 60);

//        BoundZSetOperations boundZSetOperations = redisTemplate.boundZSetOps(redisKey);
        System.out.println(redisTemplate.opsForZSet().reverseRange(redisKey, 0, -1));
    }

    @Test
    public void testInsertKeyword4() {
        String redisKey = RedisKeyUtil.getLocationKeywordKey(4);

        redisTemplate.opsForZSet().add(redisKey, "情侣", 110);
        redisTemplate.opsForZSet().add(redisKey, "亚洲第一大", 100);
        redisTemplate.opsForZSet().add(redisKey, "日落", 99);
        redisTemplate.opsForZSet().add(redisKey, "浪漫", 70);
        redisTemplate.opsForZSet().add(redisKey, "交通便利", 95);
        redisTemplate.opsForZSet().add(redisKey, "地标", 88);
        redisTemplate.opsForZSet().add(redisKey, "俯瞰", 77);
        redisTemplate.opsForZSet().add(redisKey, "灯光", 66);
        redisTemplate.opsForZSet().add(redisKey, "风景", 64);
        redisTemplate.opsForZSet().add(redisKey, "恐高", 60);

//        BoundZSetOperations boundZSetOperations = redisTemplate.boundZSetOps(redisKey);
        System.out.println(redisTemplate.opsForZSet().reverseRange(redisKey, 0, -1));
    }

    @Test
    public void testGetSearchKeyword() {
        String redisKey = RedisKeyUtil.getSearchKeywordKey();
        System.out.println(redisTemplate.opsForZSet().reverseRange(redisKey, 0, -1));
    }

    @Test
    public void testDelete() {
        String keys = "tocken:*";
        Set keysSet = redisTemplate.keys(keys);
        redisTemplate.delete(keysSet);
    }

}
