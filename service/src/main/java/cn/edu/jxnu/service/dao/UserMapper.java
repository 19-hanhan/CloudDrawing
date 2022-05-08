package cn.edu.jxnu.service.dao;

import cn.edu.jxnu.service.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User selectById(int id);

    User selectByEmail(String email);

    int updateAvatar(int id, String avatarUrl);

    int updatePassword(int id, String password);

    int updateNickname(int id, String nickname);

    int updateExist(int id, int exist);

    int insertUser(User user);

}
