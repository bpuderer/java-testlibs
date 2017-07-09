package testlibs;

public class Message {

	private String message;
	
	public Message() {
		this("default message");
	}

	public Message(String message) {
		this.message = message;
	}
	
	public Message setMessage(String message) {
		this.message = message;
		return this;
	}

	public String getMessage() {
		return message;
	}
	
}
