package cn.edu.jxnu.service.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class Notice {

    private int id;
    private int diaryId;
    private String actionContent;
    private int senderId;
    private int actionId;
    private int isRead;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private int exist;

}
