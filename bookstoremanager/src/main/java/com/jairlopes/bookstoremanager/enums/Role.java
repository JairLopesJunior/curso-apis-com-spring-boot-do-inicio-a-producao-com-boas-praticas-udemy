package com.jairlopes.bookstoremanager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    ADMIN("admin"),
    USER("User");

    private final String description;
}
