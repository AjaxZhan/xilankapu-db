package cn.cagurzhan.service;

import cn.cagurzhan.domain.entity.Menu;
import cn.cagurzhan.domain.vo.RouterVo;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 菜单业务层
 *
 * @author Cagur Zhan
 */

public interface MenuService {
    Set<String> selectMenuPermsByUserId(Long userId);

    /**
     * 根据用户ID查询菜单树信息
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<Menu> selectMenuTreeByUserId(Long userId);

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    List<RouterVo> buildMenus(List<Menu> menus);

    Set<String> selectAuthByMenuId(Long menuId);
}
