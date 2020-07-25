package assets.textures;

import static org.lwjgl.opengl.GL30.GL_TEXTURE_2D_ARRAY;

public class ArrayTexture2D extends Texture3D {

	public ArrayTexture2D() {
		super(GL_TEXTURE_2D_ARRAY);
	}

	
	public ArrayTexture2D(String[] paths) {
		super();
		
		this.setImageData(paths);
	}
	
}

