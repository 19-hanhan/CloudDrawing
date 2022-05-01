package cn.edu.jxnu.server.dao;

import cn.edu.jxnu.server.domain.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NoticeMapper {
    @Select("SELECT * FROM notice")
    List<Notice> FindAll();

    @Select("SELECT * FROM notice WHERE action_id = #{userId}")
    List<Notice> FindByUserId(int userId);

    @Select("SELECT COUNT(*) FROM notice WHERE action_id = #{userId} and notice.read = 0")
    int CountNewNotice(int userId);

    @Update("<script>" +
            "UPDATE notice\n" +
            "SET notice.read = 1\n" +
            "WHERE exist = 1 AND action_id = #{userId}\n" +
            "<if test=\"id != null and id != 0\">\n" +
            "    AND id = #{id}\n" +
            "</if>\n" +
            "</script>")
    int ReadNotice(@Param("userId") int userId, @Param("id") int id);

    @Update("<script>" +
            "UPDATE notice\n" +
            "SET exist = 0\n" +
            "WHERE action_id = #{userId}\n" +
            "<if test=\"id != null and id != 0\">\n" +
            "    AND id = #{id}\n" +
            "</if>\n" +
            "</script>")
    int DeleteNotice(@Param("userId") int userId, @Param("id") int id);
}
