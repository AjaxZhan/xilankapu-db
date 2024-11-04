package cn.cagurzhan.controller;

import cn.cagurzhan.constant.Constant;
import cn.cagurzhan.domain.R;
import cn.cagurzhan.domain.entity.Menu;
import cn.cagurzhan.domain.model.LoginBody;
import cn.cagurzhan.domain.model.LoginUser;
import cn.cagurzhan.domain.vo.InfoVo;
import cn.cagurzhan.domain.vo.RouterVo;
import cn.cagurzhan.helper.LoginHelper;
import cn.cagurzhan.service.MenuService;
import cn.cagurzhan.service.UserService;
import cn.cagurzhan.service.impl.LoginService;
import cn.dev33.satoken.annotation.SaIgnore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 登录验证控制器
 *
 * @author Cagur Zhan
 */
@RestController
@Slf4j
@RequiredArgsConstructor // 采用final的方式注入服务
public class LoginController {

    private final UserService userService;
    private final LoginService loginService;
    private final MenuService menuService;

    /**
     * 登录接口
     * @return 登录信息（包含jwt）
     */
    @SaIgnore
    @PostMapping("/login")
    public R<Map<String,Object>> login(@Validated @RequestBody LoginBody loginBody){
        HashMap<String, Object> loginVo = new HashMap<>();
        // 调用login方法，生成token
        String accessToken = loginService.login(loginBody.getUsername(), loginBody.getPassword());
        LoginUser loginUser = LoginHelper.getLoginUser();
        loginVo.put("username",loginUser.getUsername());
        Set<String> rolePermission = loginUser.getRolePermission();
        loginVo.put("roles",rolePermission);
        loginVo.put(Constant.TOKEN,accessToken);
        return R.ok(loginVo);
    }

    /**
     * 用户信息接口
     * @return 用户信息
     */
    @GetMapping("/getInfo")
    public R<InfoVo> getInfo(){
        InfoVo infoVo = userService.buildLoginUserInfo();
        return R.ok(infoVo);
    }

    @GetMapping("/getRouters")
    public R<List<RouterVo>> getRouters(){
        Long userId = LoginHelper.getUserId();
        List<Menu> menus = menuService.selectMenuTreeByUserId(userId);
        List<RouterVo> routerVos = menuService.buildMenus(menus);
        return R.ok(routerVos);
    }

    @SaIgnore
    @PostMapping("/logout")
    public R<Void> logout(){
        loginService.logout();
        return R.ok();
    }


}
