package cibei.entity;

import lombok.Data;
import java.util.Date;

/**
 * 用户设置实体类，对应 user_settings 表，与 users 一对一
 */
@Data
public class UserSettings {

    /** 主键 */
    private String id;

    /** 外键，关联 users.id */
    private String userId;

    /** 用户设定的辞职目标日期 */
    private Date targetQuitDate;

    /** 首页展示的下一站旅游目的地，如"大理" */
    private String nextDestination;

    /** 创建时间 */
    private Date createdAt;

    /** 最后更新时间 */
    private Date updatedAt;
}
