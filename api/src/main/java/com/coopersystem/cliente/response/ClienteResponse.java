/**
 * 
 */
package com.coopersystem.cliente.response;

import java.time.Instant;

import com.coopersystem.cliente.util.UsuarioSummary;

import lombok.Getter;
import lombok.Setter;

/**
 * @author gekson
 *
 */
@Getter
@Setter
public class ClienteResponse {
	private Long id;
	private String nome;
	private UsuarioSummary createdBy;
    private Instant dataCriacao;
    private String cpf;
	private String cep;
	private String logradouro;
	private String bairro;
	private String cidade;
	private String uf;
}
