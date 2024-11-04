package cn.cagurzhan.mapper;

import cn.cagurzhan.domain.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Cagur Zhan
 */
@Mapper
public interface UserRoleMapper extends BaseMapperPlus<UserMapper, UserRole,UserRole> {
}
