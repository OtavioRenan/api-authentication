package br.com.authentication.infra.adapters.repositorys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.authentication.domain.User;
import br.com.authentication.domain.ports.repositorys.UserRepositoryPort;
import br.com.authentication.infra.adapters.Jpa.UserJpaRepository;
import br.com.authentication.infra.adapters.entitys.UserEntity;

@Component
public class UserRepository implements UserRepositoryPort {

    @Autowired
    private UserJpaRepository repository;

    @Override
    public User save(User u) {
        return repository.save(new UserEntity(u)).toUser();
    }

    @Override
    public User findByLogin(String l) {
        return repository.findByLogin(l).orElseThrow().toUser();
    }    
}