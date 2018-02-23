package assets.textures;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.glTexSubImage3D;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_3D;
import static org.lwjgl.opengl.GL42.*;

import java.nio.IntBuffer;


public abstract class Texture3D extends Texture {

	private int depth;
	
	
	public Texture3D() {
		this(GL_TEXTURE_3D);
	}
	
	
	public Texture3D(int type) {
		super(type);
	}
	
	
	public Texture3D(int type, int width, int height, int depth) {
		super(type);
		
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
		
		//TODO:
		//this.setSubImageData(depth, ImageLoader.loadImageDataAsRGBAInt(path));
		
	}
	
	
	public void setSubImageData(int depth, int[] data) {
		
		//TODO:
		//this.setSubImageData(depth, CustomBufferUtils.createIntBuffer(data));
		
	}
	
	
	public void setSubImageData(int depth, int[][] data) {
		
		//TODO:
		//this.setSubImageData(depth, CustomBufferUtils.createIntBuffer(data));
		
	}
	
	
	public void setSubImageData(int depth, IntBuffer buffer) {		
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