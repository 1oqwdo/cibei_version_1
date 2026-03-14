package cibei.mapper;

import cibei.entity.TravelPlan;
import java.util.List;

/**
 * travel_plans 表 Mapper 接口
 */
public interface TravelPlanMapper {

    /** 新增旅行计划 */
    int insert(TravelPlan plan);

    /** 根据ID查询 */
    TravelPlan selectById(String id);

    /** 查询用户的所有计划 */
    List<TravelPlan> selectByUserId(String userId);

    /** 查询用户当前选中的计划（is_current = true） */
    TravelPlan selectCurrentByUserId(String userId);

    /** 更新计划信息 */
    int update(TravelPlan plan);

    /** 将用户所有计划的 is_current 置为 false */
    int clearCurrentByUserId(String userId);

    /** 根据ID删除 */
    int deleteById(String id);

    /** 删除用户的所有计划 */
    int deleteByUserId(String userId);
}
