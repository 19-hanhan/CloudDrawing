package cn.edu.jxnu.service.service;

import cn.edu.jxnu.service.dao.LocationMapper;
import cn.edu.jxnu.service.entity.Location;
import cn.edu.jxnu.service.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LocationService {

    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    public List<Location> findAllLocation() {
        return locationMapper.selectAllLocation();
    }

    public Location findLocationById(int id) {
        return locationMapper.selectLocationById(id);
    }

    public List<Map<String, Object>> findLocationKeyword(int id) {
        String redisKey = RedisKeyUtil.getLocationKeywordKey(id);
        Set<String> set = redisTemplate.opsForZSet().reverseRange(redisKey, 0, -1);
        List<Map<String, Object>> list = new ArrayList<>();
        for (String s : set) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", s);
            map.put("value", Math.round(redisTemplate.opsForZSet().score(redisKey, s)));
            list.add(map);
            if (list.size() == 10) break;
        }
        return list;
    }

}
