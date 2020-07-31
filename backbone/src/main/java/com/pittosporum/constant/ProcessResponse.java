package com.pittosporum.constant;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 *
 * @author  yichen(graffitidef@gmail.com)
 */

@Setter
@Getter
public class ProcessResponse<E> implements Serializable {
	private static final long serialVersionUID = 5340924409297261588L;

	private E entity;
	private String errorCode;
	private String message;
	private boolean hasError;

	//for exception: cannot deserialize from Object value (no delegate- or property-based Creator)
	public ProcessResponse(){}

	private ProcessResponse(E entity, String errorCode, Boolean hasError, String message) {
		this.entity = entity;
		this.errorCode = errorCode;
		this.hasError = hasError;
		this.message = message;
	}

	public static<E> ProcessResponse<E> success(){
		return new ProcessResponse(null, ResponseStatusCode.PROCESS_SUCCESS.getStatusCode(), false, ResponseStatusCode.PROCESS_SUCCESS.getMessage());
	}

	public static <E> ProcessResponse<E> success(E data){
		return new ProcessResponse<E>(data, ResponseStatusCode.PROCESS_SUCCESS.getStatusCode(), false, ResponseStatusCode.PROCESS_SUCCESS.getMessage());
	}

	public static <E> ProcessResponse<E> failure(String errorCode){
		return new ProcessResponse<E>(null, errorCode, true, null);
	}

	public static <E> ProcessResponse<E> failure(String errorCode, String message){
		return new ProcessResponse<E>(null, errorCode, true, message);
	}
}
