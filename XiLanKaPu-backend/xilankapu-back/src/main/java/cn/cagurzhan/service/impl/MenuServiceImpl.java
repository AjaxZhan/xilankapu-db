package cn.cagurzhan.service.impl;

import cn.cagurzhan.constant.UserConstants;
import cn.cagurzhan.domain.entity.Menu;
import cn.cagurzhan.domain.model.LoginUser;
import cn.cagurzhan.domain.vo.MetaVo;
import cn.cagurzhan.domain.vo.RouterVo;
import cn.cagurzhan.helper.LoginHelper;
import cn.cagurzhan.mapper.MenuMapper;
import cn.cagurzhan.service.MenuService;
import cn.cagurzhan.utils.StreamUtils;
import cn.cagurzhan.utils.StringUtils;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Cagur Zhan
 */

@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper,Menu> implements MenuService {

    private final MenuMapper menuMapper;

    @Override
    public Set<String> selectMenuPermsByUserId(Long userId) {
        List<String> perms = menuMapper.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(StringUtils.splitList(perm.trim()));
            }
        }
        return permsSet;
    }

    @Override
    public List<Menu> selectMenuTreeByUserId(Long userId) {
        List<Menu> menus = null;
        if (LoginHelper.isAdmin(userId)) {
            menus = menuMapper.selectMenuTreeAll();
        } else {
            menus = menuMapper.selectMenuTreeByUserId(userId);
        }
        return getChildPerms(menus, 0);
    }

    private List<Menu> getChildPerms(List<Menu> list, int parentId) {
        List<Menu> returnList = new ArrayList<>();
        for (Menu t : list) {
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<Menu> list, Menu t) {
        // 得到子节点列表
        List<Menu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (Menu tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }
    /**
     * 得到子节点列表
     */
    private List<Menu> getChildList(List<Menu> list, Menu t) {
        return StreamUtils.filter(list, n -> n.getParentId().equals(t.getMenuId()));
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<Menu> list, Menu t) {
        return CollUtil.isNotEmpty(getChildList(list, t));
    }


    @Override
    public List<RouterVo> buildMenus(List<Menu> menus) {
        List<RouterVo> routers = new LinkedList<>();

        for(Menu menu : menus){
            RouterVo router = new RouterVo();
            List<Menu> cMenu = menu.getChildren();
            // path
            router.setPath(menu.getPath());
            // // redirect
            // if(menu.getParentId() == 0 && CollUtil.isNotEmpty(cMenu)){
            //     router.setRedirect(menu.getChildren().get(0).getPath());
            // }
            // name（子）
            if(menu.getMenuType().equals(UserConstants.TYPE_MENU)){
                router.setName(menu.getComponent());
            }
            // meta
            MetaVo metaVo = new MetaVo();
            metaVo.setTitle(menu.getMenuName());
            metaVo.setRank(menu.getOrderNum());
            if(StringUtils.isNotEmpty(menu.getIcon())) metaVo.setIcon(menu.getIcon());
            // 因为查到的路由已经是本人才能看到的了，所以直接给每个路由都塞上权限即可。
            metaVo.setRoles(LoginHelper.getLoginUser().getRolePermission());
            // 对于按钮级别的perms，我们收集起来添加到它的父亲的auths即可。
            // 假设最多只有二级菜单
            if(menu.getMenuType().equals(UserConstants.TYPE_MENU)){
                metaVo.setAuths(selectAuthByMenuId(menu.getMenuId()));
            }
            router.setMeta(metaVo);
            // children
            if(CollUtil.isNotEmpty(cMenu)){
                router.setChildren(buildMenus(cMenu));
            }
            routers.add(router);
        }

        return routers;
    }

    @Override
    public Set<String> selectAuthByMenuId(Long menuId) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Menu::getPerms);
        wrapper.eq(Menu::getMenuType,UserConstants.TYPE_BUTTON)
                .eq(Menu::getParentId,menuId)
                .eq(Menu::getStatus,UserConstants.MENU_NORMAL);
        return menuMapper.selectList(wrapper).stream().map(Menu::getPerms).collect(Collectors.toSet());
    }
}
