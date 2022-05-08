package cn.edu.jxnu.service.dao;

import cn.edu.jxnu.service.entity.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {

    List<Notice> selectNotices(int actionId);

    int updateIsRead(int id, int actionId, int isRead);

    int updateExist(int id, int actionId, int exist);

    int selectUnreadCountByActionId(int actionId);

    Notice selectNoticeById(int id);

    int insertNotice(Notice notice);

}
