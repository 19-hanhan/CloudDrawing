package cn.edu.jxnu.server.controller;

import cn.edu.jxnu.server.domain.Diary;
import cn.edu.jxnu.server.service.DiaryService;
import cn.edu.jxnu.server.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/diary")
@Api(tags = "日记类接口")
public class DiaryController {
    @ApiModelProperty("日志信息对象")
    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    @ApiModelProperty("服务层对象")
    private DiaryService service;

    @GetMapping("/all_diary")
    @ApiOperation("分页查询所有的日记信息")
    public Map<String, Object> AllDiary(@RequestParam(value = "userId", defaultValue = "0") int userId, @RequestParam("pageNumber") int page, @RequestParam("pageSize") int len) {
        // 其中添加一个列表项，为图片索引，字段名为image_lis
        log.info("=====查询第" + String.valueOf(page) + "页的日记信息=====");
        JsonResult<List> json = new JsonResult<>(service.FindUserPart(userId, page, len));
        Map<String, Object> mp = new HashMap<>();
        mp.put("totalPages", service.ClacUserPage(userId, len));
        json.Add(mp);
        return json.getData();
    }

    @PostMapping("/click_like")
    @ApiOperation("点赞")
    public Map<String, Object> ClickLike(int userId, int diaryId) {
        return new JsonResult<>().getData(); // 待填充：返回100为删除点赞，返回200为点赞
    }

    @PostMapping("/delete_diary")
    @ApiOperation("删除日记")
    public Map<String, Object> DeleteDiary(int userId, int diaryId) {
        return new JsonResult<>().getData(); // 待填充
    }

    @PostMapping("/add_diary")
    @ApiOperation("新建日记")
    public Map<String, Object> AddDiary(int userId, String content) {
        return new JsonResult<>().getData(); // 待填充
    }
}
