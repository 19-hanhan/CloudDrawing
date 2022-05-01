package cn.edu.jxnu.server.controller;

import cn.edu.jxnu.server.service.NoticeService;
import cn.edu.jxnu.server.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author hanhan
 * @Date 2022/4/30 4:10
 */

@RestController
@RequestMapping("/test")
@Api(tags = "测试类接口")
public class TestController {
    @ApiModelProperty("日志信息对象")
    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    @ApiModelProperty("服务层对象")
    NoticeService service;

    @PutMapping("/a")
    @ApiOperation("test-a")
    public Map<String, Object> a(int a) {
        return new JsonResult<>().getData();
    }
}
