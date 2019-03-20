/**
 * 
 */
package com.coopersystem.cliente.security;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.coopersystem.cliente.model.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * @author gekson
 *
 */
@Getter @Setter
public class UsuarioPrincipal implements UserDetails {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String login;

    @JsonIgnore
    private String senha;

    private Collection<? extends GrantedAuthority> autorizacoes;

    public UsuarioPrincipal(Long id, String login, String senha, Collection<? extends GrantedAuthority> autorizacoes) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.autorizacoes = autorizacoes;
    }

    public static UsuarioPrincipal create(Usuario usuario) {
        List<GrantedAuthority> autorizacoes = usuario.getPermissoes().stream().map(permissao ->
                new SimpleGrantedAuthority(permissao.getNome().name())
        ).collect(Collectors.toList());

        return new UsuarioPrincipal(
                usuario.getId(),
                usuario.getLogin(),
                usuario.getSenha(),
                autorizacoes
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioPrincipal that = (UsuarioPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return autorizacoes;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
