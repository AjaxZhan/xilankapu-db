package cn.cagurzhan.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Cagur Zhan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReleaseVo {
    private Date create_at;
    private Date published_at;
    private String body;

}
