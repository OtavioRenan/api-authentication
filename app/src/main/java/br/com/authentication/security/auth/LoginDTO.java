package br.com.authentication.security.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private String login;

    private String password;
}
