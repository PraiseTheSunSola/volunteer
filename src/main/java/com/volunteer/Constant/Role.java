package com.volunteer.Constant;

public enum Role {
    USER, ADMIN;

    public String getRoleName() {
        return "ROLE_" + this.name();  // "ROLE_ADMIN", "ROLE_USER" 형태로 반환
    }
}
