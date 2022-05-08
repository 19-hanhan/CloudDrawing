package cn.edu.jxnu.service.controller;

import cn.edu.jxnu.service.entity.LoginToken;
import cn.edu.jxnu.service.entity.User;
import cn.edu.jxnu.service.service.FollowService;
import cn.edu.jxnu.service.service.UserService;
import cn.edu.jxnu.service.util.HostHolder;
import cn.edu.jxnu.service.util.ServerConstant;
import cn.edu.jxnu.service.util.ServerUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Controller
@RequestMapping("/user")
@ResponseBody
public class UserController implements ServerConstant {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Value("${service.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${service.path.upload}")
    private String uploadPath;

    @Autowired
    private FollowService followService;

    @Autowired
    private HostHolder hostHolder;

    @PostMapping("/login")
    public String login(String username, String password, HttpServletResponse response) {
        long expiredSeconds = DEFAULT_EXPIRED_SECONDS;

        Map<String, Object> map = userService.loginByEmail(username, password, expiredSeconds);
        if (map.containsKey("token")) {
            Cookie cookie = new Cookie("token", map.get("token").toString());
            cookie.setPath("/");
            cookie.setMaxAge((int) expiredSeconds);
            response.addCookie(cookie);

            Map<String, Object> result = new HashMap<>();
            LoginToken loginToken = userService.findLoginToken((String) map.get("token"));
            result.put("user", userService.findUserById(loginToken.getUserId()));

            return ServerUtil.getJSONString(200, "登录成功!", result);
        } else {
            // map.get("usernameMsg")
            // map.get("passwordMsg")
            return ServerUtil.getJSONString(400, "登录失败", map);
        }
    }

    @PostMapping("/register")
    public String register(String nickname, String username, String password, String confirmPassword) {
        User user = userService.findUserByEmail(username);

        if (StringUtils.isBlank(nickname) || StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword)) {
            throw new IllegalArgumentException("参数不能为空!");
        }

        if (!password.equals(confirmPassword)) {
            throw new RuntimeException("两次密码不一致!");
        }

        if (user != null) {
            throw new IllegalArgumentException("该邮箱已经注册啦!");
        }

        user = new User();
        user.setNickname(nickname);
        user.setSalt(ServerUtil.generateUUID().substring(0, 5));
        user.setPassword(ServerUtil.md5(password + user.getSalt()));
        user.setEmail(username);
        user.setAvatar(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setExist(1);
        user.setFans(0);
        user.setFollow(0);

        userService.addUser(user);

        return ServerUtil.getJSONString(200, "注册成功");
    }

    @PostMapping("/logout")
    public String logout(@CookieValue("token") String token) {
        userService.logout(token);
        SecurityContextHolder.clearContext();
        return ServerUtil.getJSONString(200, "登出成功!");
    }

    @GetMapping("/check_follow")
    public String checkFollow(int checkId) {
        User user = hostHolder.getUser();
        boolean isFollow = followService.hasfollow(user.getId(), checkId);
        if (isFollow == true) {
            return ServerUtil.getJSONString(200, "已关注");
        } else {
            return ServerUtil.getJSONString(201, "未关注");
        }
    }

    @PostMapping("/change_follow")
    public String changefollow(int followId) {
        User user = hostHolder.getUser();
        if (user.getId() == followId) {
            throw new IllegalArgumentException("不能关注自己!");
        }
        boolean isFollow = followService.hasfollow(user.getId(), followId);

        if (isFollow) { // 取关
            followService.unfollow(user.getId(), followId);
            return ServerUtil.getJSONString(201, "取消关注成功!");

        } else { // 关注
            followService.follow(user.getId(), followId);
            return ServerUtil.getJSONString(200, "关注成功!");
        }
    }

