/**
 * 
 */
package com.coopersystem.cliente.security;

import lombok.Getter;
import lombok.Setter;

/**
 * @author gekson
 *
 */
@Getter @Setter
public class ApiResponse {
	private Boolean successo;
    private String messagem;

    public ApiResponse(Boolean successo, String messagem) {
        this.successo = successo;
        this.messagem = messagem;
    }
}
