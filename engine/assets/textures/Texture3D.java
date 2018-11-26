package assets.textures;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.glTexSubImage3D;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_3D;
import static org.lwjgl.opengl.GL42.*;

import java.nio.IntBuffer;
<<<<<<< HEAD
=======
import assets.textures.utils.ImageLoader;
import utils.CustomBufferUtils;
>>>>>>> master


public abstract class Texture3D extends Texture {

	private int depth;
	
	
<<<<<<< HEAD
	public Texture3D() {
		this(GL_TEXTURE_3D);
	}
	
	
	public Texture3D(int type) {
		super(type);
	}
	
	
	public Texture3D(int type, int width, int height, int depth) {
		super(type);
		
=======
	public Texture3D(int type, int width, int height, int depth) {
		super(type, width, height);
		
		this.depth = depth;
	}
	
	
	public Texture3D(int width, int height, int depth) {
		this(GL_TEXTURE_3D, width, height, depth);
		
	}
	
	
	public void setTexStorage() {
		this.bind();
		
		glTexStorage3D(this.getType(), this.getMipMapLevels(), GL_RGBA8, this.getWidth(), this.getHeight(), depth);
		
		this.setFilter(getFilterMode());
		this.setTextureWrap(this.getWrapMode());
		
		this.setStorageAllocated(true);
		
		this.unbind();
>>>>>>> master
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
		
<<<<<<< HEAD
		//TODO:
		//this.setSubImageData(depth, ImageLoader.loadImageDataAsRGBAInt(path));
=======
		this.setSubImageData(depth, ImageLoader.loadImageDataAsRGBAInt(path));
>>>>>>> master
		
	}
	
	
	public void setSubImageData(int depth, int[] data) {
		
<<<<<<< HEAD
		//TODO:
		//this.setSubImageData(depth, CustomBufferUtils.createIntBuffer(data));
=======
		this.setSubImageData(depth, CustomBufferUtils.createIntBuffer(data));
>>>>>>> master
		
	}
	
	
	public void setSubImageData(int depth, int[][] data) {
		
<<<<<<< HEAD
		//TODO:
		//this.setSubImageData(depth, CustomBufferUtils.createIntBuffer(data));
=======
		this.setSubImageData(depth, CustomBufferUtils.createIntBuffer(data));
>>>>>>> master
		
	}
	
	
<<<<<<< HEAD
	public void setSubImageData(int depth, IntBuffer buffer) {		
=======
	public void setSubImageData(int depth, IntBuffer buffer) {
		
		if (isStorageAllocated()) {
			this.setTexStorage();
			
			this.setStorageAllocated(true);
		}
		
>>>>>>> master
		this.bind();
		
		glTexSubImage3D(this.getType(), 0, 0, 0, 0, this.getWidth(), this.getHeight(), depth, GL_RGBA, GL_UNSIGNED_INT, buffer);
		
		this.unbind();
<<<<<<< HEAD
=======
		
>>>>>>> master
	}
	
	
	public int getDepth() {
		return depth;
	}
	
	
	public void setDepth(int depth) {
		this.depth = depth;
	}

<<<<<<< HEAD
}
=======
}
>>>>>>> master
