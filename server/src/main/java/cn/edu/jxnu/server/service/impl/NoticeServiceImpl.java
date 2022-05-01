package cn.edu.jxnu.server.service.impl;

import cn.edu.jxnu.server.dao.NoticeMapper;
import cn.edu.jxnu.server.domain.Notice;
import cn.edu.jxnu.server.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author hanhan
 * @Date 2022/4/29 2:12
 */

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    NoticeMapper mapper;

    @Override
    public List<Notice> FindAll() {
        return mapper.FindAll();
    }

    @Override
    public List<Notice> FindByUserId(int userId) {
        return mapper.FindByUserId(userId);
    }

    @Override
    public boolean HaveNewNotice(int userId) {
        return mapper.CountNewNotice(userId) > 0;
    }

    @Override
    public int ReadNotice(int userId, int id) {
        return mapper.ReadNotice(userId, id);
    }

    @Override
    public int DeleteNotice(int userId, int id) {
        return mapper.DeleteNotice(userId, id);
    }
}
