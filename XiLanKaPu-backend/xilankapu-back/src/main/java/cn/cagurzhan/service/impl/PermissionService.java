package cn.cagurzhan.service.impl;

import cn.cagurzhan.domain.entity.User;
import cn.cagurzhan.service.MenuService;
import cn.cagurzhan.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户权限处理
 * @author Cagur Zhan
 */
@Service
@RequiredArgsConstructor
public class PermissionService {

    private final RoleService roleService;
    private final MenuService menuService;

    /**
     * 获取角色的角色标识符
     * @param user 用户
     * @return 权限标识字符串集合
     */
    public Set<String> getRolePermission(User user){
        Set<String> roles = new HashSet<>();
        if(user.isAdmin()){
            roles.add("admin");
        }else{
            roles.addAll(roleService.selectRolePermissionByUserId(user.getUserId()));
        }
        return roles;
    }

    /**
     * 获取用户的菜单权限标识符
     * @param user 用户
     * @return 菜单权限标识符
     */
    public Set<String> getMenuPermission(User user) {
        HashSet<String> perms = new HashSet<>();
        if(user.isAdmin()){
            perms.add("*:*:*");
        }else{
            perms.addAll(menuService.selectMenuPermsByUserId(user.getUserId()));
        }
        return perms;
    }   
}
