package pittosporum.core;

import lombok.Getter;
import lombok.Setter;
import pittosporum.constant.ErrorCode;

/**
 *
 * @author  yichen(graffitidef@gmail.com)
 */

public enum  ResponseStatusCode implements ErrorCode {
	PROCESS_SUCCESS(0, "process_success"),
	PROCESS_FAIL(1, "process_fail"),
	DUPLICATION_RECORD(10001, "This record already exists in the system."),
	;

	private final int statusCode;
	private final String message;

	ResponseStatusCode(int statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getMessage() {
		return message;
	}


}

