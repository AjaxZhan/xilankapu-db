package cn.cagurzhan.extra.satoken.service;

import cn.cagurzhan.constant.UserType;
import cn.cagurzhan.domain.model.LoginUser;
import cn.cagurzhan.helper.LoginHelper;
import cn.dev33.satoken.stp.StpInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * sa-token 权限管理实现类
 *
 * @author Lion Li
 */
public class SaPermissionImpl implements StpInterface {

    /**
     * 获取菜单权限列表
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        UserType userType = UserType.getUserType(loginUser.getUserType());
        if (userType == UserType.SYS_USER) {
            return new ArrayList<>(loginUser.getMenuPermission());
        }
        return new ArrayList<>();
    }

    /**
     * 获取角色权限列表
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        UserType userType = UserType.getUserType(loginUser.getUserType());
        if (userType == UserType.SYS_USER) {
            return new ArrayList<>(loginUser.getRolePermission());
        } else if (userType == UserType.APP_USER) {
            // 其他端 自行根据业务编写
        }
        return new ArrayList<>();
    }
}
