package com.igrejacristahangar.cantina.modules.usuario.controller;

import com.igrejacristahangar.cantina.config.security.TokenService;
import com.igrejacristahangar.cantina.modules.usuario.dto.AuthenticationDTO;
import com.igrejacristahangar.cantina.modules.usuario.dto.RegisterDTO;
import com.igrejacristahangar.cantina.modules.usuario.dto.TokenResponseDTO;
import com.igrejacristahangar.cantina.modules.usuario.model.Usuario;
import com.igrejacristahangar.cantina.modules.usuario.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UsuarioController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getSenha());

        var auth = this.authenticationManager.authenticate(usernamePassword);
        System.out.println("CHEGOU AQUI");
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        TokenResponseDTO responseDTO = new TokenResponseDTO(token);

        return ResponseEntity.ok(responseDTO);

    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDTO request) {
        if (usuarioRepository.findByEmail(request.getEmail()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(request.getSenha());
        Usuario newUser = Usuario.builder()
                .email(request.getEmail())
                .senha(encryptedPassword)
                .role(request.getRole())
                .build();

        this.usuarioRepository.save(newUser);

        return ResponseEntity.ok("Usuário criado com sucesso!");
    }

}
