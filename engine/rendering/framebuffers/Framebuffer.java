package rendering.framebuffers;

import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import assets.GLTargetObject;
import assets.textures.Texture;
import assets.textures.Texture2D;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDrawBuffers;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;

public class Framebuffer extends GLTargetObject {	
	
	private GLTargetObject depthAttachment = null;
	private int depthWidth, depthHeight;
	
	private GLTargetObject colorAttachment = null;
	private int colorWidth, colorHeight;
	
	public Framebuffer() {
		super(glGenFramebuffers(), GL_FRAMEBUFFER);
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
		
		depthWidth = width;
		depthHeight = height;
		
		glFramebufferRenderbuffer(getType(), GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthAttachment.getID());
		
		if (!validate(46)) {
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
	public boolean addDepthAttachment(Texture texture) {
		bind();
		texture.bind();
		
		depthAttachment = texture;
		
		depthWidth = texture.getWidth();
		depthHeight = texture.getHeight();
		
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, texture.getType(), texture.getID(), 0);
		
		if (!validate(73)) {			
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
		
		colorAttachment = RenderBuffer.generateColorRenderBuffer(width, height);
		glFramebufferRenderbuffer(getType(), GL_COLOR_ATTACHMENT0, GL_RENDERBUFFER, colorAttachment.getID());
		
		this.colorWidth = width;
		this.colorHeight = height;
		
		if (!validate(102)) {
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
	public boolean addColorAttachment(Texture texture) {
		bind();
		texture.bind();
		
		this.colorWidth = texture.getWidth();
		this.colorHeight = texture.getHeight();
		
		colorAttachment = texture;
		glFramebufferTexture2D(getType(), GL_COLOR_ATTACHMENT0, texture.getType(), texture.getID(), 0);
		
		if (!validate(130)) {
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
		
		validate(149);
		
		this.unbind();
	}
	
	
	public void disableReadFromColorBuffer() {
		this.bind();
		glReadBuffer(GL_NONE);
		
		validate(159);
		
		this.unbind();
	}
	
	
	private IntBuffer colorBufferList() {
		IntBuffer buffer = BufferUtils.createIntBuffer(1);
		
		buffer.put(colorAttachment.getID());		
		buffer.flip();
		
		return buffer;
	}
	
	
	/**
	 * 
	 * Checks the current state of this framebuffer and logs potential errors.
	 * 
	 * @return Returns true if there are no problems with the framebuffer.
	 */
	public boolean validate(int line) {
		int error = glCheckFramebufferStatus(GL_FRAMEBUFFER);
		
		if (error != GL_FRAMEBUFFER_COMPLETE) {
			switch (error) {
				case GL_FRAMEBUFFER_UNDEFINED:
					System.err.println("Framebuffer Error l." + line + ": FRAMEBUFFER_UNDEFINED");
					break;
				case GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT:
					System.err.println("Framebuffer Error l." + line + ": GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT");
					break;
				case GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT:
					System.err.println("Framebuffer Error l." + line + ": GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT");
					break;
				case GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER:
					System.err.println("Framebuffer Error l." + line + ": GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER");
					break;
				case GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER:
					System.err.println("Framebuffer Error l." + line + ": GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER");
					break;
				case GL_FRAMEBUFFER_UNSUPPORTED:
					System.err.println("Framebuffer Error l." + line + ": GL_FRAMEBUFFER_UNSUPPORTED");
					break;
				case GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE:
					System.err.println("Framebuffer Error l." + line + ": GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE");
					break;
				default:
					System.err.println("Framebuffer Error l." + line + ": Unknown error.");
			}
			
			return false;
		}
		
		return true;
	}
	
	
	public int getWidth() {
		return colorWidth;
	}
	
	
	public int getHeight() {
		return colorHeight;
	}
	
	
	public int getDepthWidth() {
		return depthWidth;
	}
	
	
	public int getDepthHeight() {
		return depthHeight;
	}
	
	
	/**
	 * 
	 * Copy a block of pixels from the source framebuffer to this framebuffer.
	 * 
	 * @param source The source framebuffer to take the pixels from
	 */
	public void blitFramebuffer(Framebuffer source) {
		source.bindAsReadFramebuffer();
		this.bindAsDrawFramebuffer();
		
		glBlitFramebuffer(0, 0, source.getWidth(), source.getHeight(), 0, 0, this.getWidth(), this.getHeight(), GL_COLOR_BUFFER_BIT, GL_NEAREST);
		
		source.unbindAsReadFramebuffer();
		this.unbindAsDrawFramebuffer();
	}
	
	
	public void blitToDefaultFramebuffer(int windowWidth, int windowHeight) {
		this.bindAsReadFramebuffer();
		this.unbindAsDrawFramebuffer();
		
		glBlitFramebuffer(0, 0, this.getWidth(), this.getHeight(), 0, 0, windowWidth, windowHeight, GL_COLOR_BUFFER_BIT, GL_NEAREST);
		
		this.unbindAsReadFramebuffer();		
	}
	
	
	public void clear() {
		glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
	}
	
	
	public void bindAsReadFramebuffer() {
		glBindFramebuffer(GL_READ_BUFFER, getID());
	}
	
	
	public void unbindAsReadFramebuffer() {
		glBindFramebuffer(GL_READ_BUFFER, 0);
	}
	
	
	public void bind() {
		glBindFramebuffer(getType(), getID());
	}
	
	
	public void bindAsDrawFramebuffer() {
		glBindFramebuffer(GL_DRAW_BUFFER, getID());
	}
	
	
	public void unbindAsDrawFramebuffer() {
		glBindFramebuffer(GL_DRAW_BUFFER, 0);;
	}
	
	
	public void unbind() {
		glBindFramebuffer(getType(), 0);
	}
	
	
	public void delete() {
		if (colorAttachment != null) {
			colorAttachment.delete();
		}
		
		if (depthAttachment != null) {
			depthAttachment.delete();
		}
		
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
