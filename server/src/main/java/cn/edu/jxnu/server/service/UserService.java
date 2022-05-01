package cn.edu.jxnu.server.service;

import cn.edu.jxnu.server.domain.User;

import java.util.List;

public interface UserService {
    /**
     * 实现创建用户的功能
     *
     * @param usr 包含用户信息的用户对象
     * @return 执行成功的行数
     */
    int AddUser(User usr);

    /**
     * 通过用户id进行用户信息的修改，用户id不可改
     *
     * @param usr 包含用户信息的用户对象
     * @return 执行成功的行数
     */
    int UpdateInformationById(User usr);

    /**
     * 通过用户的id删除某个用户
     *
     * @param id 用户id
     * @return 执行成功的行数
     */
    int DeleteById(int id);

    /**
     * 查找所有的用户对象
     *
     * @return 用户对象列表
     */
    List<User> FindAll();

    /**
     * 通过用户名和手机号查找用户对象
     *
     * @param nickname 用户名字符串
     * @param phone 手机号
     * @return 用户对象
     */
    List<User> FindByMultipleOptions(String nickname, String phone);
}
