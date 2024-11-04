package cn.cagurzhan.service;

import cn.cagurzhan.domain.entity.User;
import cn.cagurzhan.domain.page.PageQuery;
import cn.cagurzhan.domain.page.PureDataInfo;
import cn.cagurzhan.domain.vo.InfoVo;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 用户表服务接口
 */
public interface UserService extends IService<User> {

    User selectUserById(Long userId);

    /**
     * 构建用户信息Vo
     * @return 用户信息Vo
     */
    InfoVo buildLoginUserInfo();

    /**
     * 查询用户列表
     * @param user 用户对象
     * @param pageQuery 分页查询
     * @return 用户列表
     */
    PureDataInfo<User> queryPageList(User user, PageQuery pageQuery);

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    boolean checkUserNameUnique(User user);

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    boolean checkEmailUnique(User user);

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int insertUser(User user);

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    void checkUserAllowed(User user);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int updateUser(User user);
    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    int deleteUserByIds(Long[] userIds);
    /**
     * 重置用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    int resetPwd(User user);
    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    int updateUserStatus(User user);
    /**
     * 用户授权角色
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    void insertUserAuth(Long userId, Long[] roleIds);
}
