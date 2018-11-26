package assets.textures;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;

import java.nio.ByteBuffer;

import assets.textures.utils.Image;
import assets.textures.utils.ImageLoader;

public class CubeMap extends Texture {

	//The position of each face in the path array.
	public static final int RIGHT = 0;
	public static final int LEFT = 1;
	public static final int TOP = 2;
	public static final int BOTTOM = 3;
	public static final int BACK = 4;
	public static final int FRONT = 5;
	
	
	public CubeMap(String[] paths) {
		super(GL_TEXTURE_CUBE_MAP);
		
		loadCubeMap(paths);
	}
	
	
	private void loadCubeMap(String[] paths) {
		if (paths.length != 6)
			System.err.println("Error: A CubeMap requires 6 textures!");
		
		this.bind();
		
		for (int i = 0; i < paths.length; ++i) {
			Image image = ImageLoader.loadImageRGBA(paths[i]);
			ByteBuffer buffer = image.getImageDataAsByteBuffer();
			
			glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL_RGBA, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);		
		}
		
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);
		
		this.unbind();
	}

}
