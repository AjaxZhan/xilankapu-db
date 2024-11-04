package cn.cagurzhan.domain.entity;

import java.util.Date;

import java.io.Serializable;

import cn.cagurzhan.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Pattern)表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_pattern")
public class Pattern extends BaseEntity {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 纹样分类ID
     */
    @TableField(updateStrategy = FieldStrategy.NOT_NULL)
    private Long pclassId;
    /**
     * 纹样分类名字
     */
    @TableField(exist = false,updateStrategy = FieldStrategy.NOT_NULL)
    private PClass pClass;
    /**
     * 皮尔斯三性分类
     */
    @TableField(updateStrategy = FieldStrategy.NOT_NULL)
    private Integer piClass;
    /**
     * 纹样名字
     */
    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private String name;
    /**
     * 来源
     */
    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private String src;
    /**
     * 语构
     */
    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private String yugou;
    /**
     * 语义
     */
    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private String yuyi;
    /**
     * 语用
     */
    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private String yuyong;
    /**
     * 主图片
     */
    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private String picMain;
    /**
     * 提炼图
     */
    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private String picSimple;
    /**
     * 色彩图
     */
    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private String picColor;
    /**
     * 删除标注
     */
    @TableLogic
    @JsonIgnore
    private String delFlag;



}
