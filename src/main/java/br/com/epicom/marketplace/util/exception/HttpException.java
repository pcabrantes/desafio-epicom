package br.com.epicom.marketplace.util.exception;

public class HttpException extends Exception {

	private static final long serialVersionUID = -8954615299939977763L;
	private String message;
	private int status;
	
	public HttpException(String message, int status) {
		super(message);
		setMessage(message);
		setStatus(status);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
}
