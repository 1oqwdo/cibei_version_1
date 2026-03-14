package cibei.entity;

import lombok.Data;
import java.util.Date;

/**
 * 匿名吐槽实体类，对应 rants 表，完全匿名不关联用户
 */
@Data
public class Rant {

    /** 主键 */
    private String id;

    /** 吐槽文本内容 */
    private String content;

    /** 语音吐槽的音频文件地址 */
    private String voiceUrl;

    /** 图片吐槽的图片地址 */
    private String imageUrl;

    /** 吐槽时间 */
    private Date createdAt;

    /** IP 地区，用于趣味统计 */
    private String ipLocation;
}
