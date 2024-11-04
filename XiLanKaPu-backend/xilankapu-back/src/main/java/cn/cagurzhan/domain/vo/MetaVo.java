package cn.cagurzhan.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Set;

/**
 * @author Cagur Zhan
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MetaVo {
    /**
     * 图标地址
     */
    private String icon;
    /**
     * 路由标题
     */
    private String title;

    /**
     * 排序
     */
    private Integer rank;

    /**
     * 角色
     */
    private Set<String> roles;

    /**
     * 权限
     */
    private Set<String> auths;

}
