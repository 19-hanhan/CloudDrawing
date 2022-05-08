package cn.edu.jxnu.service.entity;

import lombok.Data;

import java.util.Date;

@Data
public class LoginToken {

    private int id;

    private int userId;

    private String token;

    private int status;

    private Date expired;

}
