package com.linlu.wms.security;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * URL路径工具
 *
 * @author xi
 */
public class AntPathUtil {
    private AntPathUtil() {
    }

    public static final AntPathMatcher MATCHER = createMatcher();

    public static boolean match(String path, List<String> permitUrlList) {
        if (CollectionUtils.isEmpty(permitUrlList)) {
            return true;
        }
        if (!StringUtils.hasText(path)) {
            return false;
        }
        return permitUrlList.stream().filter(p -> MATCHER.match(p, path)).findAny().isPresent();
    }

    private static AntPathMatcher createMatcher() {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        antPathMatcher.setCaseSensitive(false);
        return antPathMatcher;
    }
}
