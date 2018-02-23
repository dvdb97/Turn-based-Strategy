package fontRendering.texture;

import assets.textures.Texture2D;

public class FontTexture extends Texture2D {
	
	private int charsPerRow;
	
	private int charsPerCol;
	
	private char[] correspondingChars;
	
	
	public FontTexture(String path, int charsPerRow, int charsPerCol) {
		super(path);
		
		this.charsPerRow = charsPerRow;
		
		this.charsPerCol = charsPerCol;
		
	}
	
	
	public FontTexture(String path, int mipmapLevels, int charsPerRow, int charsPerCol, char[] correspondingChars) {
		super(path, mipmapLevels);
		
		this.charsPerRow = charsPerRow;
		this.charsPerCol = charsPerCol;
		
		setCorrespondingChars(correspondingChars);
	}
	
	
	public void setCorrespondingChars(char[] correspondingChars) {
		this.correspondingChars = correspondingChars;
	}
	
	
	//******************************* look up functions *******************************
	
	
	public float getXPosition(char c) {
		return getCharXPositionOnTexture(getCharIndex(c));
	}
	
	
	public float getYPosition(char c) {
		return getCharYPositionOnTexture(getCharIndex(c));
	}
	
	
	private int getCharIndex(char c) {
		
		for (int i = 0; i < correspondingChars.length; ++i) {
			
			if (correspondingChars[i] == c) {
				return i;
			}
			
		}
		
		return charsPerCol * charsPerRow - 1;
		
	}
	
	
	private float getCharXPositionOnTexture(int index) {
		
		int x = index % charsPerRow;
		
		float charWidth = getCharWidth();
		
		return x * charWidth;		
		
	}
	
	
	private float getCharYPositionOnTexture(int index) {
		
		int y = index / charsPerRow;
		
		float charHeight = getCharHeight();
		
		return y * charHeight;
		
	}
	
	
	public float getCharWidth() {
		return 1.0f / charsPerRow;
	}
	
	
	public float getCharHeight() {
		return 1.0f / charsPerCol;
	}

}
