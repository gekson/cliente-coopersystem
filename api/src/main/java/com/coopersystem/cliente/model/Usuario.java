/**
 * 
 */
package com.coopersystem.cliente.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.coopersystem.cliente.model.audit.DataAuditoria;

import lombok.Getter;
import lombok.Setter;

/**
 * @author gekson
 *
 */
@Entity
@Table(name = "usuarios", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "login"
        })
})
@Getter @Setter
public class Usuario extends DataAuditoria {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 15)
    private String login;

    @NotBlank
    @Size(max = 100)
    private String senha;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "usuario_permissoes",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "permissao_id"))
    private Set<Permissao> permissoes = new HashSet<>();

    public Usuario() {

    }

    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

}
