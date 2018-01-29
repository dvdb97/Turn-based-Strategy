package rendering.shapes;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

import assets.models.Element_Model;
import assets.textures.Texture2D;
import gui_core.GUIManager;
import math.matrices.Matrix33f;
import math.matrices.Matrix44f;
import math.vectors.Vector4f;
import rendering.RenderEngine;

public abstract class GUIShape extends Element_Model {
	
	public GUIShape() {
		super(GL_TRIANGLES);
		
	}
	
	
	protected void loadData() {
		
		this.setVertexPositionData(getPositionData(), 3, GL_STATIC_DRAW);
		this.setVertexTexturePositionData(getTexPosData(), 2, GL_STATIC_DRAW);
		this.setElementArrayData(getIndexData());
		
	}
	
	
	/**
	 * 
	 * @return Returns the position data to draw this shape
	 */
	public abstract FloatBuffer getPositionData();
	
	
	/**
	 * 
	 * @return Returns the texture position data to draw this shape
	 */
	public abstract FloatBuffer getTexPosData();
	
	
	/**
	 * 
	 * @return Returns the index array to draw this shape
	 */
	public abstract IntBuffer getIndexData();
	
	
	/**
	 * 
	 * Checks if the cursor is on the Shape.
	 * 
	 * @param cursorX The x position of the cursor.
	 * @param cursorY The y position of the cursor.
	 * @return Returns Wether the cursor ist on the this shape.
	 */
	public abstract boolean isHit(float cursorX, float cursorY);
	
	
	/**
	 * 
	 * Renders the Shape on the screen.
	 * 
	 * @param texture The texture that is needed to render this shape. Can be null.
	 * @param color The color that is needed to render this shape. Can be null.
	 * @param matrix A 3x3 Matrix to transform the shape when rendering.
	 */
	public void render(Texture2D texture, Vector4f color, Matrix44f matrix) {
		
		if (texture != null) {
			GUIManager.useGuiShader(matrix);
		} else {
			GUIManager.useGuiShader(matrix, color);
		}
		
		RenderEngine.draw(this, texture);
		
		GUIManager.disableGuiShader();
		
	}

}
