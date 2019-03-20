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
public class SignUpRequest {

    @NotBlank
    @Size(min = 3, max = 15)
    private String login;

    @NotBlank
    @Size(min = 6, max = 20)
    private String senha;
}
