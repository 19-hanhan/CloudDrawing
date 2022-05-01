package cn.edu.jxnu.server.service;

import cn.edu.jxnu.server.domain.Notice;

import java.util.List;

/**
 * @Author hanhan
 * @Date 2022/4/29 2:09
 */

public interface NoticeService {
    /**
     * 查找所有通知
     *
     * @return 所有通知列表
     */
    List<Notice> FindAll();

    /**
     * 查找某个用户的通知
     *
     * @param userId 用户Id
     * @return 对应用户的通知列表
     */
    List<Notice> FindByUserId(int userId);

    /**
     * 判断是否有未读的通知
     *
     * @param userId 用户Id
     * @return 是/否
     */
    boolean HaveNewNotice(int userId);

    /**
     * 将未读的通知按要求变成有已读
     *
     * @param userId 登录的用户Id
     * @param id     要阅读的通知的标识
     * @return 修改成功的通知条数
     */
    int ReadNotice(int userId, int id);

    /**
     * 删除某条通知
     *
     * @param userId 登录的用户Id
     * @param id 通知标识
     * @return
     */
    int DeleteNotice(int userId, int id);
}
