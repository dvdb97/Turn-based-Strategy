package assets.textures;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.glTexSubImage3D;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_3D;
import static org.lwjgl.opengl.GL42.*;

import java.nio.IntBuffer;
import assets.textures.utils.ImageLoader;
import utils.CustomBufferUtils;


public abstract class Texture3D extends Texture {

	private int depth;
	
	
	public Texture3D(int type, int width, int height, int depth) {
		super(type, width, height);
		
		this.depth = depth;
	}
	
	
	public Texture3D(int width, int height, int depth) {
		this(GL_TEXTURE_3D, width, height, depth);
		
	}
	
	
	public void setTexStorage() {
		this.bind();
		
		this.setFilter(getFilterMode());
		this.setTextureWrap(this.getWrapMode());
		
		glTexStorage3D(this.getType(), this.getMipMapLevels(), GL_RGBA8, this.getWidth(), this.getHeight(), depth);
		
		this.setStorageAllocated(true);
		
		this.unbind();
	}
	
	
	public void setImageData(String[] paths) {
		
		if (paths.length != this.getDepth()) {
			
			System.out.println("The data doesn't match the dimensions of the texture!");
			
			return;
			
		}
		
		for (int i = 0; i < paths.length; i++) {
			
			this.setSubImageData(i, paths[i]);
			
		}
		
		this.generateMipMapLevels();
		
	}
	
	
	public void setImageData(int[][] data) {
		
		if (data.length != this.getDepth()) {
			
			System.out.println("The data doesn't match the dimensions of the texture!");
			
			return;
		}
		
		for (int i = 0; i < data.length; i++) {
			
			this.setSubImageData(i, data[i]);
			
		}
		
		this.generateMipMapLevels();
		
	}
	
	
	public void setSubImageData(int depth, String path) {
		
		this.setSubImageData(depth, ImageLoader.loadImageDataAsRGBAInt(path));
		
	}
	
	
	public void setSubImageData(int depth, int[] data) {
		
		this.setSubImageData(depth, CustomBufferUtils.createIntBuffer(data));
		
	}
	
	
	public void setSubImageData(int depth, int[][] data) {
		
		this.setSubImageData(depth, CustomBufferUtils.createIntBuffer(data));
		
	}
	
	
	public void setSubImageData(int depth, IntBuffer buffer) {
		
		if (isStorageAllocated()) {
			this.setTexStorage();
			
			this.setStorageAllocated(true);
		}
		
		this.bind();
		
		glTexSubImage3D(this.getType(), 0, 0, 0, 0, this.getWidth(), this.getHeight(), depth, GL_RGBA, GL_UNSIGNED_INT, buffer);
		
		this.unbind();
		
	}
	
	
	public int getDepth() {
		return depth;
	}
	
	
	public void setDepth(int depth) {
		this.depth = depth;
	}

}
