package cn.cagurzhan.domain.entity;

import java.util.Date;

import java.io.Serializable;

import cn.cagurzhan.constant.UserConstants;
import cn.cagurzhan.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 角色表实体
 *
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
public class Role extends BaseEntity {
    /**
     * 角色ID
     */
    @TableId(value = "role_id")
    private Long roleId;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @Size(min = 0, max = 30, message = "角色名称长度不能超过{max}个字符")
    private String roleName;

    /**
     * 角色权限
     */
    @NotBlank(message = "权限字符不能为空")
    @Size(min = 0, max = 100, message = "权限字符长度不能超过{max}个字符")
    private String roleKey;

    /**
     * 角色排序
     */
    @NotNull(message = "显示顺序不能为空")
    private Integer roleSort;


    /**
     * 角色状态（0正常 1停用）
     */
    @JsonIgnore
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    @JsonIgnore
    private String delFlag;

    /**
     * 备注
     */
    @JsonIgnore
    private String remark;

    /**
     * 用户是否存在此角色标识 默认不存在
     */
    @TableField(exist = false)
    @JsonIgnore
    private boolean flag = false;

    /**
     * 菜单组
     */
    @TableField(exist = false)
    private Long[] menuIds;

    public Role(Long roleId) {
        this.roleId = roleId;
    }

    public boolean isAdmin() {
        return UserConstants.ADMIN_ID.equals(this.roleId);
    }



}
