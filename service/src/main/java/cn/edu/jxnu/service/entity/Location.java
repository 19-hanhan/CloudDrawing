package cn.edu.jxnu.service.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Location {

    private int id;
    private String name;
    private double lng;
    private double lat;
    private boolean show;

    private List<Map<String, Object>> keyword;

}
