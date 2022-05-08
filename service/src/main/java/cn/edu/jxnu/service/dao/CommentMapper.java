package cn.edu.jxnu.service.dao;

import cn.edu.jxnu.service.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    List<Comment> selectCommentByDiaryId(int diaryId);

    int updateExist(int id, int exist);

    int insertComment(Comment comment);

    Comment selectCommentById(int id);

    int selectCommentCountByDiaryId(int diaryId);

}
