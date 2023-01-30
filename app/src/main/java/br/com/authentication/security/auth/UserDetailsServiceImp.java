package br.com.authentication.security.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.authentication.infra.adapters.Jpa.UserJpaRepository;
import br.com.authentication.infra.adapters.entitys.UserEntity;

public class UserDetailsServiceImp implements UserDetailsService {
   
    @Autowired
    private UserJpaRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> user = repository.findByLogin(username);

        if(user.isEmpty()) {
            throw new UsernameNotFoundException("Usuário inexistente ou senha inválida.");
        }

        return new UserDetailsData(user.orElse(new UserEntity()).toUser());
    }
}