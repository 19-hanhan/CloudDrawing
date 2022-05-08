package cn.edu.jxnu.service.entity;

import cn.edu.jxnu.service.util.ServerConstant;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class User implements ServerConstant {
    private int id;
    private String nickname;
    private String password;
    private String salt;
    private String email;
    private String avatar;
    private int exist;
    private int follow;
    private int fans;

    public void setAvatar(String avatar) {
        avatar = avatar.substring(avatar.lastIndexOf("/") + 1);
        this.avatar = DOMAIN + "/user/avatar/" + avatar;
    }
}
