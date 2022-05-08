package cn.edu.jxnu.service.controller;

import cn.edu.jxnu.service.entity.Location;
import cn.edu.jxnu.service.service.LocationService;
import cn.edu.jxnu.service.util.ServerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/all_location")
    public String getAllLocation() {
        List<Location> locationList = locationService.findAllLocation();
        List<Location> locations = new ArrayList<>();

        if (locationList != null) {
            for (Location location : locationList) {
                location.setKeyword(locationService.findLocationKeyword(location.getId()));
                locations.add(location);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("locations", locations);

        return ServerUtil.getJSONString(200, "获取全部地点", result);

    }

}
