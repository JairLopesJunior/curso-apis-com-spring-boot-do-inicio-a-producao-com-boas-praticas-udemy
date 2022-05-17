package com.jairlopes.bookstoremanager.builder;

import com.jairlopes.bookstoremanager.dto.UserDTO;
import com.jairlopes.bookstoremanager.enums.Gender;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class UserDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Jair";

    @Builder.Default
    private Integer age = 26;

    @Builder.Default
    private Gender gender = Gender.MALE;

    @Builder.Default
    private String email = "jair@gmail.com";

    @Builder.Default
    private String username = "jairlopes";

    @Builder.Default
    private String password = "123456";

    @Builder.Default
    private LocalDate birthDate = LocalDate.of(1988, 3, 25);

    public UserDTO buildUserDTO() {
        return new UserDTO(id,
                name,
                age,
                gender,
                email,
                username,
                password,
                birthDate);
    }
}

