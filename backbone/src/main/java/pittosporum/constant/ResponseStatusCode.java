package pittosporum.constant;

import pittosporum.constant.ErrorCode;

/**
 *
 * @author  yichen(graffitidef@gmail.com)
 */

public enum  ResponseStatusCode implements ErrorCode {
	PROCESS_SUCCESS("A0000","process_success"),
	PROCESS_FAILURE("A0001","process_failure"),
	;

	private final String statusCode;
	private final String message;

	ResponseStatusCode(String statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public String getMessage() {
		return message;
	}


}

