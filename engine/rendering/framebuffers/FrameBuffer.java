package rendering.framebuffers;

import static org.lwjgl.opengl.GL30.*;

public class FrameBuffer {
	
	private final int ID;
	
	private final int TYPE = GL_FRAMEBUFFER;
	
	
	public FrameBuffer() {
		this.ID = glGenFramebuffers();
	}
	
	
	public void bind() {
		glBindFramebuffer(TYPE, ID);
	}
	
	
	public void unbind() {
		glBindFramebuffer(TYPE, 0);
	}

}
