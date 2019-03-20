/**
 * 
 */
package com.coopersystem.cliente.controller;

import java.net.URI;
import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.coopersystem.cliente.exception.AppException;
import com.coopersystem.cliente.model.Permissao;
import com.coopersystem.cliente.model.PermissaoEnum;
import com.coopersystem.cliente.model.Usuario;
import com.coopersystem.cliente.repository.PermissaoRepository;
import com.coopersystem.cliente.repository.UsuarioRepository;
import com.coopersystem.cliente.security.ApiResponse;
import com.coopersystem.cliente.security.JwtAuthenticationResponse;
import com.coopersystem.cliente.security.JwtTokenProvider;
import com.coopersystem.cliente.security.LoginRequest;
import com.coopersystem.cliente.security.SignUpRequest;

/**
 * @author gekson
 *
 */
@RestController
@RequestMapping("/api/auth")
public class AutenticacaoController {
	@Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PermissaoRepository permissaoRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> autenticarUsuario(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLogin(),
                        loginRequest.getSenha()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }
    
    @SuppressWarnings("unchecked")
	@PostMapping("/signup")
    public ResponseEntity<?> criarUsuario(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(usuarioRepository.existsByLogin(signUpRequest.getLogin())) {
            return new ResponseEntity(new ApiResponse(false, "Usuário já existe!"),
                    HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = new Usuario(signUpRequest.getLogin(), signUpRequest.getSenha());

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        Permissao permissao = permissaoRepository.findByNome(PermissaoEnum.PERMISSAO_COMUM)
                .orElseThrow(() -> new AppException("Permissão não definida."));

        usuario.setPermissoes(Collections.singleton(permissao));

        Usuario result = usuarioRepository.save(usuario);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{login}")
                .buildAndExpand(result.getLogin()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Usuário cadastrado com sucesso"));
    }

}
