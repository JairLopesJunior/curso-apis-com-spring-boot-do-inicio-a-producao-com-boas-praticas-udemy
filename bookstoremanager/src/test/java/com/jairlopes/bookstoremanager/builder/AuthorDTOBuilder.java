package com.jairlopes.bookstoremanager.builder;

import com.jairlopes.bookstoremanager.dto.AuthorDTO;
import lombok.Builder;

@Builder
public class AuthorDTOBuilder {

    @Builder.Default
    private final Long id = 1L;

    @Builder.Default
    private final String name = "Jair Lopes";

    @Builder.Default
    private final Integer age = 32;

    public AuthorDTO buildAuthorDTO() {
        return new AuthorDTO(id, name, age);
    }
}
