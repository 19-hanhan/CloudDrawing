package cn.edu.jxnu.server.service;

import cn.edu.jxnu.server.domain.Comment;

import java.util.List;

public interface CommentService {
    /**
     * 查找并返回所有评论
     *
     * @return 评论列表
     */
    List<Comment> FindAll();

    /**
     * 通过日记Id获取评论
     * @param diaryId 所属日记的Id
     * @return
     */
    List<Comment> FindByDiaryId(int diaryId);
}
