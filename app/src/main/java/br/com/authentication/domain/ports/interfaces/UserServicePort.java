package br.com.authentication.domain.ports.interfaces;

import br.com.authentication.domain.dtos.UserRequestDTO;

public interface UserServicePort {
    void create(UserRequestDTO u);
}
