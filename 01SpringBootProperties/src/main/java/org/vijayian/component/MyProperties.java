package org.vijayian.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * properties
 *
 * @author ViJay
 * @date 2021-01-16
 */
@Component
@ConfigurationProperties(prefix = "my")
@Data
public class MyProperties {
    private String name;
    private String address;
    private List<String> likes;
}
