package cn.cagurzhan.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 七牛云OSS配置
 * @author Cagur
 */
@Component
@Data
@ConfigurationProperties( prefix = "oss")
public class OssProperties {
    // ACK
    private String accessKey;
    // SK
    private String secretKey;
    // 桶名
    private String bucket;
    // 域名
    private String domain;
}
