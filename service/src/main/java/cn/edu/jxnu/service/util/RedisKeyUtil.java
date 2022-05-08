package cn.edu.jxnu.service.util;

public class RedisKeyUtil {

    private static final String SPLIT = ":";
    private static final String PREFIX_TOKEN = "token";
    private static final String PREFIX_FOLLOW = "follow";
    private static final String PREFIX_FANS = "fans";
    private static final String PREFIX_LIKE_COMMENT = "like:comment";
    private static final String PREFIX_LIKE_DIARY = "like:diary";
    private static final String PREFIX_DIARY = "diary";
    private static final String PREFIX_USER_PHOTO = "user:photo";
    private static final String PREFIX_DIARY_PHOTO = "diary:photo";
    private static final String PREFIX_LOCATION_KEYWORD = "location";
    private static final String PREFIX_SEARCH_KEYWORD = "search:keyword";
    private static final String PREFIX_USER = "user";

    // 登录凭证
    public static String getTokenKey(String token) {
        return PREFIX_TOKEN + SPLIT + token;
    }

    // 某个用户关注的实体
    // follow:userId -> zset(userId, now)
    public static String getFollowKey(int userId) {
        return PREFIX_FOLLOW + SPLIT + userId;
    }

    // 某个用户拥有的粉丝
    // fans:userId -> zset(userId, now)
    public static String getFansKey(int userId) {
        return PREFIX_FANS + SPLIT + userId;
    }

    // 某个评论下的点赞
    public static String getLikeCommentKey(int commentId) {
        return PREFIX_LIKE_COMMENT + SPLIT + commentId;
    }

    // 某个日记下的点赞
    public static String getLikeDiaryKey(int diaryId) {
        return PREFIX_LIKE_DIARY + SPLIT + diaryId;
    }

    // 日记分数
    public static String getDiaryScoreKey() {
        return PREFIX_LIKE_DIARY + SPLIT + "score";
    }

    // 用户主页图片
    public static String getUserPhotoKey(int userId) {
        return PREFIX_USER_PHOTO + SPLIT + userId;
    }

    // 日记图片
    public static String getDiaryPhotoKey(int diaryId) {
        return PREFIX_DIARY_PHOTO + SPLIT + diaryId;
    }

    // 地点词频
    public static String getLocationKeywordKey(int locationId) {
        return PREFIX_LOCATION_KEYWORD + SPLIT + locationId;
    }

    // 搜索关键字词频
    public static String getSearchKeywordKey() {
        return PREFIX_SEARCH_KEYWORD;
    }

    // 用户缓存
    public static String getUserKey(int userId) {
        return PREFIX_USER + SPLIT + userId;
    }

}
