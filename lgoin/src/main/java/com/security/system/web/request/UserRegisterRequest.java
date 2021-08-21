package com.security.system.web.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import com.security.system.entity.User;
import com.security.system.validator.FullName;

/**
 * @author shuang.kou
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
    @FullName
    @NotBlank
    private String fullName;

    public User toUser() {
        return User.builder().fullName(this.getFullName())
                .userName(this.getUserName())
                .enabled(true).build();
    }
}
