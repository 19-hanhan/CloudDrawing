package cn.edu.jxnu.server.service.impl;

import cn.edu.jxnu.server.dao.CommentMapper;
import cn.edu.jxnu.server.domain.Comment;
import cn.edu.jxnu.server.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author hanhan
 * @Date 2022/4/29 2:10
 */

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper mapper;

    @Override
    public List<Comment> FindAll() {
        return mapper.FindAll();
    }

    @Override
    public List<Comment> FindByDiaryId(int diaryId) {
        return mapper.FindByDiaryId(diaryId);
    }
}
