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
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadRequestException(String messagem) {
        super(messagem);
    }

    public BadRequestException(String messagem, Throwable causa) {
        super(messagem, causa);
    }

}
