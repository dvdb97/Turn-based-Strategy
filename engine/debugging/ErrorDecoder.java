package debugging;

import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;

public class ErrorDecoder {
	
	private static ErrorDecoder decoder = new ErrorDecoder();
	
	private HashMap<Integer, String> errors;	
	
	private ErrorDecoder() {
		errors = new HashMap<>();
		
		errors.put(GL_NO_ERROR, "GL_NO_ERROR");
		errors.put(GL_INVALID_ENUM, "GL_INVALID_ENUM");
		errors.put(GL_INVALID_VALUE, "GL_INVALID_VALUE");
		errors.put(GL_INVALID_OPERATION, "GL_INVALID_OPERATION");
	}
	
	
	public static ErrorDecoder getInstance() {
		return decoder;
	}
	
	
	public String decode(int error) {
		if (errors.containsKey(error)) {
			return errors.get(error);
		}
		
		return "<" + error + ">";
	}
	
}
