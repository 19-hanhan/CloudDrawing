package cn.edu.jxnu.server.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Author hanhan
 * @Date 2022/4/29 1:06
 */

@ApiModel("评论实体类")
public class Comment {
    @ApiModelProperty("评论唯一标识")
    private int id;
    @ApiModelProperty("所属日记Id")
    private int diaryId;
    @ApiModelProperty("所属用户Id")
    private int userId;
    @ApiModelProperty("用户昵称")
    private String nickname;
    @ApiModelProperty("用户头像")
    private String avatar;
    @ApiModelProperty("是否为子评论标识")
    private int parentId;
    @ApiModelProperty("评论内容")
    private String content;
    @ApiModelProperty("评论创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date commentTime;
    @ApiModelProperty("点赞量")
    private int like;
    @ApiModelProperty("存在标记")
    private boolean exist;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", diaryId=" + diaryId +
                ", userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", parentId=" + parentId +
                ", content='" + content + '\'' +
                ", commentTime=" + commentTime +
                ", like=" + like +
                ", exist=" + exist +
                '}';
    }

    public Comment() {
    }

    public Comment(Diary dy, User usr) {
        this(dy, usr, 0);
    }

    public Comment(Diary dy, User usr, int parentId) {
        diaryId = dy.getId();
        userId = usr.getId();
        nickname = usr.getNickname();
        avatar = usr.getAvatar();
        this.parentId = parentId;
        commentTime = new Date();
        like = 0;
        exist = true;
    }

    public Comment(Diary dy, User usr, int parentId, String content) {
        this(dy, usr, parentId);
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(int diaryId) {
        this.diaryId = diaryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }
}
