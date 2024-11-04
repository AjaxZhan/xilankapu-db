package cn.cagurzhan.mapper;

import cn.cagurzhan.domain.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色映射表
 *
 * @author Cagur Zhan
 */
@Mapper
public interface RoleMapper extends BaseMapperPlus<RoleMapper, Role,Role> {

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<Role> selectRolePermissionByUserId(Long userId);
}
