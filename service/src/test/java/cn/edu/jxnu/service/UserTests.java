package cn.edu.jxnu.service;

import cn.edu.jxnu.service.dao.UserMapper;
import cn.edu.jxnu.service.entity.User;
import cn.edu.jxnu.service.util.ServerUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ServiceApplication.class)
public class UserTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectById() {
        User user = userMapper.selectById(3);
        System.out.println(user);

        for (int i = 0; i < 4; i++) {
            System.out.println(ServerUtil.generateUUID());
        }
    }

}
