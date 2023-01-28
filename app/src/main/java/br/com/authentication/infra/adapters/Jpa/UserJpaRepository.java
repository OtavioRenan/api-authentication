package br.com.authentication.infra.adapters.Jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.authentication.infra.adapters.entitys.UserEntity;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByLogin(String l);
}