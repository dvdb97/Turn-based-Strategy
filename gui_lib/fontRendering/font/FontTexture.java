package fontRendering.font;

import assets.textures.Texture2D;

public class FontTexture extends Texture2D {

	
	private char[] chars;
	
	private int charsPerRow;
	
	private int charsPerCol;
	
<<<<<<< HEAD:gui_lib/fontRendering/texture/FontTexture.java
	private char[] correspondingChars;
	
	
	public FontTexture(String path, int charsPerRow, int charsPerCol) {
		super(path);
=======

	public FontTexture(String path, int width, int height, int charsPerRow, int charsPerCol) {
		super(path, width, height);
>>>>>>> master:gui_lib/fontRendering/font/FontTexture.java
		
		this.setFilter(Texture.CLAMP_TO_BORDER);
		this.setTextureWrap(Texture.LINEAR);
		
		this.charsPerRow = charsPerRow;
		this.charsPerCol = charsPerCol;
		
<<<<<<< HEAD:gui_lib/fontRendering/texture/FontTexture.java
	}
	
	
	public FontTexture(String path, int mipmapLevels, int charsPerRow, int charsPerCol, char[] correspondingChars) {
		super(path, mipmapLevels);
		
		this.charsPerRow = charsPerRow;
		this.charsPerCol = charsPerCol;
		
		setCorrespondingChars(correspondingChars);
	}
	
	
	public void setCorrespondingChars(char[] correspondingChars) {
		this.correspondingChars = correspondingChars;
=======
>>>>>>> master:gui_lib/fontRendering/font/FontTexture.java
	}
	
	
	public float getXPosition(char c) {
		return getCharXPositionOnTexture(getCharIndex(c));
	}
	
	
	public float getYPosition(char c) {
		return getCharYPositionOnTexture(getCharIndex(c));
	}
	
	
	private int getCharIndex(char c) {
		
		for (int i = 0; i < chars.length; ++i) {
			
			if (chars[i] == c) {
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


	public char[] getChars() {
		return chars;
	}


	public void setChars(char[] chars) {
		this.chars = chars;
	}

}
