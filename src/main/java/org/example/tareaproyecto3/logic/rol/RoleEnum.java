package org.example.tareaproyecto3.logic.rol;

public enum RoleEnum {

    USER("USER"),
    SUPER_ADMIN_ROLE("SUPER-ADMIN-ROLE");

    private final String authority;

    RoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }
}