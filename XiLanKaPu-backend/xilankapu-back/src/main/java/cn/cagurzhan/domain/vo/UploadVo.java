package cn.cagurzhan.domain.vo;

import lombok.Data;

/**
 * @author Cagur Zhan
 */
@Data
public class UploadVo {
    /**
     * 纹样ID
     */
    private Long id;
    /**
     * 图片类型
     *  0: pic_main
     *  1: pic_simple
     *  2: pic_color
     */
    private Integer type;
    /**
     * 图片地址
     */
    private String url;
}
