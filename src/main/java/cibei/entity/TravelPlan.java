package cibei.entity;

import lombok.Data;
import java.util.Date;

/**
 * 旅行计划实体类，对应 travel_plans 表
 */
@Data
public class TravelPlan {

    /** 主键 */
    private String id;

    /** 外键，关联 users.id */
    private String userId;

    /** 目的地名称，如"云南大理" */
    private String destination;

    /** 详细计划内容（手动录入或AI生成） */
    private String planDetails;

    /** 是否为当前选中的"下一站"（首页展示） */
    private Boolean isCurrent;

    /** 是否为AI生成 */
    private Boolean generatedByAi;

    /** 创建时间 */
    private Date createdAt;

    /** 最后更新时间 */
    private Date updatedAt;
}
