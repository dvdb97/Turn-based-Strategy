package assets.models.abstractModels;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import math.matrices.Matrix44f;

/*
 * TODO: Remove most of the functions and just create one function for saving data on
 * the gpu:
 * 
 * void setData(FloatBuffer data, int attributeID, int size, int accessibilty);
 * 
 * store all bufferIDs in a list
 */


public abstract class Model extends PrimitiveModel implements Renderable {
	
	
	//****************************** Constants ******************************
	
	
	public static final int POS_ARRAY_INDEX = 0;
	public static final int COL_ARRAY_INDEX = 1;
	public static final int TEX_POS_ARRAY_INDEX = 2;
	public static final int NORMAL_ARRAY_INDEX = 3;
	
	
	//****************************** Member variables ******************************
	
	
	//List to store all vertex attribute pointers needed for this model.
	private List<Integer> indexList = new ArrayList<Integer>();
	
	
	//The vertex array object
	private int vaoID;
	
	
	private Matrix44f transformationMatrix;
	
	
	//The buffers
	private int positionBufferID = 0;
		
	private int colorBufferID = 0;
		
	private int texturePositionBufferID = 0;
	
	private int normalBufferID = 0;
	
	private int drawMode = 0;
	
	
	public Model(int drawMode) {
		this.drawMode = drawMode;
		
		vaoID = glGenVertexArrays();
	}
	
	
	public abstract void onDrawStart();
	
	
	public abstract void onDrawStop();
	
	
	public abstract void render();
	
	
	public void activateAttributes() {
		
		glBindVertexArray(this.getVaoID());
		
		for (int attribute : indexList) {
			glEnableVertexAttribArray(attribute);
		}
		
	}
	
	
	public void deactivateAttributes() {
		
		for (int attribute : indexList) {
			glDisableVertexAttribArray(attribute);
		}
		
		glBindVertexArray(0);
		
	}
	
	
	
	//****************************** Data Management ******************************
	
	
	/*  
	 * positionDataBuffer - the actual data to be stored. Make sure that the buffer is actually flipped!
	 *  size - how many values in this array do actually belong to one vertex?
	 */
	
	public void setVertexPositionData(FloatBuffer positionDataBuffer, int vaIndex, int size, int accessibilty) {
		
		if (positionBufferID != 0) {
			System.out.println("There is already vertex postion data stored in " + this.toString() + "!" + 
							   "In the current version of this RenderEngine it is not possible to change the data or add new data!");
			
			return;		
		}
		
		if (size <= 1) {
			System.out.println("A size smaller than two doesnt make any sense. " +
							   "I changed it to two as my glorious programmer taught me to!");
		
			size = 2;
		}
		
		glBindVertexArray(getVaoID());
		
		positionBufferID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, positionBufferID);
		
		glBufferData(GL_ARRAY_BUFFER, positionDataBuffer, accessibilty);
		
