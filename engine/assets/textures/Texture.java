package assets.textures;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;
import static org.lwjgl.opengl.GL20.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

import assets.GLTargetObject;


public abstract class Texture extends GLTargetObject {

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
	
	
	//The number of mipmaplevels
	private int mipMapLevels = 1;
	
	//The width and height of the texture
	private int width, height;
	
	//the filter mode
	private int filter = LINEAR;
	
	//The wrap mode
	private int wrapMode = CLAMP_TO_EDGE;
	
	
	public Texture(int type) {
		super(glGenTextures(), type);
		
	}
	
	
	public Texture(int type, int width, int height) {
		super(glGenTextures(), type);
		
		this.width = width;
		this.height = height;
	}
	
	
	public void setFilter(int filter) {
		
		this.bind();
		
		glTexParameteri(getType(), GL_TEXTURE_MAG_FILTER, filter);
		glTexParameteri(getType(), GL_TEXTURE_MIN_FILTER, filter);
		
		this.filter = filter;
		
		this.unbind();
		
	}
	
	
	public int getFilterMode() {
		return filter;
	}
	
	
	public void setTextureWrap(int wrapMode) {
		
		this.bind();
		
		glTexParameteri(getType(), GL_TEXTURE_WRAP_S, wrapMode);
		glTexParameteri(getType(), GL_TEXTURE_WRAP_T, wrapMode);
		
		this.wrapMode = wrapMode;
		
		this.unbind();
		
	}
	
	
	public int getWrapMode() {
		return wrapMode;
	}
	
	
	public void generateMipMapLevels() {
		glGenerateMipmap(getType());
	}
	
	
	public void setMipMapLevels(int mipmaplevels) {
		this.mipMapLevels = mipmaplevels;
	}
	
	
	public int getMipMapLevels() {
		return mipMapLevels;
	}
	
	
	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public int getHeight() {
		return height;
	}

	
	public void setHeight(int height) {
		this.height = height;
	}
	
	
	/**
	 * 
	 * Bind this texture to a specific texture unit.
	 * 
	 * @param texUnit
	 */
	public void bind(int texUnit) {
		if (texUnit < GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS) {
			glActiveTexture(texUnit);
			this.bind();
		} else {
			System.err.println("Cannot bind texture " + getID() + " to texture unit " + 
							   	texUnit + " as it exceeds Opengl's limit.");
		}
	}
	
	
	public void unbind(int texUnit) {
		if (texUnit < GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS) {
			glActiveTexture(texUnit);
			this.unbind();
		} else {
			System.err.println("Cannot unbind texture " + getID() + " from texture unit " + 
								texUnit + " as it exceeds Opengl's limit.");
		}
	}
	
	
	@Override
	public void bind() {
		glBindTexture(getType(), getID());		
	}


	@Override
	public void unbind() {
		glBindTexture(getType(), 0);		
	}


	@Override
	public void delete() {
		glDeleteTextures(getID());	
	}

}