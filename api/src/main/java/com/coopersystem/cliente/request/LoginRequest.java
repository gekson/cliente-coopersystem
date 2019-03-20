/**
 * 
 */
package com.coopersystem.cliente.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/**
 * @author gekson
 *
 */
@Getter @Setter
public class LoginRequest {
	@NotBlank
    private String login;

    @NotBlank
    private String senha;
}
