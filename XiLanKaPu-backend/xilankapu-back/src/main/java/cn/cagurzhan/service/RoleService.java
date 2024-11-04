package cn.cagurzhan.service;

import cn.cagurzhan.domain.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.Set;

/**
 * 角色业务层
 * @author Cagur Zhan
 */

public interface RoleService extends IService<Role> {

    /**
     * 根据用户ID查询角色标识符
     * @param userId 用户ID
     * @return 角色标识符
     */
    Set<String> selectRolePermissionByUserId(Long userId);
}
