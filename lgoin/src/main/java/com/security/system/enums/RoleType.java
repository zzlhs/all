package com.security.system.enums;

import lombok.Getter;

/**
 * @author shuang.kou
 */

@Getter
public enum RoleType {
    USER("ROLE_USER", "用户"),
    TEMP_USER("ROLE_TEMP_USER", "临时用户"),
    MANAGER("ROLE_MANAGER", "管理者"),
    ADMIN("ROLE_ADMIN", "Admin");
    private final String name;
    private final String description;

    RoleType(java.lang.String name, java.lang.String description) {
        this.name = name;
        this.description = description;
    }
}
