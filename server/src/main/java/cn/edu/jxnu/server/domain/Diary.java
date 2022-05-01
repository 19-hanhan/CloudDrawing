package cn.edu.jxnu.server.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("日记实体类")
public class Diary {
    @ApiModelProperty("日记唯一标识")
    private int id;
    @ApiModelProperty("日记发布者唯一标识")
    private int userId;
    @ApiModelProperty("用户名称")
    private String nickname;
    @ApiModelProperty("用户头像")
    private String avatar;
    @ApiModelProperty("题目")
    private String title;
    @ApiModelProperty("发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("标签")
    private String tag;
    @ApiModelProperty("点赞量")
    private int like;
    @ApiModelProperty("收藏量")
    private int favorite;
    @ApiModelProperty("评论数")
    private int commentNum;
    @ApiModelProperty("热度")
    private float hot;
    @ApiModelProperty("存在标记")
    private boolean exist;

    public Diary() {
    }

    public Diary(User usr, String title) {
        userId = usr.getId();
        nickname = usr.getNickname();
        avatar = usr.getAvatar();
        this.title = title;
        createTime = new Date();
        like = 0;
        favorite = 0;
        commentNum = 0;
        hot = 0;
        exist = true;
    }

    public Diary(User usr, String title, String content) {
        this(usr, title);
        this.content = content;
    }

    @Override
    public String toString() {
        return "Diary{" +
                "id=" + id +
                ", userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", title='" + title + '\'' +
                ", createTime=" + createTime +
                ", content='" + content + '\'' +
                ", tag='" + tag + '\'' +
                ", like=" + like +
                ", favorite=" + favorite +
                ", commentNum=" + commentNum +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public float getHot() {
        return hot;
    }

    public void setHot(float hot) {
        this.hot = hot;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }
}
