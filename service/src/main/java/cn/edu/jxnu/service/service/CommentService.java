package cn.edu.jxnu.service.service;

import cn.edu.jxnu.service.dao.CommentMapper;
import cn.edu.jxnu.service.entity.Comment;
import cn.edu.jxnu.service.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Autowired
    private DiaryService diaryService;

    public List<Comment> findCommentByDiaryId(int diaryId) {
        return commentMapper.selectCommentByDiaryId(diaryId);
    }

    public int deleteComment(int id) {

        Comment comment = commentMapper.selectCommentById(id);
        if (comment == null) {
            throw new IllegalArgumentException("评论不存在!");
        }

        int rows = commentMapper.updateExist(id, 0);
        if (comment.getParentId() == 0) {
            int count = commentMapper.selectCommentCountByDiaryId(comment.getDiaryId());
            diaryService.updateCommentCount(comment.getDiaryId(), count);
        }
        return rows;
    }

    public int addComment(Comment comment) {

        // 过滤敏感词
        comment.setContent(sensitiveFilter.filter(comment.getContent()));
        // 转义HTML标签
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));

        int rows = commentMapper.insertComment(comment);

        if (comment.getParentId() == 0) {
            int count = commentMapper.selectCommentCountByDiaryId(comment.getDiaryId());
            diaryService.updateCommentCount(comment.getDiaryId(), count);
        }
        return rows;
    }

    public Comment findCommentById(int id) {
        return commentMapper.selectCommentById(id);
    }

}
