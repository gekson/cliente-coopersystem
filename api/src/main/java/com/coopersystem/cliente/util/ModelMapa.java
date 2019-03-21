/**
 * 
 */
package com.coopersystem.cliente.util;

import com.coopersystem.cliente.model.Cliente;
import com.coopersystem.cliente.model.Usuario;
import com.coopersystem.cliente.response.ClienteResponse;

/**
 * @author gekson
 *
 */
public class ModelMapa {
	
	public static ClienteResponse mapClienteToClienteResponse(Cliente cliente, Usuario creator) {
        ClienteResponse clienteResponse = new ClienteResponse();
        clienteResponse.setId(cliente.getId());
        clienteResponse.setNome(cliente.getNome());
        clienteResponse.setDataCriacao(cliente.getCreatedAt());
        UsuarioSummary creatorSummary = new UsuarioSummary(creator.getId(), creator.getLogin(), creator.getLogin());
        clienteResponse.setCreatedBy(creatorSummary);
        clienteResponse.setCpf(cliente.getCpf());

        return clienteResponse;
    }
}
