package testlibs;

public class Message {

	private String message;
	
	public Message() {
		this("default message");
	}

	public Message(String message) {
		this.message = message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
}
