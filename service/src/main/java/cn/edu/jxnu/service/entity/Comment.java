package cn.edu.jxnu.service.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class Comment {

    private int id;
    private int diaryId;
    private int userId;
    private int parentId;
    private String content;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date commentTime;
    private int likeNum;
    private int exist;

}
