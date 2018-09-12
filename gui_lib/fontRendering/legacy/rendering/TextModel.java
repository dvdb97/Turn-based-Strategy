package fontRendering.legacy.rendering;

import assets.meshes.geometry.Vertex;
import assets.models.Element_Model;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class TextModel extends Element_Model {
	
	private String text;
	
	private int maxCharsPerRow;
	
	private int numRows;

	private TextModel(String text) {
		super(GL_TRIANGLES);
		
		this.text = text;
		
		parseText();
	}
	
	
	public TextModel(String text, Vertex[] vertices, int[] indexArray) {
		this(text);
		
		this.setData(vertices, indexArray);
	}
	
	
	public TextModel(String text, FloatBuffer posBuffer, FloatBuffer texPosBuffer, IntBuffer indexBuffer) {
		this(text);
		
		this.setVertexPositionData(posBuffer, 3, GL_STATIC_DRAW);
		this.setElementArrayData(indexBuffer);
		this.setVertexTexturePositionData(texPosBuffer, 2, GL_STATIC_DRAW);
		
		parseText();
		
	}
	
	
	public TextModel(String text, int numRows, int maxCharsPerRow, Vertex[] vertices, int[] indexArray) {
		super(GL_TRIANGLES);
		
		this.setData(vertices, indexArray);
		
		this.numRows = numRows;
		this.maxCharsPerRow = maxCharsPerRow;
		this.text = text;
		
	}
	
	
	public TextModel(String text, int numRows, int maxCharsPerRow, FloatBuffer posBuffer, FloatBuffer texPosBuffer, IntBuffer indexBuffer) {
		super(GL_TRIANGLES);
		
		this.numRows = numRows;
		this.maxCharsPerRow = maxCharsPerRow;
		this.text = text;
		
		this.setVertexPositionData(posBuffer, 3, GL_STATIC_DRAW);
		this.setElementArrayData(indexBuffer);
		this.setVertexTexturePositionData(texPosBuffer, 2, GL_STATIC_DRAW);
		
	}
	
	
	private void parseText() {
		
		this.numRows = 0;
		this.maxCharsPerRow = 0;
		
		for (int i = 0, charCounter = 0; i < text.length(); i++) {
			
			if (text.charAt(i) == '\n') {
				this.numRows++;
				
				if (charCounter > maxCharsPerRow) {
					maxCharsPerRow = charCounter;
				}
				
				charCounter = 0;
				
				continue;
			}
			
			if (text.charAt(i) == '\t') {
				charCounter += 6;
				
				continue;
			}
			
			charCounter++;
			
		}
		
	}


	public String getText() {
		return text;
	}


	public int getMaxCharsPerRow() {
		return maxCharsPerRow;
	}


	public int getNumRows() {
		return numRows;
	}

}
