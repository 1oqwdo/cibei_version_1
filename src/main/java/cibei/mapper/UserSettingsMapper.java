package cibei.mapper;

import cibei.entity.UserSettings;

/**
 * user_settings 表 Mapper 接口
 */
public interface UserSettingsMapper {

    /** 新增用户设置 */
    int insert(UserSettings settings);

    /** 根据ID查询 */
    UserSettings selectById(String id);

    /** 根据 userId 查询（一对一，返回单条） */
    UserSettings selectByUserId(String userId);

    /** 更新用户设置 */
    int update(UserSettings settings);

    /** 根据ID删除 */
    int deleteById(String id);

    /** 根据 userId 删除 */
    int deleteByUserId(String userId);
}
