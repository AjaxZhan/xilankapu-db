package cn.cagurzhan.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Set;

/**
 * @author Cagur Zhan
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InfoVo {
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户角色
     */
    private Set<String> roleName;
    /**
     * 邮箱地址
     */
    private String email;

}
