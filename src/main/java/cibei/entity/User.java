package cibei.entity;

import lombok.Data;
import java.util.Date;

/**
 * 用户实体类，对应 users 表
 */
@Data
public class User {

    /** 主键，用户唯一ID */
    private String id;

    /** 微信 openid（微信登录时使用） */
    private String openid;

    /** 手机号 */
    private String phone;

    /** 密码（bcrypt 加密存储） */
    private String password;

    /** 用户昵称，默认"匿名用户" */
    private String nickname;

    /** 用户头像地址 */
    private String avatarUrl;

    /** 注册时间 */
    private Date createdAt;

    /** 最近登录时间 */
    private Date lastLogin;
}
