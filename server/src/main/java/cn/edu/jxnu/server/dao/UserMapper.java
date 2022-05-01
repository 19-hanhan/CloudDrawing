package cn.edu.jxnu.server.dao;

import cn.edu.jxnu.server.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    @Insert("INSERT user(nickname, password, salt, phone, email, avatar, favorite, fans) VALUES (#{nickname}, #{password}, #{salt}, #{phone}, #{email}, #{avatar}, #{favorite}, #{fans})")
    int AddUser(User usr);

    @Update("UPDATE user SET nickname = #{nickname}, password = #{password}, phone = #{phone}, email = #{email}, avatar = #{avatar} WHERE id = #{id}")
    int UpdateInformationById(User usr);

    @Update("UPDATE user SET exist = 0 WHERE id = #{id}")
    int DeleteById(int id);

    @Select("SELECT * FROM user")
    List<User> FindAll();

    @Select("<script>" +
            "SELECT * FROM user\n" +
            "<where>\n" +
            "    <if test=\"nickname != null and nickname != ''\">\n" +
            "        AND username like '%${username}%'\n" +
            "    </if>\n" +
            "    <if test=\"phone != null and phone != ''\">\n" +
            "        AND phone like '%${phone}%'\n" +
            "    </if>\n" +
            "</where>" +
            "</script>")
    List<User> FindByMultipleOptions(@Param("nickname") String nickname, @Param("phone") String phone);
}
