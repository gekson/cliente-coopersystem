/**
 * 
 */
package com.coopersystem.cliente.controller;

import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.coopersystem.cliente.model.Cliente;
import com.coopersystem.cliente.repository.ClienteRepository;
import com.coopersystem.cliente.repository.UsuarioRepository;
import com.coopersystem.cliente.request.ClienteRequest;
import com.coopersystem.cliente.response.ClienteResponse;
import com.coopersystem.cliente.response.PaginaResponse;
import com.coopersystem.cliente.security.ApiResponse;
import com.coopersystem.cliente.security.UsuarioAtual;
import com.coopersystem.cliente.security.UsuarioPrincipal;
import com.coopersystem.cliente.service.ClienteService;
import com.coopersystem.cliente.util.Constantes;

/**
 * @author gekson
 *
 */
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	@Autowired
    private ClienteRepository clienteRepository;
	
	@Autowired
    private UsuarioRepository usuarioRepository;
	
	@Autowired
    private ClienteService clienteService;
	
	private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);
	
	@GetMapping
    public PaginaResponse<ClienteResponse> getClientes(@UsuarioAtual UsuarioPrincipal currentUser,
                                                @RequestParam(value = "page", defaultValue = Constantes.DEFAULT_PAGE_NUMBER) int page,
                                                @RequestParam(value = "size", defaultValue = Constantes.DEFAULT_PAGE_SIZE) int size) {
        return clienteService.getAllClientes(currentUser, page, size);
    }
	
	@PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addCliente(@Valid @RequestBody ClienteRequest clienteRequest) {
        Cliente cliente = clienteService.addCliente(clienteRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{clienteId}")
                .buildAndExpand(cliente.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Cliente Created Successfully"));
    }

    @GetMapping("/{clienteId}")
    public ClienteResponse getClienteById(@UsuarioAtual UsuarioPrincipal currentUser,
                                    @PathVariable Long clienteId) {
        return clienteService.getClienteById(clienteId, currentUser);
    }
}
