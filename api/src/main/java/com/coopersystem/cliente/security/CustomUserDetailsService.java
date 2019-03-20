/**
 * 
 */
package com.coopersystem.cliente.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coopersystem.cliente.model.Usuario;
import com.coopersystem.cliente.repository.UsuarioRepository;

/**
 * @author gekson
 *
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> 
                        new UsernameNotFoundException("Usuaário não existe com este login : " + login)
        );

        return UsuarioPrincipal.create(usuario);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
    	Usuario usuario = usuarioRepository.findById(id).orElseThrow(
            () -> new UsernameNotFoundException("Usuaário não encontrado com o id : " + id)
        );

        return UsuarioPrincipal.create(usuario);
    }

}
