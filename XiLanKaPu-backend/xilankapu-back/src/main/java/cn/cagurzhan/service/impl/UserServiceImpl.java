package cn.cagurzhan.service.impl;

import cn.cagurzhan.constant.UserConstants;
import cn.cagurzhan.domain.dto.RoleDTO;
import cn.cagurzhan.domain.entity.User;
import cn.cagurzhan.domain.entity.UserRole;
import cn.cagurzhan.domain.page.PageQuery;
import cn.cagurzhan.domain.page.PureDataInfo;
import cn.cagurzhan.domain.vo.InfoVo;
import cn.cagurzhan.exception.ServiceException;
import cn.cagurzhan.helper.LoginHelper;
import cn.cagurzhan.mapper.UserMapper;
import cn.cagurzhan.mapper.UserRoleMapper;
import cn.cagurzhan.service.UserService;
import cn.cagurzhan.utils.StreamUtils;
import cn.cagurzhan.utils.StringUtils;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * 用户表服务实现
 */
@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;

    @Override
    public User selectUserById(Long userId) {
        return userMapper.selectUserById(userId);
    }

    @Override
    public InfoVo buildLoginUserInfo() {
        User user = getById(LoginHelper.getUserId());
        InfoVo vo = new InfoVo();

        vo.setNickname(user.getNickName());
        vo.setUsername(user.getUserName());
        vo.setEmail(user.getEmail());
        vo.setAvatar(user.getAvatar());

        HashSet<String> roleNames = new HashSet<>();

        List<RoleDTO> roles = LoginHelper.getLoginUser().getRoles();
        for (RoleDTO role : roles) {
            roleNames.add(role.getRoleName());
        }
        vo.setRoleName(roleNames);

        return vo;
    }

    /**
     * 分页查询用户信息
     */
    @Override
    public PureDataInfo<User> queryPageList(User user, PageQuery pageQuery) {
        Page<User> page = userMapper.selectPageUserList(pageQuery.build(), this.buildQueryWrapper(user));
        return PureDataInfo.build(page);
    }

    /**
     * 检查用户名是否唯一
     */
    @Override
    public boolean checkUserNameUnique(User user) {
        boolean exist = userMapper.exists(new LambdaQueryWrapper<User>()
                .eq(User::getUserName, user.getUserName())
                .ne(ObjectUtil.isNotNull(user.getUserId()), User::getUserId, user.getUserId()));
        return !exist;
    }

    /**
     * 检查邮箱是否唯一
     */
    @Override
    public boolean checkEmailUnique(User user) {
        boolean exist = userMapper.exists(new LambdaQueryWrapper<User>()
                .eq(User::getEmail, user.getEmail())
                .ne(ObjectUtil.isNotNull(user.getUserId()), User::getUserId, user.getUserId()));
        return !exist;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertUser(User user) {
        user.setDelFlag("0");
        // 新增用户信息
        int rows = userMapper.insert(user);
        // 新增用户与角色管理
        insertUserRole(user);
        return rows;
    }

    @Override
    public void checkUserAllowed(User user) {
        // TODO 这里并不安全，暂时只做了动态路由，没有做数据权限，没有做权限校验
        if(LoginHelper.isAdmin()) {
            return ;
        }
        if (!user.getUserId().equals(LoginHelper.getUserId())){
            throw new ServiceException("非法操作，请勿操作他人账户");
        }
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUser(User user) {
        Long userId = user.getUserId();
        // 删除用户与角色关联
        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId));
        // 新增用户与角色管理
        insertUserRole(user);
        return userMapper.updateById(user);
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteUserByIds(Long[] userIds) {
        for (Long userId : userIds) {
            checkUserAllowed(new User(userId));
        }
        List<Long> ids = Arrays.asList(userIds);
        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().in(UserRole::getUserId, ids));
        return userMapper.deleteBatchIds(ids);
    }

    @Override
    public int resetPwd(User user) {
        return userMapper.updateById(user);
    }

    @Override
    public int updateUserStatus(User user) {
        return userMapper.updateById(user);
    }

    /**
     * 用户授权角色
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertUserAuth(Long userId, Long[] roleIds) {
        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userId));
        insertUserRole(userId, roleIds);
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(User user) {
        this.insertUserRole(user.getUserId(), user.getRoleIds());
    }
    /**
     * 新增用户角色信息
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    public void insertUserRole(Long userId, Long[] roleIds) {
        if (ArrayUtil.isNotEmpty(roleIds)) {
            // 新增用户与角色管理
            List<UserRole> list = StreamUtils.toList(Arrays.asList(roleIds), roleId -> {
                UserRole ur = new UserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                return ur;
            });
            userRoleMapper.insertBatch(list);
        }
    }

    private Wrapper<User> buildQueryWrapper(User user) {
        Map<String, Object> params = user.getParams();
        QueryWrapper<User> wrapper = Wrappers.query();
        wrapper.eq("u.del_flag", UserConstants.USER_NORMAL)
                .eq(ObjectUtil.isNotNull(user.getUserId()), "u.user_id", user.getUserId())
                .like(StringUtils.isNotBlank(user.getUserName()), "u.user_name", user.getUserName())
                .eq(StringUtils.isNotBlank(user.getStatus()), "u.status", user.getStatus())
                .like(StringUtils.isNotBlank(user.getPhonenumber()), "u.phonenumber", user.getPhonenumber())
                .between(params.get("beginTime") != null && params.get("endTime") != null,
                        "u.create_time", params.get("beginTime"), params.get("endTime"));
        return wrapper;
    }
}

