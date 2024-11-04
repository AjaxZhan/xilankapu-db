package cn.cagurzhan.demo;

import cn.cagurzhan.domain.R;
import cn.cagurzhan.domain.entity.User;
import cn.cagurzhan.mapper.UserMapper;
import cn.cagurzhan.service.impl.PermissionService;
import cn.dev33.satoken.annotation.SaIgnore;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author Cagur Zhan
 */
@RestController
@RequiredArgsConstructor
public class TestPermissionController {

    private final PermissionService permissionService;
    private final UserMapper userMapper;


    @SaIgnore
    @GetMapping("/test/role")
    public R<Set<String>> testGetRolePermission(){
        User user = new User();
        user.setUserId(1L);
        Set<String> rolePermission = permissionService.getRolePermission(user);
        return R.ok(rolePermission);
    }

    @SaIgnore
    @GetMapping("/test/menu")
    public R<Set<String>> testGetMenuPermission(){
        User user = new User(2L);
        Set<String> menuPermission = permissionService.getMenuPermission(user);
        return R.ok(menuPermission);
    }

    @SaIgnore
    @GetMapping("/test/user")
    public R<User> testUser(){
        User user = userMapper.selectUserById(2L);
        return R.ok(user);
    }
}
