package cn.edu.jxnu.server.service.impl;

import cn.edu.jxnu.server.dao.DiaryMapper;
import cn.edu.jxnu.server.domain.Diary;
import cn.edu.jxnu.server.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiaryServiceImpl implements DiaryService {
    @Autowired
    private DiaryMapper mapper;

    @Override
    public int AddDiary(Diary dy) {
        return mapper.AddDiary(dy);
    }

    @Override
    public int UpdateDiaryById(Diary dy) {
        return mapper.UpdateDiaryById(dy);
    }

    @Override
    public int DeleteDiaryById(int id) {
        return mapper.DeleteDiaryById(id);
    }

    @Override
    public List<Diary> FindAll() {
        return mapper.FindAll();
    }

    @Override
    public List<Diary> FindUserPart(int userId, int page, int len) {
        return mapper.FindUserPart(userId, page * len, len);
    }

    @Override
    public int ClacUserPage(int userId, int len) {
        return (int) Math.ceil(1.0 * mapper.UserSize(userId) / len);
    }

    @Override
    public Diary FindById(int id) {
        return mapper.FindById(id);
    }

    @Override
    public List<Diary> FindByMultipleOptions(int uId, String title, String content, String tag) {
        return mapper.FindByMultipleOptions(uId, title, content, tag);
    }
}
