package cibei.mapper;

import cibei.entity.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * users 表 Mapper 接口
 */
@Mapper
public interface UserMapper {

    /** 新增用户 */
    int insert(User user);

    /** 根据ID查询 */
    User selectById(String id);

    /** 根据 openid 查询 */
    User selectByOpenid(String openid);

    /** 根据手机号查询 */
    User selectByPhone(String phone);

    /** 查询所有用户 */
    List<User> selectAll();

    /** 更新用户信息 */
    int update(User user);

    /** 根据ID删除 */
    int deleteById(String id);
}
