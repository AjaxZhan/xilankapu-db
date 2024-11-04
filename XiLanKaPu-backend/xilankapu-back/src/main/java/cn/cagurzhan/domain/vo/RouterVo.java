package cn.cagurzhan.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * 路由信息
 * @author Cagur Zhan
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RouterVo {

    /**
     * 路径
     */
    private String path;

    /**
     * 路由名字
     */
    private String name;

    /**
     * 重定向，如果是M就可以加path
     */
    private String redirect;

    /**
     * 元信息
     */
    private MetaVo meta;

    /**
     * 子结点
     */
    private List<RouterVo> children;
}
