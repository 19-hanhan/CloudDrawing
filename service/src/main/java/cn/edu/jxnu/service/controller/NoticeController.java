package cn.edu.jxnu.service.controller;

import cn.edu.jxnu.service.entity.Notice;
import cn.edu.jxnu.service.entity.User;
import cn.edu.jxnu.service.service.NoticeService;
import cn.edu.jxnu.service.service.UserService;
import cn.edu.jxnu.service.util.HostHolder;
import cn.edu.jxnu.service.util.ServerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/notice")
@ResponseBody
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    private UserService userService;

    // 查找当前用户作为收件人的全部通知
    @GetMapping("/all_notice")
    public String getNotice() {

        User actionUser = hostHolder.getUser();

        List<Notice> noticeList = noticeService.findNotices(actionUser.getId());

        List<Map<String, Object>> notices = new ArrayList<>();


        if (noticeList != null) {
            for (Notice notice : noticeList) {
                Map<String, Object> map = new HashMap<>();
                map.put("notice", notice);
                map.put("sender", userService.findUserById(notice.getSenderId()));
                map.put("action", userService.findUserById(actionUser.getId()));
                notices.add(map);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("notices", notices);

        return ServerUtil.getJSONString(200, null, result);
    }

    @PostMapping("/change_notice")
    public String putNotice(@RequestParam(defaultValue = "0") int noticeId) {

        User user = hostHolder.getUser();

        String msg = null;
        if (noticeId != 0) {
            Notice notice = noticeService.findNoticeById(noticeId);
            if (notice == null || notice.getExist() == 0) {
                throw new IllegalArgumentException("通知不存在!");
            }
            if (notice.getActionId() != user.getId()) {
                throw new IllegalArgumentException("不能篡改其他用户的通知状态!");
            }

            msg = "已读成功!";
        } else {
            msg = "已读全部通知!";
        }


        noticeService.readNotice(noticeId, user.getId());

        return ServerUtil.getJSONString(200, msg);
    }

    @PostMapping("/delete_notice")
    public String deleteNotice(@RequestParam(defaultValue = "0") int noticeId) {

        User user = hostHolder.getUser();

        String msg = null;
        if (noticeId != 0) {
            Notice notice = noticeService.findNoticeById(noticeId);
            if (notice == null) {
                throw new IllegalArgumentException("通知号不存在!");
            }
            if (notice.getActionId() != user.getId()) {
                throw new IllegalArgumentException("不能篡改其他用户的通知状态!");
            }
            if (notice.getExist() == 0) {
                throw new IllegalArgumentException("通知已经删除了!");
            }

            msg = "删除成功!";
        } else {
            msg = "删除全部通知!";
        }

        noticeService.deleteNotice(noticeId, user.getId());

        return ServerUtil.getJSONString(200, msg);
    }

    @GetMapping("/new_notice")
    public String newNotice() {
        User user = hostHolder.getUser();
        boolean hasUnread = noticeService.hasUnreadNotice(user.getId());
        if (hasUnread) {
            return ServerUtil.getJSONString(200, "存在未读通知!");
        }
        return ServerUtil.getJSONString(205, "不存在未读通知!");
    }

}
