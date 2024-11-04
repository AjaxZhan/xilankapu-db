package cn.cagurzhan.domain.entity;

import java.util.Date;

import java.io.Serializable;
import java.util.List;

import cn.cagurzhan.constant.UserConstants;
import cn.cagurzhan.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 系统用户实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class User extends BaseEntity {

    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long userId;

    /**
     * 用户名
     */
    @NotBlank(message = "用户账号不能为空")
    @Size(min = 0, max = 30, message = "用户账号长度不能超过{max}个字符")
    private String userName;

    /**
     * 昵称
     */
    @NotBlank(message = "用户昵称不能为空")
    @Size(min = 0, max = 30, message = "用户昵称长度不能超过{max}个字符")
    private String nickName;

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过{max}个字符")
    private String email;

    /**
     * 手机号
     */
    private String phonenumber;

    /**
     *用户类型
     */
    private String userType;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 0男1女
     */
    private String sex;

    /**
     * 密码
     */
    @TableField(
            insertStrategy = FieldStrategy.NOT_EMPTY,
            updateStrategy = FieldStrategy.NOT_EMPTY,
            whereStrategy = FieldStrategy.NOT_EMPTY
    )
    private String password;

    /* 我暂时不理解为何在这里加个这个。*/
    @JsonIgnore
    @JsonProperty
    public String getPassword() {
        return password;
    }

    /**
     * 标志：0正常1停用
     */
    private String status;

    /**
     * 删除标志
     */
    @TableLogic
    private String delFlag;

    /**
     * 最后登录IP
     */
    private String loginIp;
    /**
     * 最后登录时间
     */
    private Date loginDate;
    /**
     * 评论
     */
    private String remark;


    /**
     * 角色对象
     */
    @TableField(exist = false)
    private List<Role> roles;

    /**
     * 角色组
     */
    @TableField(exist = false)
    private Long[] roleIds;

    /**
     * 数据权限 当前角色ID
     */
    @TableField(exist = false)
    private Long roleId;


    public User(Long userId) {
        this.userId = userId;
    }

    // 判断是否为管理员
    public boolean isAdmin() {
        return UserConstants.ADMIN_ID.equals(this.userId);
    }

}
