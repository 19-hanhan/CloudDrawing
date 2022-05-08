package cn.edu.jxnu.service.dao;

import cn.edu.jxnu.service.entity.Location;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LocationMapper {

    List<Location> selectAllLocation();

    Location selectLocationById(int id);

}
