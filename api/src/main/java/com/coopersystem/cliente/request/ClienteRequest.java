/**
 * 
 */
package com.coopersystem.cliente.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * @author gekson
 *
 */
@Getter @Setter
public class ClienteRequest {

	@NotBlank
    @Size(max = 100)
    private String nome;
	private String cpf;
	private String cep;
	private String logradouro;
	private String bairro;
	private String cidade;
	private String uf;
}
