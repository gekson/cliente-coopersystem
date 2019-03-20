/**
 * 
 */
package com.coopersystem.cliente.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.coopersystem.cliente.model.audit.UsuarioEDataAuditoria;

import lombok.Getter;
import lombok.Setter;

/**
 * @author gekson
 *
 */
@Entity
@Table(name = "clientes")
@Getter @Setter
public class Cliente extends UsuarioEDataAuditoria {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank
    @Size(max = 100, min = 3)
    private String nome;
	
	@NotBlank
	private String cpf;
	
	@NotBlank
	private String cep;
	
	@NotBlank
	private String logradouro;
	
	@NotBlank
	private String bairro;
	
	@NotBlank
	private String cidade;
	
	@NotBlank
	private String uf;
	
	private String complemento;
}
