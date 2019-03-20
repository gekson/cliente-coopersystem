/**
 * 
 */
package com.coopersystem.cliente.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author gekson
 *
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AppException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppException(String messagem) {
        super(messagem);
    }

    public AppException(String messagem, Throwable causa) {
        super(messagem, causa);
    }

}
