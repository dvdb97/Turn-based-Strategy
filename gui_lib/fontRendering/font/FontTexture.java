package fontRendering.font;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import assets.textures.Texture;
import assets.textures.Texture2D;

public class FontTexture extends Texture2D {	

	public static final float GLYPH_WIDTH = 1.0f / 16;
	public static final float GLYPH_HEIGHT = 1.0f / 16;
	
	private static final Charset iso_8859_1 = Charset.forName("ISO-8859-1");;
	
	/**
	 * 
	 * Load a texture atlas containing characters.
	 * The required format for the font is the ISO-8859-1 format.
	 * 
	 * @param path The path of the texture.
	 * @param width The width of the texture in pixels.
	 * @param height The height of the texture in pixels.
	 */
	public FontTexture(String path, int width, int height) {
		super(path, Texture.LINEAR, Texture.NEAREST);
		
		this.setTextureWrap(Texture.CLAMP_TO_BORDER);
	}
	
	
	/**
	 * Encodes a given String in the ISO-8859-1 format.
	 * 
	 * @param s The String to encode.
	 * @return Returns a Bytebuffer containing the encoded chars.
	 */
	public static ByteBuffer toISO_8859_1(String s) {
		return iso_8859_1.encode(s);
	}

	
	/**
	 * 
	 * @return Returns the width of a glyph in texCoords.
	 */
	public static float getCharWidth() {
		return GLYPH_WIDTH;
	}
	
	
	/**
	 * 
	 * @return Returns the height of a glyph in texCoords.
	 */
	public static float getCharHeight() {
		return GLYPH_HEIGHT;
	}

	
	/**
	 *  
	 * @param b A char that has been encoded in ISO-8859-1 format.
	 * @return Returns the y position of the glyph on the texture.
	 */
	public static float getYPosition(byte b) {
		int row = b / 16;
		
		return row * getCharHeight();
	}

	
	/**
	 * 
	 * @param b A char that has been encoded in ISO-8859-1 format.
	 * @return Returns the x position of the glyph on the texture.
	 */
	public static float getXPosition(byte b) {
		int col = b % 16;
		
		return col * getCharWidth();
	}
}
