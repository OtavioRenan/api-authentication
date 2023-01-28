package br.com.authentication.infra.adapters.entitys;

import br.com.authentication.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String login;

    private String password;

    @Column(name = "flag_active")
    private Boolean flagActive;

    public UserEntity(User u) {
        id = u.getId();
        name = u.getName();
        login = u.getLogin();
        password = u.getPassword();
        flagActive =  u.getFlagActive();
    }

    public User toUser() {
        return new User(this);
    }
}