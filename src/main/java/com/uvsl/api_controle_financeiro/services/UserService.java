package com.uvsl.api_controle_financeiro.services;

import com.uvsl.api_controle_financeiro.domain.User;
import com.uvsl.api_controle_financeiro.dtos.UserDTO;
import com.uvsl.api_controle_financeiro.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.name());
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());
        User savedUser = userRepository.save(user);
        return new UserDTO(savedUser.getId(), savedUser.getName(), savedUser.getEmail(), savedUser.getPassword());
    }
}
