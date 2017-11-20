package assets.textures;


import static org.lwjgl.opengl.GL11.*;


public class Texture {
	
	private final int ID;
	
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
	
	
	public int getType() {
		return TYPE;
	}
	
	
	public int getID() {
		return ID;
	}

}
