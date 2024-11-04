package cn.cagurzhan.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Cagur Zhan
 */
@Data
public class RoleDTO implements Serializable {
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限
     */
    private String roleKey;
}
