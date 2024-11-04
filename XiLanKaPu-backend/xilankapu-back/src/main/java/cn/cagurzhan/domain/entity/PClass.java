package cn.cagurzhan.domain.entity;

import cn.cagurzhan.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Cagur Zhan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_pclass")
public class PClass extends BaseEntity {
    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 分类名
     */
    private String pclass;
    /**
     * 删除标志
     */
    @TableLogic
    @JsonIgnore
    private String delFlag;
}
