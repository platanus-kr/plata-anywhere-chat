package org.platanus.platachat.web.auth.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AuthProvider {

    LOCAL("LOCAL", "local"),
    AUTH_SERVICE("AUTH_SERVICE", "auth-service");

    private final String key;
    private final String name;

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
