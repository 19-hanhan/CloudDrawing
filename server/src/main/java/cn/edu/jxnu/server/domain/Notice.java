package cn.edu.jxnu.server.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Author hanhan
 * @Date 2022/4/29 1:06
 */

@ApiModel("通知消息实体类")
public class Notice {
    @ApiModelProperty("消息唯一标识")
    private int id;
    @ApiModelProperty("消息内容")
    private String actionContent;
    @ApiModelProperty("发件人id")
    private int senderId;
    @ApiModelProperty("发件人")
    private String sender;
    @ApiModelProperty("收件人Id")
    private int actionId;
    @ApiModelProperty("收件人昵称")
    private String action;
    @ApiModelProperty("已读标记")
    private boolean read;
    @ApiModelProperty("通知创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @ApiModelProperty("存在标记")
    private boolean exist;

    public Notice() {
    }

    public Notice(User sender, User action) {
        this.senderId = sender.getId();
        this.sender = sender.getNickname();
        this.actionId = action.getId();
        this.action = action.getNickname();
        createTime = new Date();
        read = false;
        exist = true;
    }

    public Notice(User sender, User action, String actionContent) {
        this(sender, action);
        this.actionContent = actionContent;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", actionContent='" + actionContent + '\'' +
                ", senderId=" + senderId +
                ", sender='" + sender + '\'' +
                ", actionId=" + actionId +
                ", action='" + action + '\'' +
                ", read=" + read +
                ", createTime=" + createTime +
                ", exist=" + exist +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActionContent() {
        return actionContent;
    }

    public void setActionContent(String actionContent) {
        this.actionContent = actionContent;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }
}
