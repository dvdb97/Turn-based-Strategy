package rendering.framebuffers;

import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import assets.GLTargetObject;
import assets.textures.Texture2D;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDrawBuffers;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;

public class FrameBuffer extends GLTargetObject {	
	
	private GLTargetObject depthAttachment = null;
	
	private ArrayList<GLTargetObject> colorAttachments;
	
	private final int ATTACHMENT_PARAM = 36064;
	
	
	public FrameBuffer() {
		super(glGenFramebuffers(), GL_FRAMEBUFFER);
		
		colorAttachments = new ArrayList<GLTargetObject>();
	}
	
	
	/**
	 * 
	 * @param width The width of the depth component
	 * @param height The height of the depth component
	 * @return Returns true when adding the depth component was successful
	 */
	public boolean addDepthAttachment(int width, int height) {
		bind();
		
		depthAttachment = RenderBuffer.generateDepthRenderBuffer(width, height);
		
		glFramebufferRenderbuffer(getType(), GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthAttachment.getID());
		
		if (!validate()) {
			unbind();
			return false;
		}
		
		unbind();
		
		return true;
	}
	
	
	/**
	 * 
	 * @param texture The depth texture
	 * @return Returns true when adding the depth component was successful
	 */
	public boolean addDepthAttachment(Texture2D texture) {
		bind();
		texture.bind();
		
		depthAttachment = texture;
		
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, texture.getType(), texture.getID(), 0);
		
		if (!validate()) {			
			unbind();
			return false;
		}

		unbind();
		
		return true;
		
	}
	
	
	/**
	 * 
	 * Adds a RenderBuffer as a color attachment to this FrameBuffer.
	 * 
	 * @param width The width of the RenderBuffer
	 * @param height The height of the RenderBuffer
	 * @return Returns true when adding the color attachment was successful
	 * 
	 */
	public boolean addColorAttachment(int width, int height) {
		bind();
		
		
		if (colorAttachments.size() == 15) {
			unbind();
			return false;
		}
		
		
		colorAttachments.add(RenderBuffer.generateColorRenderBuffer(width, height));
		
		glFramebufferRenderbuffer(getType(), ATTACHMENT_PARAM + colorAttachments.size(), GL_RENDERBUFFER, colorAttachments.get(colorAttachments.size() - 1).getID());
		
		
		if (!validate()) {
			unbind();
			return false;
		}
		
		
		glDrawBuffers(colorBufferList());
		
		unbind();
		
		return true;
	}
	
	
	/**
	 * 
	 * @param texture The texture that will be rendered to
	 * @return Returns true when adding the color attachment was successful
	 */
	public boolean addColorAttachment(Texture2D texture) {
		bind();
		texture.bind();
		
		
		if (colorAttachments.size() == 15) {
			System.err.println("The maximum amount of color attachments per FrameBuffer is 15!");
			
			texture.unbind();
			unbind();
			return false;
		}
		
		
		colorAttachments.add(texture);
		
		glFramebufferTexture(getType(), ATTACHMENT_PARAM + colorAttachments.size(), texture.getID(), 0);
		
		
		if (!validate()) {
			texture.unbind();
			unbind();
			return false;
		}
		
		
		glDrawBuffers(colorBufferList());
		
		texture.unbind();
		unbind();
		
		return true;
	}
	
	
	public void disableDrawToColorBuffer() {
		this.bind();
		glDrawBuffers(GL_NONE);
		
		validate();
		
		this.unbind();
	}
	
	
	public void disableReadFromColorBuffer() {
		this.bind();
		glReadBuffer(GL_NONE);
		
		validate();
		
		this.unbind();
	}
	
	
	private IntBuffer colorBufferList() {
		
		IntBuffer buffer = BufferUtils.createIntBuffer(colorAttachments.size());
		
		for (GLTargetObject object : colorAttachments) {
			buffer.put(object.getID());
		}
		
		buffer.flip();
		
		return buffer;
		
	}
	
	
	/**
	 * 
	 * Checks the current state of this framebuffer and logs potential errors.
	 * 
	 * @return Returns true if there are no problems with the framebuffer.
	 */
	public boolean validate() {
		int error = glCheckFramebufferStatus(GL_FRAMEBUFFER);
		
		if (error != GL_FRAMEBUFFER_COMPLETE) {
			switch (error) {
				case GL_FRAMEBUFFER_UNDEFINED:
					System.err.println("Framebuffer Error: FRAMEBUFFER_UNDEFINED");
					break;
				case GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT:
					System.err.println("Framebuffer Error: GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT");
					break;
				case GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT:
					System.err.println("Framebuffer Error: GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT");
					break;
				case GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER:
					System.err.println("Framebuffer Error: GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER");
					break;
				case GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER:
					System.err.println("Framebuffer Error: GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER");
					break;
				case GL_FRAMEBUFFER_UNSUPPORTED:
					System.err.println("Framebuffer Error: GL_FRAMEBUFFER_UNSUPPORTED");
					break;
				case GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE:
					System.err.println("Framebuffer Error: GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE");
					break;
				default:
					System.err.println("Framebuffer Error: Unknown error.");
			}
			
			return false;
		}
		
		return true;
	}
	
	
	public void clear() {
		glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
	}
	
	
	public void bind() {
		glBindFramebuffer(getType(), getID());
	}
	
	
	public void unbind() {
		glBindFramebuffer(getType(), 0);
	}
	
	
	public void delete() {
		glDeleteFramebuffers(getID());
	}

}



/**
 * 
 * @author Dario
 * 
 * This class is only locally available because it will only be used with a
 * framebuffer.
 * 
 */
class RenderBuffer extends GLTargetObject {	
	
	private RenderBuffer() {
		super(glGenRenderbuffers(), GL_RENDERBUFFER);
	}
	
	
	public static RenderBuffer generateDepthRenderBuffer(int width, int height) {
		
		RenderBuffer rb = new RenderBuffer();
		
		rb.setDepthStorage(width, height);
		
		return rb;
		
	}
	
	
	public static RenderBuffer generateColorRenderBuffer(int width, int height) {
		
		RenderBuffer rb = new RenderBuffer();
		
		rb.setColorStorage(width, height);
		
		return rb;
		
	}
	
	
	private void setDepthStorage(int width, int height) {
		bind();
		
		glRenderbufferStorage(getType(), GL_DEPTH_COMPONENT, width, height);
		
		unbind();	
	}
	
	
	private void setColorStorage(int width, int height) {
		bind();
		
		glRenderbufferStorage(getType(), GL_RGBA8, width, height);
		
		unbind();
	}
	
	
	public void bind() {
		glBindRenderbuffer(getType(), getID());
	}
	
	
	public void unbind() {
		glBindRenderbuffer(getType(), 0);
	}
	
	public void delete() {
		glDeleteRenderbuffers(getID());
	}
	
}
