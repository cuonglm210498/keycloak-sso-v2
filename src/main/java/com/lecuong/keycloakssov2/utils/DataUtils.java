package com.lecuong.keycloakssov2.utils;

import java.util.Collection;

/**
 * @author CuongLM
 * @created 25/05/2024 - 21:04
 * @project keycloak-sso-v2
 */
public class DataUtils {

    public static Boolean isTrue(Object value) {

        if (value == null) return false;

        if (value instanceof String) return !((String) value).trim().isEmpty();

        if (value instanceof Number) return !((Number) value).equals(0L);

        if (value instanceof Boolean) return (Boolean) value;

        if (value instanceof Collection) return !((Collection) value).isEmpty();

        if (value instanceof Object[]) return ((Object[]) value).length > 0;

        return true;
    }
}
