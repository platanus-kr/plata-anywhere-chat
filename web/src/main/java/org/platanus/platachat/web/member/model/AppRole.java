package org.platanus.platachat.web.member.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AppRole {
    ROLE_USER("ROLE_USER", "사용자"),
    ROLE_ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String name;

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public static AppRole fromTokenRole(String role) {
        if ("ADMIN".equals(role) || "ROLE_ADMIN".equals(role)) {
            return ROLE_ADMIN;
        }
        return ROLE_USER;
    }
}
