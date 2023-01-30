package br.com.authentication.domain.adapters;

import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.authentication.domain.User;
import br.com.authentication.domain.dtos.UserRequestDTO;
import br.com.authentication.domain.ports.interfaces.UserServicePort;
import br.com.authentication.domain.ports.repositorys.UserRepositoryPort;

public class UserServiceImp implements UserServicePort {

    private final UserRepositoryPort repository;

    private final PasswordEncoder encoder;

    public UserServiceImp(UserRepositoryPort repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public void create(UserRequestDTO u) {
        User user = new User();
        user.setName(u.getName());
        user.setLogin(u.getLogin());
        user.setPassword(encoder.encode(u.getPassword()));
        user.setFlagActive(Boolean.TRUE);
        
        repository.save(user);
    }
}