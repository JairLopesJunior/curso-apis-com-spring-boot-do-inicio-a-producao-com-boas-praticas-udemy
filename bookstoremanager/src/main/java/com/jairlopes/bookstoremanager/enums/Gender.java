package com.jairlopes.bookstoremanager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum Gender {

    MALE("Male"),
    FEMALE("Female");

    private Gender(String description) {
        this.description = description;
    }

    private String description;
}
