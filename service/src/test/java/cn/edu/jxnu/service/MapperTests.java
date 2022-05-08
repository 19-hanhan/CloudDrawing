package cn.edu.jxnu.service;

import cn.edu.jxnu.service.dao.DiaryMapper;
import cn.edu.jxnu.service.entity.Diary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ServiceApplication.class)
public class MapperTests {

    @Autowired
    private DiaryMapper diaryMapper;

    @Test
    public void testSelectDiarys() {
        List<Diary> list = diaryMapper.selectDiarys(0, 0, 10, 0);
        for (Diary diary : list) {
            System.out.println(diary);
        }
    }

    @Test
    public void testSelectDiaryCount(){
        System.out.println(diaryMapper.selectDiaryCount(0));
    }

    @Test
    public void testUpdateExist(){
        diaryMapper.updateExist(1,1);
    }

}
