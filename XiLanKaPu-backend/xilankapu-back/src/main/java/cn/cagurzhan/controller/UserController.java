package cn.cagurzhan.controller;

import cn.cagurzhan.domain.R;
import cn.cagurzhan.domain.entity.Role;
import cn.cagurzhan.domain.entity.User;
import cn.cagurzhan.domain.page.PageQuery;
import cn.cagurzhan.domain.page.PureDataInfo;
import cn.cagurzhan.exception.ServiceException;
import cn.cagurzhan.helper.LoginHelper;
import cn.cagurzhan.mapper.RoleMapper;
import cn.cagurzhan.service.RoleService;
import cn.cagurzhan.service.UserService;
import cn.cagurzhan.utils.StreamUtils;
import cn.cagurzhan.utils.StringUtils;
import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.util.ArrayUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Cagur Zhan
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController extends BaseController {

    private final UserService userService;
    private final RoleService roleService;

    /**
     * 分页查询用户信息
     */
    @GetMapping("/list")
    public PureDataInfo<User> list(User user, PageQuery pageQuery){
        return userService.queryPageList(user,pageQuery);
    }

    /**
     * 新增用户
     */
    @PostMapping
    public R<Void> add(@Validated @RequestBody User user){
        if (!userService.checkUserNameUnique(user)) {
            return R.fail("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        }else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user)) {
            return R.fail("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setPassword(BCrypt.hashpw(user.getPassword()));
        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改用户
     */
    @PutMapping
    public R<Void> edit(@RequestBody User user){
        // 校验是否为本人 / admin
        userService.checkUserAllowed(user);
        // 校验重复性
        if (!userService.checkUserNameUnique(user)) {
            return R.fail("修改用户'" + user.getUserName() + "'失败，登录账号已存在");
        }else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user)) {
            return R.fail("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        return toAjax(userService.updateUser(user));
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{userIds}")
    public R<Void> remove(@PathVariable Long[] userIds){
        if (ArrayUtil.contains(userIds, getUserId())) {
            return R.fail("当前用户不能删除");
        }
        return toAjax(userService.deleteUserByIds(userIds));
    }

    /**
     * 重置密码
     */
    @PutMapping("/resetPwd")
    public R<Void> resetPwd(@RequestBody User user){
        userService.checkUserAllowed(user);
        user.setPassword(BCrypt.hashpw(user.getPassword()));
        return toAjax(userService.resetPwd(user));
    }

    /**
     *状态修改
     */
    @PutMapping("/changeStatus")
    public R<Void> changeStatus(@RequestBody User user) {
        userService.checkUserAllowed(user);
        return toAjax(userService.updateUserStatus(user));
    }

    /**
     * 给用户进行角色授权
     */
    @PutMapping("/authRole")
    public R<Void> insertAuthRole(Long userId, Long[] roleIds) {

        if(!LoginHelper.isAdmin()) {
            throw new ServiceException("非admin，无权进行授权操作");
        }
        userService.insertUserAuth(userId, roleIds);
        return R.ok();
    }

    @GetMapping("/roles")
    public R<List<Role>> getAllRoles(){
        List<Role> list = roleService.list();
        return R.ok(list);
    }




}
