package cn.edu.jxnu.server.controller;

import cn.edu.jxnu.server.service.NoticeService;
import cn.edu.jxnu.server.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.apache.ibatis.annotations.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author hanhan
 * @Date 2022/4/29 2:13
 */

@RestController
@RequestMapping("/notice")
@Api(tags = "通知类接口")
public class NoticeController {
    @ApiModelProperty("日志信息对象")
    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    @ApiModelProperty("服务层对象")
    NoticeService service;

    @GetMapping("/all_notice")
    @ApiModelProperty("获取某个用户的所有通知")
    public Map<String, Object> GetNotice(int userId) {
        log.info("=====查询" + String.valueOf(userId) + "用户的通知信息=====");
        return new JsonResult<>(service.FindByUserId(userId)).getData();
    }

    @PutMapping("/all_notice")
    @ApiModelProperty("修改用户的通知已读情况")
    public Map<String, Object> PutNotice(int userId, @RequestParam(defaultValue = "0") int noticeId) {
        if (noticeId == 0)
            log.info("=====修改" + String.valueOf(userId) + "用户所有信息为已读=====");
        else
            log.info("=====修改" + String.valueOf(userId) + "用户" + String.valueOf(noticeId) + "信息为已读=====");
        service.ReadNotice(userId, noticeId);
        return new JsonResult<>().getData();
    }

    @DeleteMapping("/all_notice")
    @ApiModelProperty("删除用户的通知")
    public Map<String, Object> DeleteNotice(int userId, @RequestParam(defaultValue = "0") int noticeId) {
        if (noticeId == 0)
            log.info("=====删除" + String.valueOf(userId) + "用户的所有信息=====");
        else
            log.info("=====删除" + String.valueOf(userId) + "用户的" + String.valueOf(noticeId) + "信息=====");
        service.DeleteNotice(userId, noticeId);
        return new JsonResult<>().getData();
    }

    @GetMapping("/new_notice")
    @ApiModelProperty("查询用户是否有新通知")
    public Map<String, Object> NewNotice(int userId) {
        log.info("=====查询" + String.valueOf(userId) + "用户是否有新通知=====");
        return service.HaveNewNotice(userId) ? new JsonResult<>().getData() : new JsonResult<>(400, "未查询到数据").getData();
    }
}
