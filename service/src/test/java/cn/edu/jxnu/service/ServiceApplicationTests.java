package cn.edu.jxnu.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ServiceApplication.class)
class ServiceApplicationTests {

    @Test
    void testContextLoads() {
        System.out.println(1);
    }

}
