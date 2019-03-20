/**
 * 
 */
package com.coopersystem.cliente.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coopersystem.cliente.model.Usuario;

/**
 * @author gekson
 *
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByIdIn(List<Long> usuarioIds);

    Optional<Usuario> findByLogin(String login);

    Boolean existsByLogin(String login);

}
