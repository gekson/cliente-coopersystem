/**
 * 
 */
package com.coopersystem.cliente.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coopersystem.cliente.model.Cliente;

/**
 * @author gekson
 *
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findById(Long clienteId);

    Page<Cliente> findByCreatedBy(Long userId, Pageable pageable);

//    long countByCreatedBy(Long userId);

    List<Cliente> findByIdIn(List<Long> clienteIds);

    List<Cliente> findByIdIn(List<Long> clienteIds, Sort sort);

}
