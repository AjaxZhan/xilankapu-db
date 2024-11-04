package cn.cagurzhan.service.impl;

import cn.cagurzhan.constant.CacheConstants;
import cn.cagurzhan.constant.Constant;
import cn.cagurzhan.constant.LoginType;
import cn.cagurzhan.constant.UserStatus;
import cn.cagurzhan.domain.dto.RoleDTO;
import cn.cagurzhan.domain.entity.User;
import cn.cagurzhan.domain.model.LoginUser;
import cn.cagurzhan.exception.user.UserException;
import cn.cagurzhan.helper.LoginHelper;
import cn.cagurzhan.mapper.UserMapper;
import cn.cagurzhan.service.UserService;
import cn.cagurzhan.utils.DateUtils;
import cn.cagurzhan.utils.ServletUtils;
import cn.cagurzhan.utils.redis.RedisUtils;
import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.function.Supplier;

/**
 * 登录服务
 *
 * @author Cagur Zhan
 */

@RequiredArgsConstructor
@Service
@Slf4j
public class LoginService {

    private final UserMapper userMapper;
    private final PermissionService permissionService;

    @Value("${user.password.maxRetryCount}")
    private Integer maxRetryCount;

    @Value("${user.password.lockTime}")
    private Integer lockTime;


    /**
     * 登录验证方法
     *
     * @param username 用户名
     * @param password 密码
     * @return token值
     */
    public String login(String username, String password){
        // 从数据库获取User对象，校验是否存在
        User user = loadUserByUserName(username);
        // 检验密码与错误次数
        checkLogin(LoginType.PASSWORD,username,()->
            !BCrypt.checkpw(password,user.getPassword()));
        // 构建LoginUser
        LoginUser loginUser = buildLoginUser(user);
        // 生成token
        LoginHelper.login(loginUser);
        // 更新数据库登录信息
        updateUserLoginInfo(user.getUserId(),username);
        // TODO 记录登录日志
        // 返回token
        return StpUtil.getTokenValue();
    }

    /**
     * 辅助函数，用户登录后更新数据库登录信息
     * @param userId 用户ID
     * @param username 用户名
     */
    public void updateUserLoginInfo(Long userId, String username){
        User user = new User();
        user.setUserId(userId);
        user.setLoginIp(ServletUtils.getClientIP());
        user.setLoginDate(DateUtils.getNowDate());
        user.setUpdateBy(username);
        userMapper.updateById(user);
    }

    /**
     * 构建登录用户
     * @param user 系统用户
     * @return 登录用户
     */
    private LoginUser buildLoginUser(User user){
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(user.getUserId());
        loginUser.setUserType(user.getUserType());
        loginUser.setUserId(user.getUserId());
        loginUser.setUsername(user.getUserName());
        loginUser.setRolePermission(permissionService.getRolePermission(user));
        loginUser.setMenuPermission(permissionService.getMenuPermission(user));
        List<RoleDTO> roles = BeanUtil.copyToList(user.getRoles(), RoleDTO.class);
        loginUser.setRoles(roles);
        return loginUser;
    }

    /**
     * 登录验证，密码校验和错误次数校验
     * @param loginType 登录类型
     * @param username 用户名
     * @param supplier 生产型接口，创建对象并返回。检验密码的匿名函数
     */
    private void checkLogin(LoginType loginType,String username, Supplier<Boolean> supplier){
        String cacheErrorKey = CacheConstants.PWD_ERR_CNT_KEY + username;
        String loginFail = Constant.LOGIN_FAIL;
        // 获取登录错误次数
        int errorNum = ObjectUtil.defaultIfNull(RedisUtils.getCacheObject(cacheErrorKey),0);
        // 锁定时间内登录，踢掉
        if(errorNum >= maxRetryCount){
            // TODO 记录登陆日志
            throw new UserException(loginType.getRetryLimitExceed(),maxRetryCount,lockTime);
        }

        // 验证失败，外面是取反的表达式
        if(supplier.get()){
            errorNum++;
            RedisUtils.setCacheObject(cacheErrorKey,errorNum, Duration.ofMinutes(lockTime));
            if(errorNum >= maxRetryCount){
                // TODO 记录登录日志
                throw new UserException(loginType.getRetryLimitCount(),maxRetryCount,lockTime);
            }else{
                // TODO 记录日志
                throw new UserException(loginType.getRetryLimitCount(),errorNum);
            }
        }

        // 登录成功
        RedisUtils.deleteObject(cacheErrorKey);
    }


    /**
     * 辅助方法，根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户对象
     */
    private User loadUserByUserName(String username){
        // 第一次只查用户名和状态字段
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(User::getUserId,User::getUserName,User::getStatus)
                        .eq(User::getUserName,username);
        User user = userMapper.selectOne(wrapper);

        if(ObjectUtil.isNull(user)){
            log.info("登录用户：{}不存在",username);
            throw new UserException("user.not.exsits",username);
        }else if(UserStatus.DISABLE.getCode().equals(user.getStatus())){
            log.info("登录用户：{}已被停用",username);
            throw new UserException("user.blocked",username);
        }

        // 如果存在，查询所有信息
        return userMapper.selectUserById(user.getUserId());
    }

    /**
     * 退出登录
     */
    public void logout() {
        LoginUser loginUser = LoginHelper.getLoginUser();
        // TODO 记录登录信息，可能抛出用户未登录异常
        StpUtil.logout();
    }
}
