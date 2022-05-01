package cn.edu.jxnu.server.dao;

import cn.edu.jxnu.server.domain.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {
    @Select("SELECT * FROM comment")
    List<Comment> FindAll();

    @Select("SELECT * FROM comment WHERE diary_id = #{diaryId}")
    List<Comment> FindByDiaryId(int diaryId);
}
