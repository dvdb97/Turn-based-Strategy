package debugging;

import org.lwjgl.opengl.GLDebugMessageCallbackI;

import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.opengl.GL43.*;


public class ErrorListener implements GLDebugMessageCallbackI{

	public static final int HIGH_SEVERITY = GL_DEBUG_SEVERITY_HIGH;
	
	public static final int MEDIUM_SEVERITY = GL_DEBUG_SEVERITY_MEDIUM;
	
	public static final int LOW_SEVERITY = GL_DEBUG_SEVERITY_LOW;
	
	public static final int NOTIFICATION = GL_DEBUG_SEVERITY_NOTIFICATION;
	
	private int severity;
	
	public ErrorListener(int severity) {
		this.severity = severity;
	}
	
	@Override
	public void invoke(int source, int type, int id, int severity, int length, long message, long userParam) {
		if (severity == this.severity) {
			System.err.println(memUTF8(message, length));	
		}
	}
	
}
