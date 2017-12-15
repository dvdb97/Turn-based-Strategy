package fontRendering.texture;

import assets.meshes.geometry.Vertex;
import assets.textures.Texture2D;
import math.vectors.Vector2f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

public class FontTexture extends Texture2D {
	
	private int charsPerRow;
	
	private int charsPerCol;
	
	private char[] correspondingChars;
	
	
	public FontTexture(String path, int mipmapLevels, int charsPerRow, int charsPerCol) {
		super(path, mipmapLevels, GL_LINEAR, GL_CLAMP_TO_BORDER);
		
		this.charsPerRow = charsPerRow;
		
		this.charsPerCol = charsPerCol;
		
	}
	
	
	public FontTexture(String path, int mipmapLevels, int charsPerRow, int charsPerCol, char[] correspondingChars) {
		this(path, mipmapLevels, charsPerRow, charsPerCol);
		
		setCorrespondingChars(correspondingChars);
	}
	
	
	public void setCorrespondingChars(char[] correspondingChars) {
		this.correspondingChars = correspondingChars;
	}
	
	
	//******************************* look up functions *******************************
	
	
	private int getCharIndex(char c) {
		
		for (int i = 0; i < correspondingChars.length; ++i) {
			
			if (correspondingChars[i] == c) {
				return i;
			}
			
		}
		
		return charsPerCol * charsPerRow - 1;
		
	}
	
	
	public void mapToQuad(char c, Vertex upperLeft, Vertex upperRight, Vertex lowerLeft, Vertex lowerRight) {
		
		int index = getCharIndex(c);
		
		float x = getCharXPositionOnTexture(index);
		float y = getCharYPositionOnTexture(index);
		
		float width = getCharWidth();
		float height = getCharHeight();
		
		
		upperLeft.setTexturePositions(new Vector2f(x, y));
		upperRight.setTexturePositions(new Vector2f(x + width, y));
		lowerLeft.setTexturePositions(new Vector2f(x, y + height));
		lowerRight.setTexturePositions(new Vector2f(x + width, y + height));
		
	}
	
	
	private float getCharXPositionOnTexture(int index) {
		
		int x = index % charsPerRow;
		
		float charWidth = getCharWidth();
		
		return x * charWidth;		
		
	}
	
	
	private float getCharYPositionOnTexture(int index) {
		
		int y = index % charsPerCol;
		
		float charHeight = getCharHeight();
		
		return y * charHeight;
		
	}
	
	
	private float getCharWidth() {
		return 1.0f / charsPerRow;
	}
	
	
	private float getCharHeight() {
		return 1.0f / charsPerCol;
	}

}
