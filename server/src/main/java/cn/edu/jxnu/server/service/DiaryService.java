package cn.edu.jxnu.server.service;

import cn.edu.jxnu.server.domain.Diary;

import java.util.List;

public interface DiaryService {
    /**
     * 新建日记
     *
     * @param dy 包含信息的日记对象
     * @return 执行成功行数
     */
    int AddDiary(Diary dy);

    /**
     * 通过id更新日记信息，id不可变
     *
     * @param dy 包含信息的日记对象
     * @return 执行成功行数
     */
    int UpdateDiaryById(Diary dy);

    /**
     * 通过id删除日记对象
     *
     * @param id 日记唯一标识
     * @return 执行成功行数
     */
    int DeleteDiaryById(int id);

    /**
     * 查找所有的日记对象
     *
     * @return 所有的日记对象
     */
    List<Diary> FindAll();

    /**
     * 查找部分日记
     *
     * @param page 开始页码
     * @param len 数据长度
     * @return
     */
    List<Diary> FindUserPart(int userId, int page, int len);

    /**
     * 计算总页数
     *
     * @param len 每页数据数量
     * @return 总页数
     */
    int ClacUserPage(int userId, int len);

    /**
     * 通过id查找到日记
     *
     * @param id 日记id
     * @return 唯一对应的日记
     */
    Diary FindById(int id);

    /**
     * 通过多种参数复合查找满足要求的日记
     *
     * @param uId     发布者id
     * @param title   日记标题
     * @param content 日记内容
     * @param tag     标签
     * @return 符合要求的的日记列表
     */
    List<Diary> FindByMultipleOptions(int uId, String title, String content, String tag);
}
