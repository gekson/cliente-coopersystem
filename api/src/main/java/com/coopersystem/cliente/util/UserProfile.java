/**
 * 
 */
package com.coopersystem.cliente.util;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

/**
 * @author gekson
 *
 */
@Getter @Setter
public class UserProfile {
	private Long id;
    private String login;
    private String nome;
    private Instant joinedAt;
    
    public UserProfile(Long id, String login, String nome, Instant joinedAt) {
        this.id = id;
        this.login = login;
        this.nome = nome;
        this.joinedAt = joinedAt;
    }
}
