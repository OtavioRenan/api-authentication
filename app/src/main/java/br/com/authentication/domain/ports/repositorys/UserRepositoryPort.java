package br.com.authentication.domain.ports.repositorys;

import br.com.authentication.domain.User;

public interface UserRepositoryPort {
    User save(User u);

    User findByLogin(String l);
}