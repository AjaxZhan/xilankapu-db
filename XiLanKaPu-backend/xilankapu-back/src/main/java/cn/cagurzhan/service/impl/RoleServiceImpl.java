package cn.cagurzhan.service.impl;

import cn.cagurzhan.domain.entity.Role;
import cn.cagurzhan.domain.entity.User;
import cn.cagurzhan.mapper.RoleMapper;
import cn.cagurzhan.mapper.UserMapper;
import cn.cagurzhan.service.RoleService;
import cn.cagurzhan.utils.StringUtils;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Cagur Zhan
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService  {

    private final RoleMapper roleMapper;

    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        List<Role> roles = roleMapper.selectRolePermissionByUserId(userId);
        HashSet<String> perms = new HashSet<>();
        for (Role role : roles) {
            if(ObjectUtil.isNotNull(role)){
                perms.addAll(StringUtils.splitList(role.getRoleKey().trim()));
            }
        }
        return perms;
    }
}
