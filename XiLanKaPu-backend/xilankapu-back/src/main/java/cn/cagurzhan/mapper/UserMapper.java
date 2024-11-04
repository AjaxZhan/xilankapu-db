package cn.cagurzhan.mapper;

import cn.cagurzhan.domain.entity.User;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * 用户表数据库访问层
 */
@Mapper
public interface UserMapper extends BaseMapperPlus<UserMapper,User,User> {
    /**
     * 通过用户ID，查询所有的用户信息，包括权限。
     * @param userId 用户ID
     * @return 用户信息
     */
    User selectUserById(Long userId);

    Page<User> selectPageUserList(@Param("page") Page<User> user, @Param(Constants.WRAPPER)Wrapper<User> userWrapper);
}
