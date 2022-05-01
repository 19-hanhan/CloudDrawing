package cn.edu.jxnu.server;

import cn.edu.jxnu.server.dao.NoticeMapper;
import cn.edu.jxnu.server.service.DiaryService;
import cn.edu.jxnu.server.service.NoticeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CloudDrawingApplicationTests {
    @Autowired
    DiaryService service;

    @Autowired
    NoticeMapper mapper;

    @Autowired
    NoticeService noticeService;

    @Test
    void contextLoads() {
        System.out.println(noticeService.DeleteNotice(2, 6));
//        System.out.println(mapper.ReadNotice(2, 0));
    }

}
