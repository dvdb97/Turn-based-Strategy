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
	
	
	public Texture2D(int width, int height) {
		super(GL_TEXTURE_2D, width, height);
	}
	
	
	public void setImageData(String path, int filter, int mipmapFilter) {
		bind();

		ByteBuffer buffer = ImageLoader.loadImageRGBA(path).getImageDataAsByteBuffer();
		
		glTexParameteri(getType(), GL_GENERATE_MIPMAP, GL_TRUE);
		
		glTexImage2D(getType(), 0, GL_RGBA, this.getWidth(), this.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		
		System.out.println("Texture2D 33");
		this.setFilter(filter, mipmapFilter);
		this.setTextureWrap(this.getWrapMode());
		
		generateMipMapLevels();
		
		unbind();
	}

}
