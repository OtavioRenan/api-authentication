package br.com.authentication.infra.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.authentication.domain.adapters.UserServiceImp;
import br.com.authentication.domain.ports.interfaces.UserServicePort;
import br.com.authentication.domain.ports.repositorys.UserRepositoryPort;

@Configuration
public class BeanConfig {

    @Bean
    UserServicePort userServicePort(UserRepositoryPort repositoryPort) {
        return new UserServiceImp(repositoryPort, new BCryptPasswordEncoder());
    }
}