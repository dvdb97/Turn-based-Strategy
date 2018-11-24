package assets.textures;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.GL_GENERATE_MIPMAP;
import java.nio.ByteBuffer;
import assets.textures.utils.ImageLoader;


public class Texture2D extends Texture {	

	public Texture2D(String path, int width, int height, int filter, int mipmapFilter) {
		super(GL_TEXTURE_2D, width, height);
		
		setImageData(path, filter, mipmapFilter);
	}
	
	/**
	 * 
	 * @param bitmap byte buffer: [r1,g1,b1,a1,r2,g2,b2,a2,r3,...]
	 * @param width
	 * @param height
	 */
	public Texture2D(ByteBuffer bitmap, int width, int height) {
		super(GL_TEXTURE_2D, width, height);
		
		setImageData(bitmap, Texture.LINEAR, Texture.NEAREST);
	}
	
	
	public void setImageData(ByteBuffer buffer, int filter, int mipmapFilter) {
		bind();
		
		glTexParameteri(getType(), GL_GENERATE_MIPMAP, GL_TRUE);
		
		glTexImage2D(getType(), 0, GL_RGBA, this.getWidth(), this.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		
		System.out.println("Texture2D 33");
		this.setFilter(filter, mipmapFilter);
		this.setTextureWrap(this.getWrapMode());
		
		generateMipMapLevels();
		
		unbind();
	}
	
	public void setImageData(String path, int filter, int mipmapFilter) {
		bind();
		
		ByteBuffer buffer = ImageLoader.loadImageRGBA(path).getImageDataAsByteBuffer();
		
	//	setImageData(buffer, filter, mipmapFilter);
		
		glTexParameteri(getType(), GL_GENERATE_MIPMAP, GL_TRUE);
		
		glTexImage2D(getType(), 0, GL_RGBA, this.getWidth(), this.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		
		System.out.println("Texture2D 33");
		this.setFilter(filter, mipmapFilter);
		this.setTextureWrap(this.getWrapMode());
		
		generateMipMapLevels();
		
		unbind();
	}
	
	
}