		glVertexAttribPointer(vaIndex, size, GL_FLOAT, false, 0, 0);
		indexList.add(vaIndex);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glBindVertexArray(0);
		
	}
	
	
	public void setVertexPositionData(FloatBuffer positionDataBuffer, int size, int accessibilty) {
		
		setVertexPositionData(positionDataBuffer, POS_ARRAY_INDEX, size, accessibilty);
		
	}
	
	
	
	
	/* 
	 * colorDataBuffer - the actual data to be stored
	 * size - the amount of values that belong to one vertex
	 */	
	
	public void setVertexColorData(FloatBuffer colorDataBuffer, int vaIndex, int size, int accessibility) {
		
		if (colorBufferID != 0) {
			System.out.println("There is already vertex color data stored in " + this.toString() + "!" + 
							   "In the current version of this RenderEngine it is not possible to change the data or add new data!");
			
			return;		
		}
		
		if (size < 3) {
			System.out.println("This function doesn't accept a size smaller than 3. The size was automatically set to 3!");
			size = 3;
		}
		
		
		glBindVertexArray(getVaoID());
		
		colorBufferID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, colorBufferID);
		
		glBufferData(GL_ARRAY_BUFFER, colorDataBuffer, accessibility);
		
		glVertexAttribPointer(vaIndex, size, GL_FLOAT, false, 0, 0);
		indexList.add(vaIndex);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glBindVertexArray(0);
		
	}
	
	
	
	public void setVertexColorData(FloatBuffer colorDataBuffer, int size, int accessibility) {
		
		setVertexColorData(colorDataBuffer, COL_ARRAY_INDEX, size, accessibility);
		
	}
	
	
	
	public void setTexturePositionData(FloatBuffer texturePositionDataBuffer, int vaIndex, int size, int accessibility) {
		
		if (texturePositionBufferID != 0) {
			System.out.println("There is already texture position data stored in " + this.toString() + "!" + 
					   "In the current version of this RenderEngine it is not possible to change the data or add new data!");
			
			return;	
		}
		
		
		glBindVertexArray(getVaoID());
		
		texturePositionBufferID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, texturePositionBufferID);
		
		glBufferData(GL_ARRAY_BUFFER, texturePositionDataBuffer, accessibility);
		
		glVertexAttribPointer(vaIndex, 3, GL_FLOAT, false, 0, 0);
		indexList.add(vaIndex);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glBindVertexArray(0);
		
	}
	
	
	
	public void setVertexTexturePositionData(FloatBuffer texturePositionDataBuffer, int size, int accessibility) {
		
		setTexturePositionData(texturePositionDataBuffer, TEX_POS_ARRAY_INDEX, size, accessibility);		
		
	}
	
	
	
	
	
	//Sets the normal data of each vertex
	public void setVertexNormalData(FloatBuffer normalData, int vaIndex, int accessibility) {
		
		if (normalBufferID != 0) {
			System.out.println("There is already normal data stored in " + this.toString() + "!" + 
					   "In the current version of this RenderEngine it is not possible to change the data or add new data!");
			
			return;	
		}
		
		glBindVertexArray(getVaoID());
		
		normalBufferID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, normalBufferID);
		
		glBufferData(GL_ARRAY_BUFFER, normalData, accessibility);
		
		glVertexAttribPointer(vaIndex, 3, GL_FLOAT, false, 0, 0);
		indexList.add(vaIndex);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glBindVertexArray(0);
		
	}
	
	
	
	public void setVertexNormalData(FloatBuffer normalData, int accessibility) {
		
		setVertexNormalData(normalData, NORMAL_ARRAY_INDEX, accessibility);
		
	}	
	
	
	
	public void setSubData(int offset, FloatBuffer positionData, FloatBuffer colorData, FloatBuffer texturePositionData) {
		
		setVertexPositionSubData(positionData, offset);
		
		if (colorData != null) {
			setVertexColorSubData(colorData, offset);
		}
		
		if (texturePositionData != null) {
			setVertexTexturePosSubData(texturePositionData, offset);
		}
		
	}
	
	
	
	
	
	//The Offset is measured in vertices
	public void setVertexPositionSubData(FloatBuffer positionDataBuffer, int offset) {
		glBindVertexArray(vaoID);
		glBindBuffer(GL_ARRAY_BUFFER, positionBufferID);
		
		glBufferSubData(GL_ARRAY_BUFFER, offset * 3 * Float.BYTES, positionDataBuffer);
		//TODO: Maybe there is a vertex attribute pointer needed
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}
	
	
	
	
	
	//The offset is measured in vertices
	public void setVertexColorSubData(FloatBuffer colorDataBuffer, int offset) {
		glBindVertexArray(vaoID);
		glBindBuffer(GL_ARRAY_BUFFER, positionBufferID);
		
		glBufferSubData(GL_ARRAY_BUFFER, offset * 3 * Float.BYTES, colorDataBuffer);
		//TODO: Maybe there is a vertex attribute pointer needed
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}
	
	
	
	
	
	//The offset is measured in vertices
	public void setVertexTexturePosSubData(FloatBuffer texturePositionBuffer, int offset) {
		glBindVertexArray(vaoID);
		glBindBuffer(GL_ARRAY_BUFFER, positionBufferID);
		
		glBufferSubData(GL_ARRAY_BUFFER, offset * 2 * Float.BYTES, texturePositionBuffer);
		//TODO: Maybe there is a vertex attribute pointer needed		
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}
	
	
	//****************************** Getters & Setters ******************************
	
	
	public int getVaoID() {
		return vaoID;
	}
	
	
	public int getArrayIndex(int i) {
		return indexList.get(i);
	}
	
	
	public int getArrayIndexListLength() {
		return indexList.size();
	}
	
	
	public int getDrawMode() {
		return drawMode;
	}

	
	//****************************** Additional functions ******************************
	
	
	public void delete() {
		glDeleteBuffers(positionBufferID);
		glDeleteBuffers(colorBufferID);
		glDeleteBuffers(texturePositionBufferID);
	}
}