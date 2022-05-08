package cn.edu.jxnu.service.service;

import cn.edu.jxnu.service.dao.NoticeMapper;
import cn.edu.jxnu.service.entity.Notice;
import cn.edu.jxnu.service.util.SensitiveFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    public List<Notice> findNotices(int actionId) {
        return noticeMapper.selectNotices(actionId);
    }

    public int readNotice(int id, int actionId) {
        return noticeMapper.updateIsRead(id, actionId, 1);
    }

    public int deleteNotice(int id, int actionId) {
        return noticeMapper.updateExist(id, actionId, 0);
    }

    public Notice findNoticeById(int id) {
        return noticeMapper.selectNoticeById(id);
    }

    public int addNotice(Notice notice) {

        String content = notice.getActionContent();

        if (StringUtils.isNotBlank(content)) {
            notice.setActionContent(sensitiveFilter.filter(content));
        }

        return noticeMapper.insertNotice(notice);
    }

    public boolean hasUnreadNotice(int actionId) {
        return noticeMapper.selectUnreadCountByActionId(actionId) != 0;
    }

}
