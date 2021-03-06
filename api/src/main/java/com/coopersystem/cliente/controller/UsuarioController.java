/**
 * 
 */
package com.coopersystem.cliente.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coopersystem.cliente.exception.ResourceNotFoundException;
import com.coopersystem.cliente.model.Usuario;
import com.coopersystem.cliente.repository.UsuarioRepository;
import com.coopersystem.cliente.security.UsuarioAtual;
import com.coopersystem.cliente.security.UsuarioPrincipal;
import com.coopersystem.cliente.util.UserProfile;
import com.coopersystem.cliente.util.UsuarioSummary;

/**
 * @author gekson
 *
 */
@RestController
@RequestMapping("/api")
public class UsuarioController {

	@Autowired
    UsuarioRepository usuarioRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
	
	@GetMapping("/user/me")
    public UsuarioSummary getCurrentUser(@UsuarioAtual UsuarioPrincipal currentUser) {
        UsuarioSummary userSummary = new UsuarioSummary(currentUser.getId(), currentUser.getLogin(), currentUser.getNome());
        return userSummary;
    }
	
	@GetMapping("/users/{login}")
    public UserProfile getUserProfile(@PathVariable(value = "login") String login) {
        Usuario user = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "login", login));


        UserProfile userProfile = new UserProfile(user.getId(), user.getLogin(), user.getLogin(), user.getCreatedAt());

        return userProfile;
    }
}
