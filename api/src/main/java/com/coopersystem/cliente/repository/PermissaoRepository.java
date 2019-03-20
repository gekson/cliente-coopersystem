/**
 * 
 */
package com.coopersystem.cliente.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coopersystem.cliente.model.Permissao;
import com.coopersystem.cliente.model.PermissaoEnum;

/**
 * @author gekson
 *
 */
@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
    Optional<Permissao> findByNome(PermissaoEnum nome);

}
