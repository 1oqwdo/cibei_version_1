-- ========================
-- 用户表
CREATE TABLE IF NOT EXISTS users (
    id         VARCHAR(64)  NOT NULL                   COMMENT '主键，用户唯一ID',
    openid     VARCHAR(64)           DEFAULT NULL      COMMENT '微信 openid',
    phone      VARCHAR(20)           DEFAULT NULL      COMMENT '手机号',
    password   VARCHAR(255)          DEFAULT NULL      COMMENT '密码（bcrypt加密）',
    nickname   VARCHAR(64)           DEFAULT '匿名用户' COMMENT '用户昵称',
    avatar_url VARCHAR(255)          DEFAULT NULL      COMMENT '用户头像地址',
    created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    last_login DATETIME              DEFAULT NULL      COMMENT '最近登录时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_openid (openid),
    UNIQUE KEY uk_phone (phone)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ========================
-- 用户设置表
CREATE TABLE IF NOT EXISTS user_settings (
    id               VARCHAR(64)  NOT NULL                   COMMENT '主键',
    user_id          VARCHAR(64)  NOT NULL                   COMMENT '外键，关联 users.id',
    target_quit_date DATE                  DEFAULT NULL      COMMENT '用户设定的辞职目标日期',
    next_destination VARCHAR(128)          DEFAULT NULL      COMMENT '下一站旅游目的地',
    created_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_id (user_id),
    CONSTRAINT fk_user_settings_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户设置表';

-- ========================
-- 旅行计划表
CREATE TABLE IF NOT EXISTS travel_plans (
    id               VARCHAR(64)   NOT NULL                   COMMENT '主键',
    user_id          VARCHAR(64)   NOT NULL                   COMMENT '外键，关联 users.id',
    destination      VARCHAR(128)  NOT NULL                   COMMENT '目的地名称',
    plan_details     TEXT                   DEFAULT NULL      COMMENT '详细计划内容',
    is_current       TINYINT(1)    NOT NULL DEFAULT 0         COMMENT '是否为当前选中的下一站',
    generated_by_ai  TINYINT(1)    NOT NULL DEFAULT 0         COMMENT '是否为AI生成',
    created_at       DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at       DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    PRIMARY KEY (id),
    INDEX idx_user_id (user_id),
    INDEX idx_user_current (user_id, is_current),
    CONSTRAINT fk_travel_plans_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='旅行计划表';

-- ========================
-- 匿名吐槽表
CREATE TABLE IF NOT EXISTS rants (
    id          VARCHAR(64)  NOT NULL                   COMMENT '主键',
    content     TEXT         NOT NULL                   COMMENT '吐槽文本内容',
    voice_url   VARCHAR(255)          DEFAULT NULL      COMMENT '语音吐槽音频地址',
    image_url   VARCHAR(255)          DEFAULT NULL      COMMENT '图片吐槽图片地址',
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '吐槽时间',
    ip_location VARCHAR(64)           DEFAULT NULL      COMMENT 'IP地区，趣味统计用',
    PRIMARY KEY (id),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='匿名吐槽表';
