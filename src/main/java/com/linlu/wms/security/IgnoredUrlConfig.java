package com.linlu.wms.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 不需要认证的URL
 *
 * @author xi
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "auth.ignored")
public class IgnoredUrlConfig {
    private List<String> urls = new ArrayList<>();
}
