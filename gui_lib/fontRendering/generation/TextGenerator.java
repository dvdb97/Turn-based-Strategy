package fontRendering.generation;

import assets.models.Element_Model;
import fontRendering.font.texture.FontTexture;
import fontRendering.generation.functions.FontFunction;
import math.vectors.Vector3f;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class TextGenerator {
	
	//A template of texture coordinates for easy texture mapping
	private final static float[] texCoords = {
		0.0f, 0.0f,
		1.0f, 0.0f,
		0.0f, 1.0f,
		1.0f, 1.0f,
	};
	
	//A template of vertex positions to generate 
	private final  static float[] coords = {
		0.0f,  0.0f,
		1.0f,  0.0f,
		0.0f, -1.0f,
		1.0f, -1.0f
	};
	
	
	//A template of the index array used to draw those rectangles
	private final static int[] indices = {
		0, 1, 3,
		0, 2, 3,
	};
	
	
	public static Element_Model generateTextModel(String text, float x, float y, float z, float width, float height, FontTexture font, FontFunction func) {
		
		return generateTextModel(text.toCharArray(), x, y, z, width, height, font, func);
		
	}
	
	
	public static Element_Model generateTextModel(char[] text, float x, float y, float z, float width, float height, FontTexture font, FontFunction func) {
		
		final int QUAD_VERTICES = 4;
		final int QUAD_INDICES = 6;
		final int POS_DATA_SIZE = 3;
		final int TEX_COORD_DATA_SIZE = 2;
		
		FloatBuffer positionBuffer = BufferUtils.createFloatBuffer(text.length * QUAD_VERTICES * POS_DATA_SIZE);
		FloatBuffer texCoordsBuffer = BufferUtils.createFloatBuffer(text.length * QUAD_VERTICES * TEX_COORD_DATA_SIZE);
		IntBuffer indexBuffer = BufferUtils.createIntBuffer(text.length * QUAD_INDICES);
		
		//The x and y position of the current char
		float xPos, yPos;
		
		//The current row of the text. Will be increased when the char '\b' marks the end of a line
		int row = 0;
		//The index of the current char in the current row
		int rowIndex = 0;
		
		//The width and height of a char
		float charWidth = width / text.length;
		float charHeight = height / getNumberOfRows(text);
		
		//The position of the subtexture on the texture in uv-coords
		float texPosXOffset, texPosYOffset;
		
		//The width of a subtexture on the texture in uv-coords
		float texCharWidth = font.getCharWidth();
		float texCharHeight = font.getCharHeight();
		
		//The texCoords adapted to the subtexture size
		float[] convertedTexCoords = adaptToCharSize(texCharWidth, texCharHeight);
		
		for (int letterIndex = 0; letterIndex < text.length; ++letterIndex) {
			
			rowIndex++;
			
			char letter = text[letterIndex];
			
			if (letter == '\n') {
				row++;
				rowIndex = 0;
			}
			
			texPosXOffset = font.getXPosition(letter);
			texPosYOffset = font.getYPosition(letter);
			
			xPos = x + rowIndex * charWidth;
			yPos = y - row * charHeight;
			
			for (int vertex = 0; vertex < QUAD_VERTICES; ++vertex) {
				
				texCoordsBuffer.put(texPosXOffset + convertedTexCoords[vertex * TEX_COORD_DATA_SIZE]);
				texCoordsBuffer.put(texPosYOffset + convertedTexCoords[vertex * TEX_COORD_DATA_SIZE + 1]);
							
				Vector3f position = new Vector3f(xPos + charWidth * coords[vertex * 2], yPos + charHeight * coords[vertex * 2 + 1], z);
				positionBuffer.put(position.toArray());
				
			}
			
			for (int index = 0; index < indices.length; ++index) {
				indexBuffer.put(letterIndex * QUAD_VERTICES + indices[index]);
			}
			
			
			
		}
		
		positionBuffer.flip();
		texCoordsBuffer.flip();
		indexBuffer.flip();
		
		Element_Model model = new Element_Model(GL_TRIANGLES);
		
		model.setVertexPositionData(positionBuffer, POS_DATA_SIZE, GL_STATIC_DRAW);
		model.setVertexTexturePositionData(texCoordsBuffer, TEX_COORD_DATA_SIZE, GL_STATIC_DRAW);
		model.setElementArrayData(indexBuffer);
		
		return model;
		
	}
	
	
	private static int getNumberOfRows(char[] s) {
		
		int numOfRows = 1;
		
		for (int i = 0; i < s.length; ++i) {
			
			if (s[i] == '\n') {
				numOfRows++;
			}
			
		}
		
		return numOfRows;
		
	}
	
	
	
	private static float[] adaptToCharSize(float width, float height) {
		
		float[] convertedTexCoords = new float[texCoords.length];
		
		for (int i = 0; i < texCoords.length; ++i) {
			
			if (i % 2 == 0) {
				convertedTexCoords[i] = texCoords[i] * width;
			} else {
				convertedTexCoords[i] = texCoords[i] * height;
			}
			
		}
		
		return convertedTexCoords;
		
	}

}
