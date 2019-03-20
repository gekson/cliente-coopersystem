/**
 * 
 */
package com.coopersystem.cliente.util;

import lombok.Getter;
import lombok.Setter;

/**
 * @author gekson
 *
 */
@Getter @Setter
public class UsuarioSummary {
	private Long id;
    private String login;
    private String nome;

    public UsuarioSummary(Long id, String login, String nome) {
        this.id = id;
        this.login = login;
        this.nome = nome;
    }
}
