package fontRendering.generation;

import fontRendering.font.FontTexture;
import fontRendering.generation.functions.FontFunction;
import fontRendering.rendering.TextModel;
import gui_core.GUIMatrixManager;
import math.matrices.Matrix33f;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;
import math.vectors.Vector4f;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class TextGenerator {
	
	private static final float[] data = {
			0.0f,  0.0f, -1.0f,
			1.0f,  0.0f, -1.0f,
			0.0f, -1.0f, -1.0f,
			1.0f, -1.0f, -1.0f		
		};
	
	//A template of texture coordinates for easy texture mapping
	private static final Vector3f[] texCoords = {
		new Vector3f(0.0f, 0.0f, 1.0f),
		new Vector3f(1.0f, 0.0f, 1.0f),
		new Vector3f(0.0f, 1.0f, 1.0f),
		new Vector3f(1.0f, 1.0f, 1.0f),
	};
	
	//A template of vertex positions to generate 
	private static final Vector4f[] coords = {
		new Vector4f(0.0f,  0.0f, -1.0f, 1.0f),
		new Vector4f(1.0f,  0.0f, -1.0f, 1.0f),
		new Vector4f(0.0f, -1.0f, -1.0f, 1.0f),
		new Vector4f(1.0f, -1.0f, -1.0f, 1.0f)		
	};
	
	
	//A template of the index array used to draw those rectangles
	private final static int[] indices = {
		0, 1, 3,
		0, 2, 3,
	};
	
	
	public static TextModel generateTextModel(String text, FontTexture font, FontFunction func) {
		
		final int QUAD_VERTICES = 4;
		final int QUAD_INDICES = 6;
		final int POS_DATA_SIZE = 3;
		final int TEX_COORD_DATA_SIZE = 2;
		
		FloatBuffer positionBuffer = BufferUtils.createFloatBuffer(text.length() * QUAD_VERTICES * POS_DATA_SIZE);
		FloatBuffer texPosBuffer = BufferUtils.createFloatBuffer(text.length() * QUAD_VERTICES * TEX_COORD_DATA_SIZE);
		IntBuffer indexBuffer = BufferUtils.createIntBuffer(text.length() * QUAD_INDICES);
		
		//The current row of the text. Will be increased when the char '\b' marks the end of a line
		int line = 0;
		
		//The index of the current char in the current row
		int posInLine = 0;
		
		//The longest row in characters
		int longestLineSize = 0;
		
		//The transformationMatrix for the position data
		Matrix44f letterTM;
		
		//The size of a letter on the texture in uv-coords
		float texCharWidth = font.getCharWidth();
		float texCharHeight = font.getCharHeight();
		
		//The position of the subtexture in uv-coords
		float texPosXOffset, texPosYOffset;
		
		Matrix33f textureTM;
		
		
		for (int letterIndex = 0; letterIndex < text.length(); ++letterIndex) {
			
			char letter = text.charAt(letterIndex);
			
			//If the next letter ends the line
			if (letter == '\n') {
				++line;
				
				if (posInLine + 1 > longestLineSize) {
					longestLineSize = posInLine;
				}
				
				posInLine = 0;
				
				continue;
			}
			
			texPosXOffset = font.getXPosition(letter);
			texPosYOffset = font.getYPosition(letter);
			
			//The transformation matrix to put the next vertex into the right position
			letterTM = GUIMatrixManager.generateRenderingMatrix(posInLine, line, 1, 1);
			
			//The transformation matrix to transform the tex coords to display a specific letter
			textureTM = GUIMatrixManager.generateTransformationMatrix(texPosXOffset, texPosYOffset, texCharWidth, texCharHeight);
			
			
			for (int vertexIndex = 0; vertexIndex < coords.length; ++vertexIndex) {
				
				positionBuffer.put(letterTM.times(coords[vertexIndex]).toVector3f().toArray());

				texPosBuffer.put(textureTM.times(texCoords[vertexIndex]).toVector2f().toArray());
				
			}
			
			
			for (int index = 0; index < indices.length; ++index) {
				indexBuffer.put(letterIndex * QUAD_VERTICES + indices[index]);
			}
			

			++posInLine;
					
		}
		
		if (posInLine + 1 > longestLineSize) {
			longestLineSize = posInLine;
		}
		
		positionBuffer.flip();
		texPosBuffer.flip();
		indexBuffer.flip();
		
		TextModel model = new TextModel(text, line, longestLineSize, positionBuffer, texPosBuffer, indexBuffer);
		
		return model;
		
	}

}


/*
private static final Vector3f[] texCoords = {
		new Vector3f(0.0f, 0.0f, 1.0f),
		new Vector3f(1.0f, 0.0f, 1.0f),
		new Vector3f(0.0f, 1.0f, 1.0f),
		new Vector3f(1.0f, 1.0f, 1.0f),
	};
	
	//A template of vertex positions to generate 
	private static final Vector4f[] coords = {
		new Vector4f(0.0f, 1.0f, -1.0f, 1.0f),
		new Vector4f(1.0f, 1.0f, -1.0f, 1.0f),
		new Vector4f(0.0f, 0.0f, -1.0f, 1.0f),
		new Vector4f(1.0f, 0.0f, -1.0f, 1.0f)		
	};*/
