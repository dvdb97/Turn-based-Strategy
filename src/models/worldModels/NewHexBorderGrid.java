package models.worldModels;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL31.GL_PRIMITIVE_RESTART;
import static org.lwjgl.opengl.GL31.glPrimitiveRestartIndex;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glDisable;


import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import assets.material.Material;
import assets.meshes.geometry.Color;
import assets.meshes.geometry.Vertex;
import assets.models.Element_Model;
import math.vectors.Vector3f;
import utils.Const;
import utils.CustomBufferUtils;
import models.seeds.ColorFunction;

public class NewHexBorderGrid extends Element_Model {
	
	private int length, width;
	
	private int lengthMod2;
	
	private float edgeLength;
	private float triangleAltitude;
	
	private float[][] elevation;
	
	ColorFunction colorFunc;
	
	private final int PRI = -1;			//primitive restart index
	
	private ArrayList<Vector3f> positions;
	
	
	
	//************************************** constructor *************************************	
	
	private NewHexBorderGrid(Vertex[] vertices) {
		
		super(GL_LINE_STRIP);
		
		processGrid();
		
	}
	
	//*************************************** prime method ****************************************
	
	private void processGrid() {
		
		processPositionAndColor();
		
		processElementArray();
		
	}
	
	
	
	//*************************************** secondary methods **********************************
	
	private void processPositionAndColor() {
		
		positions = new ArrayList<>(length * width * 3);
		FloatBuffer colBuffer = BufferUtils.createFloatBuffer(length * width * 4);
		
		for (int y=0; y<width; y++) {
			
			for (int x=0; x<length; x++) {
				
				positions.add(new Vector3f(x*triangleAltitude, (y+0.5f*(x%2))*edgeLength, elevation[x][y]));
				
				colBuffer.put(colorFunc.color(x, y, elevation[x][y]).toArray());
				
			}
			
		}
		
		
		colBuffer.flip();
		
		this.setVertexPositionData(CustomBufferUtils.createFloatBuffer(positions), 3, GL_STATIC_DRAW);
		this.setVertexColorData(colBuffer, 4, GL_STATIC_DRAW);
		
	}
	
	
	private void processElementArray() {
		
		IntBuffer elementBuffer = BufferUtils.createIntBuffer( (width*2+1)*(length-1));
		
		for (int col=0; col<length-1; col++) {
			
			for (int row=0; row<width; row++) {
				
				elementBuffer.put(col+row*length);
				elementBuffer.put(col+row*length+1);
				
			}
			
			elementBuffer.put(PRI);
			
		}
		
		elementBuffer.flip();
		
		this.setElementArrayData(elementBuffer);
		
	}
	
	
	
	//********************************** other stuff **********************************************
	public void onDrawStart() {
		super.onDrawStart();
		
		glEnable(GL_PRIMITIVE_RESTART);
		glPrimitiveRestartIndex(PRI);
	}
	
	
	public void onDrawStop() {
		super.onDrawStop();
		
		glDisable(GL_PRIMITIVE_RESTART);
		
	}
	
	//******************************************** get & set **************************************
	
	/**
	 * @return the length of the mesh in knots (vertices)
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @return the width of the mesh in knots (vertices)
	 */
	public int getWidth() {
		return width;
	}
	
	//TODO: bad methods names
	//TODO: java doc
	public float getTotalWidth() {
		return width * edgeLength;
	}
	
	public float getTotalHeight() {
		return length * edgeLength;
	}
	
	
}
