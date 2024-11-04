package cn.cagurzhan.domain.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Release)表实体类
 *
 * @author makejava
 * @since 2024-01-24 22:51:38
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_release")
public class Release  {
    @TableId
    private Long id;
    /**
     * 更新时间
     */
    private Date createAt;
    /**
     * 发布时间
     */
    private Date publishedAt;
    /**
     * 更新内容
     */
    private String body;



}
