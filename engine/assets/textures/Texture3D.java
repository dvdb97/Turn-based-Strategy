package assets.textures;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_3D;
import static org.lwjgl.opengl.GL42.*;

import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;

import assets.textures.utils.Image;
import assets.textures.utils.ImageLoader;


public class Texture3D extends Texture {

	private int depth;
	
	public Texture3D() {
		this(GL_TEXTURE_3D);
	}
	
	
	public Texture3D(String[] paths) {
		this();
		
		setImageData(paths);
	}

	
	public Texture3D(int type) {
		super(type);
	}
	
	
	public void setImageData(String[] paths) {
		this.bind();
		
		Image[] images = new Image[paths.length];
		
		Image first = ImageLoader.loadImageRGBA(paths[0]);
		images[0] = first;
		
		setWidth(first.getWidth());
		setHeight(first.getHeight());
		setDepth(paths.length);
		
		// All dimensions have to be powers of 2.
		assert getWidth() % 2 == 0 && getHeight() % 2 == 0 && getDepth() % 2 == 0;
		
		for (int i = 1; i < paths.length; i++) {
			Image img = ImageLoader.loadImageRGBA(paths[i]);
			
			// Make sure that all dimensions stay the same across all loaded images.
			assert img.getWidth() == getWidth() && img.getHeight() == getHeight();
			
			images[i] = img;
		}
		
		glTexStorage3D(getType(), 1, GL_RGBA8, getWidth(), getHeight(), depth);
		
		for (int i = 0; i < images.length; i++) {
			ByteBuffer pixels = images[i].getImageDataAsByteBuffer().flip();
			
			glTexSubImage3D(getType(), 0, 0, 0, i, getWidth(), getHeight(), 1, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
		}
		
		this.unbind();
	}
	
	
	public void setImageData(ByteBuffer pixels, int width, int height, int depth) {
		this.bind();
		
		setWidth(width);
		setHeight(height);
		setDepth(depth);
		
		glTexImage3D(getType(), 0, GL_RGBA8, getWidth(), getHeight(), getDepth(), 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
		
		this.unbind();
	}
	
	
	public int getDepth() {
		return depth;
	}
	
	
	public void setDepth(int depth) {
		this.depth = depth;
	}

}
