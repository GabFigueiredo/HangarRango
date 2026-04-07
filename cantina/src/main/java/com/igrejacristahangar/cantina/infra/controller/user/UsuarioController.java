package com.igrejacristahangar.cantina.infra.controller.user;

import com.igrejacristahangar.cantina.config.security.TokenService;
import com.igrejacristahangar.cantina.enterprise.user.User;
import com.igrejacristahangar.cantina.enterprise.user.dto.AuthenticationDTO;
import com.igrejacristahangar.cantina.enterprise.user.dto.RegisterDTO;
import com.igrejacristahangar.cantina.enterprise.user.dto.TokenResponseDTO;
import com.igrejacristahangar.cantina.enterprise.user.dto.UserResponseDTO;
import com.igrejacristahangar.cantina.enterprise.repositories.UsuarioRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Usuário", description = "Endpoints relacionado ao usuário.")
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

        User user = ((User) auth.getPrincipal());
        var token = tokenService.generateToken((user));

        UserResponseDTO usuarioResponse = UserResponseDTO.builder()
                .nome(user.getNome())
                .email(user.getEmail())
                .build();

        TokenResponseDTO responseDTO = new TokenResponseDTO(usuarioResponse, token);

        ResponseCookie cookie = ResponseCookie.from("USER_TOKEN", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(responseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDTO request) {
        if (usuarioRepository.findByEmail(request.getEmail()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(request.getSenha());
        User newUser = User.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senha(encryptedPassword)
                .role(request.getRole())
                .build();

        this.usuarioRepository.save(newUser);

        return ResponseEntity.ok("Usuário criado com sucesso!");
    }

}
