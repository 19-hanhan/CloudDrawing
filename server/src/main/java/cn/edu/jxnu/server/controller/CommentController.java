package cn.edu.jxnu.server.controller;

import cn.edu.jxnu.server.service.CommentService;
import cn.edu.jxnu.server.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author hanhan
 * @Date 2022/4/29 2:13
 */

@RestController
@RequestMapping("/comment")
@Api(tags = "评论类接口")
public class CommentController {
    @ApiModelProperty("日志信息对象")
    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    @ApiModelProperty("服务层对象")
    CommentService service;

    @GetMapping("/get_comment")
    @ApiModelProperty("通过日记Id获取对应评论")
    public Map<String, Object> GetComment(int diaryId) {
        log.info("=====查询" + String.valueOf(diaryId) + "日记的评论信息=====");
        return new JsonResult<>(service.FindByDiaryId(diaryId)).getData();
    }

    @PostMapping("/delete_comment")
    @ApiModelProperty("通过评论Id删除评论")
    public Map<String, Object> DeleteComment(int userId, int commentId) {
        return new JsonResult<>().getData(); // 待填充
    }

    @PostMapping("/add_comment")
    @ApiModelProperty("新增评论")
    public Map<String, Object> AddComment(int diaryId, int userId, String comment, int parentId) {
        // 返回的时候要把新增的那条评论放到result里面返回
        return new JsonResult<>().getData(); // 待填充
    }
}
