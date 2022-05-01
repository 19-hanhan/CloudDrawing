package cn.edu.jxnu.server.dao;

import cn.edu.jxnu.server.domain.Diary;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Mapper
@Repository
public interface DiaryMapper {
    @Insert("INSERT diary(user_id, nickname, avatar, title, create_time, content, tag, like, favorite, comment_num) " +
            "VALUES (#{userId}, #{nickname}, #{avatar}, #{title}, #{createIime}, #{content}, #{tag}, #{like}, #{favorite}, #{commentNum})")
    int AddDiary(Diary dy);

    @Update("UPDATE user SET title = #{title}, content = #{content}, tag = #{tag} WHERE id = #{id}")
    int UpdateDiaryById(Diary dy);

    @Update("UPDATE diary SET exist = 0 WHERE id = #{id}")
    int DeleteDiaryById(int id);

    @Select("SELECT * FROM diary")
    List<Diary> FindAll();

    @Select("<script>" +
            "SELECT * FROM diary\n" +
            "<if test=\"userId != null and userId != 0\">\n" +
            "   WHERE user_id = #{userId}\n" +
            "</if>\n" +
            "LIMIT #{st}, #{len}" +
            "</script>")
    List<Diary> FindUserPart(@Param("userId") int userId, @Param("st") int st, @Param("len") int len);

    @Select("SELECT COUNT(*) FROM diary WHERE user_id = #{userId}")
    int UserSize(int userId);

    @Select("SELECT * FROM diary WHERE id = #{id}")
    Diary FindById(int id);

    @Select("<script>" +
            "SELECT * FROM diary\n" +
            "<where>\n" +
            "    <if test=\"userId != null or userId != 0\">\n" +
            "        AND user_id = #{userId}\n" +
            "    </if>\n" +
            "    <if test=\"title != null or title != ''\">\n" +
            "        AND title like '%${title}%'\n" +
            "    </if>\n" +
            "    <if test=\"content != null or content != ''\">\n" +
            "        AND content like '%${content}%'\n" +
            "    </if>\n" +
            "    <if test=\"title != null or title != ''\">\n" +
            "        AND tag like '%${tag}%'\n" +
            "    </if>\n" +
            "</where>" +
            "</script>")
    List<Diary> FindByMultipleOptions(@Param("userId") int userId, @Param("title") String title, @Param("content") String content, @Param("tag") String tag);
}
