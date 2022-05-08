package cn.edu.jxnu.service.util;

public interface ServerConstant {

    /**
     * 主题: 点赞
     */
    String TOPIC_LIKE = "like";
    /**
     * 主题: 关注
     */
    String TOPIC_COMMENT = "comment";

    /**
     * 主题: 发日记
     */
    String TOPIC_PUBLISH = "publish";

    /**
     * 主题: 删日记
     */
    String TOPIC_DELETE = "delete";
    /**
     * 默认状态的登陆凭证超过时间
     */
    long DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    /**
     * 用户身份: 普通用户
     */
    String AUTHORITY_USER = "user";

    String DOMAIN = "http://10.128.77.206:4000";

}
