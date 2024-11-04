package cn.cagurzhan.domain.entity;

import java.util.Date;

import java.io.Serializable;

import cn.cagurzhan.domain.BaseEntity;
import cn.cagurzhan.domain.TreeEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
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
 * 菜单实体
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
public class Menu extends TreeEntity<Menu> {

    /**
     * 菜单ID
     */
    @TableId
    private Long menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 显示顺序
     */
    private Integer orderNum;
    /**
     * 路由地址
     */
    private String path;
    /**
     * 组件路径
     */
    private String component;
    /**
     * 是否为外链（0是 1否）
     */
    private Integer isFrame;
    /**
     * 是否缓存（0缓存 1不缓存）
     */
    private Integer isCache;
    /**
     * 类型（M目录 C菜单 F按钮）
     */
    private String menuType;
    /**
     * 显示状态（0显示 1隐藏）
     */
    private String visible;
    /**
     * 菜单状态（0正常 1停用）
     */
    private String status;
    /**
     * 权限字符串
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String perms;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 是否固定，0固定，1固定
     */
    private Integer affix;
    /**
     * 备注
     */
    private String remark;



}
