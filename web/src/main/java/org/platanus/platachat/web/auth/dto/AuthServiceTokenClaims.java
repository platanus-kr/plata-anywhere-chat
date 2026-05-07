package org.platanus.platachat.web.auth.dto;

import org.platanus.platachat.web.member.model.AppRole;

import java.util.Map;

public record AuthServiceTokenClaims(
        String userId,
        Long internalUserId,
        String role,
        boolean enabled,
        String tokenType,
        String source,
        String email,
        String name
) {

    public static AuthServiceTokenClaims from(Map<String, Object> claims) {
        return new AuthServiceTokenClaims(
                stringValue(claims.get("sub")),
                longValue(claims.get("uid")),
                stringValue(claims.getOrDefault("role", "USER")),
                booleanValue(claims.get("enabled")),
                stringValue(claims.get("type")),
                stringValue(claims.get("sub_type")),
                stringValue(claims.get("email")),
                stringValue(claims.get("name"))
        );
    }

    public AppRole appRole() {
        return AppRole.fromTokenRole(role);
    }

    private static String stringValue(Object value) {
        return value == null ? null : String.valueOf(value);
    }

    private static Long longValue(Object value) {
        if (value instanceof Number number) {
            return number.longValue();
        }
        if (value == null) {
            return null;
        }
        return Long.parseLong(String.valueOf(value));
    }

    private static boolean booleanValue(Object value) {
        if (value instanceof Boolean bool) {
            return bool;
        }
        return Boolean.parseBoolean(String.valueOf(value));
    }
}
