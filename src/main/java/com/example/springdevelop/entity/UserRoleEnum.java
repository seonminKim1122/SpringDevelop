package com.example.springdevelop.entity;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
    USER(Authority.USER),
    ADMIN(Authority.ADMIN);

    private String authority;
    private UserRoleEnum(String authority) {
        this.authority = authority;
    }

    public class Authority {
        private static final String USER = "ROLE_USER";
        private static final String ADMIN = "ROLE_ADMIN";
    }

}
