package assets.textures;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;
import static org.lwjgl.opengl.GL20.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

import assets.GLTargetObject;


public abstract class Texture extends GLTargetObject {

	public static final int RGBA_CHANNELS = 4;
	
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
		this(type);
		
		this.width = width;
		this.height = height;
	}
	
	
	public void setFilter(int filter) {
		setFilter(filter, filter);	
	}
	
	
	public void setFilter(int filter, int mipmapFilter) {
		this.bind();
		
		int combinedFilters = filter;
		
		if (filter == GL_LINEAR) {
			if (mipmapFilter == GL_LINEAR) {
				combinedFilters = GL_LINEAR_MIPMAP_LINEAR;
			} else {
				combinedFilters = GL_LINEAR_MIPMAP_NEAREST;
			}
		} else if (filter == GL_NEAREST) {
			if (mipmapFilter == GL_LINEAR) {
				combinedFilters = GL_NEAREST_MIPMAP_LINEAR;
			} else {
				combinedFilters = GL_NEAREST_MIPMAP_NEAREST;
			}
		}
		
		glTexParameteri(getType(), GL_TEXTURE_MAG_FILTER, combinedFilters);
		glTexParameteri(getType(), GL_TEXTURE_MIN_FILTER, combinedFilters);
		
		this.filter = combinedFilters;
		
		this.unbind();
	}
	
	
	public int getFilterMode() {
		return filter;
	}
	
	
	public void setTextureWrap(int wrapMode) {
		this.bind();
		
		glTexParameteri(getType(), GL_TEXTURE_WRAP_S, wrapMode);
		glTexParameteri(getType(), GL_TEXTURE_WRAP_T, wrapMode);
		glTexParameteri(getType(), GL_TEXTURE_WRAP_R, wrapMode);
		
		this.wrapMode = wrapMode;
		
		this.unbind();
	}
	
	
	public void setTextureSWrap(int wrapMode) {
		this.bind();
		
		glTexParameteri(getType(), GL_TEXTURE_WRAP_S, wrapMode);
		
		this.unbind();
	}
	
	
	public void setTextureTWrap(int wrapMode) {
		this.bind();
		
		glTexParameteri(getType(), GL_TEXTURE_WRAP_T, wrapMode);
		
		this.unbind();
	}
	
	
	public void setTextureRWrap(int wrapMode) {
		this.bind();
		
		glTexParameteri(getType(), GL_TEXTURE_WRAP_R, wrapMode);
		
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
	
	
	@Override
	public void bind() {
		glBindTexture(getType(), getID());		
	}


	@Override
	public void unbind() {
		glBindTexture(getType(), 0);		
	}	
	
	
	/**
	 * 
	 * Bind this texture to a specific texture unit.
	 * 
	 * @param texUnit The unit to bind the texture to.
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
	
	
	/**
	 * 
	 * Unbind this texture from a specific texture unit.
	 * 
	 * @param texUnit The unit to unbind the texture from.
	 */
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
	public void delete() {
		glDeleteTextures(getID());	
	}

}