    @PostMapping("/change_avatar")
    public String changeAvatar(MultipartFile avatar) {
        if (avatar == null) {
            return ServerUtil.getJSONString(405, "您没有选择图片!");
        }
        String fileName = avatar.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)) {
            return ServerUtil.getJSONString(405, "文件格式不正确!");
        }

        fileName = ServerUtil.generateUUID() + suffix;
        File dest = new File(uploadPath + "/" + fileName);
        try {
            avatar.transferTo(dest);
        } catch (IOException e) {
            logger.error("上传文件失败: " + e.getMessage());
            throw new RuntimeException("上传文件失败,服务器发生异常!", e);
        }

        // 更新当前用户的头像路径 Web路径
        // http://localhost:8080/user/avatar/xxx.png
        User user = hostHolder.getUser();
        String avatarUrl = fileName;

        userService.updateAvatar(user.getId(), avatarUrl);

        Map<String, Object> result = new HashMap<>();
        result.put("avatar", userService.findUserById(user.getId()).getAvatar());

        return ServerUtil.getJSONString(200, "更新头像成功!", result);
    }

    @GetMapping("/avatar/{fileName}")
    public void getAvatar(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        // 服务器存放路径
        fileName = uploadPath + "/" + fileName;
        String suffix = fileName.substring(fileName.lastIndexOf("."));

        response.setContentType("image/" + suffix);
        try (FileInputStream fis = new FileInputStream(fileName); OutputStream os = response.getOutputStream();) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer);
            }
        } catch (IOException e) {
            logger.error("读取头像失败: " + e.getMessage());
        }
    }

    @PostMapping("/change_password")
    public String changePassword(String password, String newpass, String checkPass) {
        if (StringUtils.isBlank(password) || StringUtils.isBlank(newpass) || StringUtils.isBlank(checkPass)) {
            return ServerUtil.getJSONString(405, "密码不能为空!");
        }
        if (!newpass.equals(checkPass)) {
            return ServerUtil.getJSONString(405, "两次密码不一致!");
        }

        User user = hostHolder.getUser();
        password = ServerUtil.md5(password + user.getSalt());
        if (!user.getPassword().equals(password)) {
            return ServerUtil.getJSONString(405, "初始密码不正确!");
        }
        password = ServerUtil.md5(newpass + user.getSalt());
        userService.updatePassword(user.getId(), password);

        return ServerUtil.getJSONString(200, "更新密码成功");
    }

    @PostMapping("/change_nickname")
    public String changeNickname(String nickname) {
        if (StringUtils.isBlank(nickname)) {
            return ServerUtil.getJSONString(405, "昵称不能为空!");
        }

        User user = hostHolder.getUser();
        userService.updateNickname(user.getId(), nickname);
        return ServerUtil.getJSONString(200, "更新昵称成功!");
    }

    @PostMapping("/delete_account")
    public String deleteAccount() {
        User user = hostHolder.getUser();
        userService.delectUser(user.getId());
        return ServerUtil.getJSONString(200, "注销用户成功!");
    }

    @GetMapping("/get_follow_num")
    public String getFollowNum(int userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("followNum", followService.findfollowCount(userId));
        return ServerUtil.getJSONString(200, null, map);
    }

    @GetMapping("/get_fans_num")
    public String getFansNum(int userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("fansNum", followService.findFansCount(userId));
        return ServerUtil.getJSONString(200, null, map);
    }

    @GetMapping("/all_photo")
    public String allPhoto(int userId) {
        // 相册图片字段名为photo_lis

        List<String> list = userService.findUserPhoto(userId);
        List<String> photos = new ArrayList<>();


        // https://localhost:8080/user-photo/{123456.png}
        if (list != null) {
            for (String s : list) {
                photos.add(domain + "/user/user-photo/" + s);
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("photos", photos);

        return ServerUtil.getJSONString(200, "用户主页图片", result);
    }

    @GetMapping("/user-photo/{fileName}")
    public void getUserPhoto(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        fileName = uploadPath + "/" + fileName;
        String suffix = fileName.substring(fileName.lastIndexOf("."));

        response.setContentType("image/" + suffix);
        try (FileInputStream fis = new FileInputStream(fileName); OutputStream os = response.getOutputStream();) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer);
            }
        } catch (IOException e) {
            logger.error("读取用户主页图片失败: " + e.getMessage());
        }
    }

    @GetMapping("/get_fans")
    public String getFans(@RequestParam(defaultValue = "0") int otherId) {
        // 粉丝放在result作为列表，里面的字段包含：id，nickname，avatar，isFollow(0没关注, 1关注了）

        int userId = otherId == 0 ? hostHolder.getUser().getId() : otherId;

        List<Map<String, Object>> list = followService.findFans(userId, 0, 100000);

        for (Map<String, Object> map : list) {
            User target = (User) map.get("user");
            map.put("isFollow", followService.hasfollow(userId, target.getId()) ? 1 : 0);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("result", list);

        return ServerUtil.getJSONString(200, null, result);
    }

    @GetMapping("/get_follow")
    public String getfollow(@RequestParam(defaultValue = "0") int otherId) {
        // 关注的人放在result作为列表
        int userId = otherId == 0 ? hostHolder.getUser().getId() : otherId;
        List<Map<String, Object>> list = followService.findfollow(userId, 0, 100000);
        User user = hostHolder.getUser();

        for (Map<String, Object> map : list) {
            User target = (User) map.get("user");
            map.put("isFollow", followService.hasfollow(user.getId(), target.getId()) ? 1 : 0);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("result", list);

        return ServerUtil.getJSONString(200, null, result);
    }

    @GetMapping("/get_user")
    public String getUser(int userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("user", userService.findUserById(userId));

        return ServerUtil.getJSONString(200, null, map);
    }
}
