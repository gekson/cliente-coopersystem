/**
 * 
 */
package com.coopersystem.cliente.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.coopersystem.cliente.exception.BadRequestException;
import com.coopersystem.cliente.exception.ResourceNotFoundException;
import com.coopersystem.cliente.model.Cliente;
import com.coopersystem.cliente.model.Usuario;
import com.coopersystem.cliente.repository.ClienteRepository;
import com.coopersystem.cliente.repository.UsuarioRepository;
import com.coopersystem.cliente.request.ClienteRequest;
import com.coopersystem.cliente.response.ClienteResponse;
import com.coopersystem.cliente.response.PaginaResponse;
import com.coopersystem.cliente.security.UsuarioPrincipal;
import com.coopersystem.cliente.util.Constantes;
import com.coopersystem.cliente.util.ModelMapa;

/**
 * @author gekson
 *
 */
@Service
public class ClienteService {
	@Autowired
    private ClienteRepository clienteRepository;
	
	@Autowired
    private UsuarioRepository usuarioRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);

	public PaginaResponse<ClienteResponse> getAllClientes(UsuarioPrincipal currentUser, int page, int size) {
		validatePageNumberAndSize(page, size);

		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Cliente> clientes = clienteRepository.findAll(pageable);

        if(clientes.getNumberOfElements() == 0) {
            return new PaginaResponse<>(Collections.emptyList(), clientes.getNumber(),
                    clientes.getSize(), clientes.getTotalElements(), clientes.getTotalPages(), clientes.isLast());
        }

        List<Long> clienteIds = clientes.map(Cliente::getId).getContent();
        Map<Long, Usuario> creatorMap = getClienteCreatorMap(clientes.getContent());
        		
        List<ClienteResponse> clienteResponses = clientes.map(cliente -> {
            return ModelMapa.mapClienteToClienteResponse(cliente, creatorMap.get(cliente.getCreatedBy()));
        }).getContent();

        return new PaginaResponse<>(clienteResponses, clientes.getNumber(),
                clientes.getSize(), clientes.getTotalElements(), clientes.getTotalPages(), clientes.isLast());
	}
	
	Map<Long, Usuario> getClienteCreatorMap(List<Cliente> clientes) {
        List<Long> creatorIds = clientes.stream()
                .map(Cliente::getCreatedBy)
                .distinct()
                .collect(Collectors.toList());

        List<Usuario> creators = usuarioRepository.findByIdIn(creatorIds);
        Map<Long, Usuario> creatorMap = creators.stream()
                .collect(Collectors.toMap(Usuario::getId, Function.identity()));

        return creatorMap;
    }

	public Cliente addCliente(@Valid ClienteRequest clienteRequest) {
		Cliente cliente = new Cliente();
        cliente.setNome(clienteRequest.getNome());
        cliente.setCpf(clienteRequest.getCpf());
        cliente.setCep(clienteRequest.getCep());
        cliente.setLogradouro(clienteRequest.getLogradouro());
        cliente.setBairro(clienteRequest.getBairro());
        cliente.setCidade(clienteRequest.getCidade());
        cliente.setUf(clienteRequest.getUf());

        return clienteRepository.save(cliente);
	}

	public ClienteResponse getClienteById(Long clienteId, UsuarioPrincipal currentUser) {
		Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(
                () -> new ResourceNotFoundException("Cliente", "id", clienteId));
		
        Usuario creator = usuarioRepository.findById(cliente.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", cliente.getCreatedBy()));

		return ModelMapa.mapClienteToClienteResponse(cliente, creator);
	}
	
	private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("O número da página não pode ser menor que zero.");
        }

        if(size > Constantes.MAX_PAGE_SIZE) {
            throw new BadRequestException("O tamanho da página não deve ser maior quen " + Constantes.MAX_PAGE_SIZE);
        }
    }
}
