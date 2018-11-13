package assets.textures;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;


public abstract class Texture {
	
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
	
	//The number of mipmaplevels
	private int mipMapLevels = 1;
	
	//The width and height of the texture
	private int width, height;
	
	//the filter mode
	private int filter = LINEAR;
	
	//The wrap mode
	private int wrapMode = CLAMP_TO_EDGE;
	
	//storage set.
	private boolean storageAllocated = false;
	
	
	public Texture(int type, int width, int height) {
		ID = glGenTextures();

		this.width = width;
		
		this.height = height;
		
		this.TYPE = type;
	}
	
	
	public void setFilter(int filter) {
		setFilter(filter, filter);	
	}
	
	
	public void setFilter(int filter, int mipmapFilter) {
		
		this.bind();
		
		int combinedFilters = filter;
		
		if (filter == GL_LINEAR) {
			
			if (mipmapFilter == GL_LINEAR) {
				System.out.println("GL_LINEAR_MIPMAP_LINEAR");
				combinedFilters = GL_LINEAR_MIPMAP_LINEAR;
			} else {
				System.out.println("GL_LINEAR_MIPMAP_NEAREST");
				combinedFilters = GL_LINEAR_MIPMAP_NEAREST;
			}
			
		} else if (filter == GL_NEAREST) {
			
			if (mipmapFilter == GL_LINEAR) {
				System.out.println("GL_NEAREST_MIPMAP_LINEAR");
				combinedFilters = GL_NEAREST_MIPMAP_LINEAR;
			} else {
				System.out.println("GL_NEAREST_MIPMAP_NEAREST");
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
		
		this.wrapMode = wrapMode;
		
		this.unbind();
		
	}
	
	
	public int getWrapMode() {
		return wrapMode;
	}
	
	
	public void generateMipMapLevels() {
		glGenerateMipmap(getType());
	}
	
	
	protected boolean isStorageAllocated() {
		return storageAllocated;
	}
	
	
	protected void setStorageAllocated(boolean value) {
		this.storageAllocated = value;
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
