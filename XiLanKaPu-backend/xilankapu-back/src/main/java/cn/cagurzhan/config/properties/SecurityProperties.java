package cn.cagurzhan.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Cagur Zhan
 */
@Data
@Component
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    /**
     * 排除路径
     */
    private String[] excludes;
}
