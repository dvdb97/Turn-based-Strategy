package assets.textures;

import static org.lwjgl.opengl.GL30.GL_TEXTURE_2D_ARRAY;

public class ArrayTexture2D extends Texture3D {

	public ArrayTexture2D() {
		super(GL_TEXTURE_2D_ARRAY);
	}

	
	public ArrayTexture2D(int width, int height, int depth) {
		super(GL_TEXTURE_2D_ARRAY, width, height, depth);
	}
	
	
	public ArrayTexture2D(int numOfSubTextures, int width, int height, String[] paths, String[] labels) {
		super(GL_TEXTURE_2D_ARRAY, width, height, paths.length);
		
		this.setImageData(paths);
	}
	
}

