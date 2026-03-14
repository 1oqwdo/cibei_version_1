package cibei.mapper;

import cibei.entity.Rant;
import java.util.List;

/**
 * rants 表 Mapper 接口
 */
public interface RantMapper {

    /** 新增吐槽 */
    int insert(Rant rant);

    /** 根据ID查询 */
    Rant selectById(String id);

    /** 查询最新吐槽列表（按时间倒序，分页） */
    List<Rant> selectLatest(@org.apache.ibatis.annotations.Param("offset") int offset,
                            @org.apache.ibatis.annotations.Param("limit")  int limit);

    /** 统计总数 */
    int count();

    /** 根据ID删除 */
    int deleteById(String id);
}
