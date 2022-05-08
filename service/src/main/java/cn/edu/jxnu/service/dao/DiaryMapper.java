package cn.edu.jxnu.service.dao;

import cn.edu.jxnu.service.entity.Diary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiaryMapper {

    List<Diary> selectDiarys(int userId, int offset, int limit, int orderMode);

    int selectDiaryCount(@Param("userId") int userId);

    int updateExist(int id, int exist);

    int insertDiary(Diary diary);

    Diary selectDiaryById(int id);

    int updateScore(int id, double score);

    int updateCommentCount(int id, int count);

}
