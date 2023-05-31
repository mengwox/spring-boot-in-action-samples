package com.mawenhao.springbootinaction.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * amazon配置
 *
 * @author mawenhao 2023/5/30
 */
@Component
@ConfigurationProperties(prefix = "amazon")
@Data
public class AmazonProperties {
    private String associateId;
}
