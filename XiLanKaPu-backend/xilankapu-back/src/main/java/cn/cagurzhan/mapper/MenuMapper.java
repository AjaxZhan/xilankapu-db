package cn.cagurzhan.mapper;

import cn.cagurzhan.constant.UserConstants;
import cn.cagurzhan.domain.entity.Menu;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * @author Cagur Zhan
 */
@Mapper
public interface MenuMapper extends BaseMapperPlus<MenuMapper, Menu,Menu>{

    List<String> selectMenuPermsByUserId(Long userId);

    default List<Menu> selectMenuTreeAll(){
        LambdaQueryWrapper<Menu> lqw = new LambdaQueryWrapper<Menu>()
                .in(Menu::getMenuType, UserConstants.TYPE_DIR, UserConstants.TYPE_MENU)
                .eq(Menu::getStatus, UserConstants.MENU_NORMAL)
                .orderByAsc(Menu::getParentId)
                .orderByAsc(Menu::getOrderNum);
        return this.selectList(lqw);
    }
    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<Menu> selectMenuTreeByUserId(Long userId);
}
