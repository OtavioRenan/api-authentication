package br.com.authentication.domain;

import br.com.authentication.infra.adapters.entitys.UserEntity;

public class User {
    private Long id;

    private String name;

    private String login;

    private String password;

    private Boolean flagActive;

    public User() {}

    public User(UserEntity u) {
        id = u.getId();
        name = u.getName();
        login = u.getLogin();
        password = u.getPassword();
        flagActive =  u.getFlagActive();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getFlagActive() {
        return flagActive;
    }

    public void setFlagActive(Boolean flagActive) {
        this.flagActive = flagActive;
    }
}
