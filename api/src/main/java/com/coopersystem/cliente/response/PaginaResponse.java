/**
 * 
 */
package com.coopersystem.cliente.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author gekson
 *
 */
@Getter @Setter
public class PaginaResponse<T> {

	private List<T> content;
    private int pagina;
    private int tamanho;
    private long totalElementos;
    private int totalPaginas;
    private boolean ultima;
    
    public PaginaResponse() {

    }
    
    public PaginaResponse(List<T> content, int pagina, int tamanho, long totalElementos, int totalPaginas, boolean ultima) {
        this.content = content;
        this.pagina = pagina;
        this.tamanho = tamanho;
        this.totalElementos = totalElementos;
        this.totalPaginas = totalPaginas;
        this.ultima = ultima;
    }
}
