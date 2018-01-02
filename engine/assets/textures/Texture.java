package assets.textures;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;


public class Texture {
	
	//Linear filtering
	public static final int LINEAR = GL_LINEAR;
	
	//Nearest neighbor filtering
	public static final int NEAREST = GL_NEAREST;
	
	//Clamp mode - Repeat the texture. 
	public static final int REPEAT = GL_REPEAT;
	
	//Clamp mode - clamp the texture to the edge. 
	public static final int CLAMP_TO_EDGE = GL_CLAMP_TO_EDGE;
	
	//Clamp mode - clamp the texture to the border.
	public static final int CLAMP_TO_BORDER = GL_CLAMP_TO_BORDER;
	
	
	
	//The opengl ID of this texture
	private final int ID;
	
	//The type of the texture
	private final int TYPE;
	
	
	public Texture(int type) {
		ID = glGenTextures();

		
		this.TYPE = type;
	}
	
	
	public void setFilter(int filter) {
		
		glTexParameteri(getType(), GL_TEXTURE_MAG_FILTER, filter);
		glTexParameteri(getType(), GL_TEXTURE_MIN_FILTER, filter);				
		
	}
	
	
	public void setTextureWrap(int wrapMode) {
		
		glTexParameteri(getType(), GL_TEXTURE_WRAP_S, wrapMode);
		glTexParameteri(getType(), GL_TEXTURE_WRAP_T, wrapMode);
		
	}
	
	
	public void bind() {
		glBindTexture(TYPE, ID);
	}
	
	
	public void unbind() {
		glBindTexture(TYPE, 0);
	}
	
	
	public void delete() {
		glDeleteTextures(ID);
	}
	
	
	public int getType() {
		return TYPE;
	}
	
	
	public int getID() {
		return ID;
	}

}
