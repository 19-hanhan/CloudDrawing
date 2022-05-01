package cn.edu.jxnu.server.controller;

import cn.edu.jxnu.server.service.UserService;
import cn.edu.jxnu.server.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Api(tags = "用户类接口")
public class UserController {
    @ApiModelProperty("日志信息对象")
    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    @ApiModelProperty("服务层对象")
    private UserService service;

    @PostMapping("/login")
    @ApiOperation("使用账号密码登录")
    public Map<String, Object> Login(String username, String password) {
        JsonResult<Void> json = new JsonResult<>();
        Map<String, Object> mp = new HashMap<>();
        mp.put("token", "asugihagnlajsdfhna;sojh");
        mp.put("userId", 2);
        mp.put("avatar", "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2F2021%2Fedpic_source%2F46%2F98%2F29%2F469829f3862c0d600f953a3e81392216_21.jpg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1653874264&t=2c2e42528ea809910864ad2f7b59e224");
        mp.put("nickname", "Ander");
        json.Add(mp);
        return json.getData(); // 待填充
    }

    @GetMapping("/check_favorite")
    @ApiOperation("检查是否为关注状态")
    public Map<String, Object> CheckFavorite(int userId, int checkId){
        return new JsonResult<>().getData(); // 待填充
    }

    @PostMapping("/change_favorite")
    @ApiOperation("改变关注状态")
    public Map<String, Object> ChangeFavorite(int userId, int followId){
        return new JsonResult<>().getData(); // 待填充
    }

    @PostMapping("/change_avatar")
    @ApiOperation("修改头像")
    public Map<String, Object> ChangeAvatar(int userId, int followId){
        return new JsonResult<>().getData(); // 待填充
    }

    @PostMapping("/change_password")
    @ApiOperation("修改密码")
    public Map<String, Object> ChangePassword(String password, String newpass, String checkPass, int userId){
        log.info("=====修改用户" + String.valueOf(userId) + "的密码=====");
        return new JsonResult<>().getData(); // 待填充
    }

    @PostMapping("/change_nickname")
    @ApiOperation("修改昵称")
    public Map<String, Object> ChangeNickname(int userId, String nickname){
        log.info("=====修改用户" + String.valueOf(userId) + "的昵称=====");
        return new JsonResult<>().getData(); // 待填充
    }

    @PostMapping("/delete_account")
    @ApiOperation("注销账户")
    public Map<String, Object> DeleteAccount(int userId){
        log.info("=====注销用户" + String.valueOf(userId) + "的账户=====");
        return new JsonResult<>().getData(); // 待填充
    }

    @GetMapping("/get_favorite_num")
    @ApiOperation("查询关注数量")
    public Map<String, Object> GetFavoriteNum(int userId){
        log.info("=====查询用户" + String.valueOf(userId) + "的关注数量=====");
        return new JsonResult<>().getData(); // 待填充
    }

    @GetMapping("/get_fans_num")
    @ApiOperation("查询粉丝数量")
    public Map<String, Object> GetFansNum(int userId){
        log.info("=====查询用户" + String.valueOf(userId) + "的粉丝数量=====");
        return new JsonResult<>().getData(); // 待填充
    }

    @GetMapping("/all_photo")
    @ApiOperation("查询用户相册图片")
    public Map<String, Object> AllPhoto(int userId){
        // 相册图片字段名为photo_lis
        log.info("=====查询用户" + String.valueOf(userId) + "的相册图片=====");
        return new JsonResult<>().getData(); // 待填充
    }

    @GetMapping("/get_fans")
    @ApiOperation("获取粉丝列表")
    public Map<String, Object> GetFans(int userId, @RequestParam(defaultValue = "0") int otherId){
        // 粉丝放在result作为列表，里面的字段包含：id，nickname，avatar，isFavorite(0没关注, 1关注了）
        log.info("=====查询用户" + String.valueOf(userId) + "的粉丝列表=====");
        return new JsonResult<>().getData(); // 待填充
    }

    @GetMapping("/get_favorite")
    @ApiOperation("获取关注列表")
    public Map<String, Object> GetFavorite(int userId, @RequestParam(defaultValue = "0") int otherId){
        // 关注的人放在result作为列表
        log.info("=====查询用户" + String.valueOf(userId) + "的相册图片=====");
        return new JsonResult<>().getData(); // 待填充
    }

    @GetMapping("/get_user")
    @ApiOperation("获取用户信息")
    public Map<String, Object> GetUser(int userId){
        // result作为map返回
        log.info("=====查询用户" + String.valueOf(userId) + "的信息=====");
        return new JsonResult<>().getData(); // 待填充
    }
}
