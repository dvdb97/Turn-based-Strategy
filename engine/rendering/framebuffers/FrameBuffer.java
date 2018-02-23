package rendering.framebuffers;

import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import assets.GLObject;
import assets.textures.Texture2D;

import static org.lwjgl.opengl.GL11.GL_DEPTH_COMPONENT;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_NONE;
import static org.lwjgl.opengl.GL20.glDrawBuffers;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;

public class FrameBuffer extends GLObject {	
	
	private GLObject depthAttachment;
	
	private ArrayList<GLObject> colorAttachments;
	
	private final int ATTACHMENT_PARAM = 36064;
	
	
	public FrameBuffer() {
		super(glGenFramebuffers(), GL_FRAMEBUFFER);
		
		colorAttachments = new ArrayList<GLObject>();
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
		
		if (glCheckFramebufferStatus(getType()) != GL_FRAMEBUFFER_COMPLETE) {
			System.out.println("Failed attaching a depth component to the FrameBuffer!");
			
			unbind();
			return false;
		}
		
		unbind();
		
		return true;
	}
	
	
	public boolean addDepthAttachment(Texture2D texture) {
		bind();
		texture.bind();
		
		depthAttachment = texture;
		
		glFramebufferTexture(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, texture.getID(), 0);
		
		if (glCheckFramebufferStatus(getType()) != GL_FRAMEBUFFER_COMPLETE) {
			System.out.println("Failed attaching a depth component to the FrameBuffer!");
			
			texture.unbind();
			unbind();
			return false;
		}
		
		texture.unbind();
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
			System.err.println("The maximum amount of color attachments per FraeBuffer is 15!");
			
			unbind();
			return false;
		}
		
		
		colorAttachments.add(RenderBuffer.generateColorRenderBuffer(width, height));
		
		glFramebufferRenderbuffer(getType(), ATTACHMENT_PARAM + colorAttachments.size(), GL_RENDERBUFFER, colorAttachments.get(colorAttachments.size() - 1).getID());
		
		
		if (glCheckFramebufferStatus(getType()) != GL_FRAMEBUFFER_COMPLETE) {
			System.out.println("Failed attaching a color component to the FrameBuffer!");
			
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
			System.err.println("The maximum amount of color attachments per FraeBuffer is 15!");
			
			texture.unbind();
			unbind();
			return false;
		}
		
		
		colorAttachments.add(texture);
		
		glFramebufferTexture(getType(), ATTACHMENT_PARAM + colorAttachments.size(), texture.getID(), 0);
		
		
		if (glCheckFramebufferStatus(getType()) != GL_FRAMEBUFFER_COMPLETE) {
			System.out.println("Failed attaching a color component to the FrameBuffer!");
			
			texture.unbind();
			unbind();
			return false;
		}
		
		
		glDrawBuffers(colorBufferList());
		
		texture.unbind();
		unbind();
		
		return true;
	}
	
	
	public void disableColorBuffer() {
		glDrawBuffers(GL_NONE);
	}
	
	
	private IntBuffer colorBufferList() {
		
		IntBuffer buffer = BufferUtils.createIntBuffer(colorAttachments.size());
		
		for (GLObject object : colorAttachments) {
			buffer.put(object.getID());
		}
		
		buffer.flip();
		
		return buffer;
		
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
class RenderBuffer extends GLObject {	
	
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
