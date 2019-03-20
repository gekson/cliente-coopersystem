/**
 * 
 */
package com.coopersystem.cliente.model.audit;

import javax.persistence.Column;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import lombok.Getter;
import lombok.Setter;

/**
 * @author gekson
 *
 */
@Getter @Setter
public abstract class UsuarioEDataAuditoria extends DataAuditoria {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@CreatedBy
    @Column(updatable = false)
    private Long createdBy;

    @LastModifiedBy
    private Long updatedBy;

}