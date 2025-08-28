package com.uvsl.api_controle_financeiro.controllers;

import com.uvsl.api_controle_financeiro.domain.User;
import com.uvsl.api_controle_financeiro.dtos.AuthDTO;
import com.uvsl.api_controle_financeiro.dtos.AuthLoginResponseDTO;
import com.uvsl.api_controle_financeiro.dtos.UserDTO;
import com.uvsl.api_controle_financeiro.repositories.UserRepository;
import com.uvsl.api_controle_financeiro.security.JwtTokenProvider;
import com.uvsl.api_controle_financeiro.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid UserDTO userDTO) {
        UserDTO userToCreate = new UserDTO(
                null,
                userDTO.name(),
                userDTO.email(),
                new BCryptPasswordEncoder().encode(userDTO.password())
        );
        UserDTO createdUser = userService.createUser(userToCreate);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthLoginResponseDTO> login(@RequestBody @Valid AuthDTO data) {
        var authToken = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = authenticationManager.authenticate(authToken);
        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        String token = jwtTokenProvider.generateToken(user);
        return ResponseEntity.ok(new AuthLoginResponseDTO(token));
    }

}
