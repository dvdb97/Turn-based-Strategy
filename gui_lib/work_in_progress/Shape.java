package work_in_progress;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import assets.meshes.geometry.Color;
import assets.models.Element_Model;
import assets.textures.Texture2D;
import gui_core.GUIShaderCollection;
import math.vectors.Vector4f;
import rendering.RenderEngine;

public abstract class Shape extends Element_Model {
	
	public Shape() {
		super(GL_TRIANGLES);
	}

	public abstract boolean isHit(float cursorX, float cursorY);
	
	//-----------------------------------
	//from GUIShape
	
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
	
	protected void loadData() {
		
		this.setVertexPositionData(getPositionData(), 3, GL_STATIC_DRAW);
		this.setVertexTexturePositionData(getTexPosData(), 2, GL_STATIC_DRAW);
		this.setElementArrayData(getIndexData());
		
	}
	
	/**
	 * 
	 * Renders the Shape on the screen.
	 * 
	 * @param texture The texture that is needed to render this shape. Can be null.
	 * @param color The color that is needed to render this shape. Can be null.
	 * @param matrix A 3x3 Matrix to transform the shape when rendering.
	 */
	public void render(Texture2D texture, Color color, GUIElementMatrix matrix) {
		
		if (texture != null) {
			GUIShaderCollection.useGuiShader(matrix.toMatrix44f());
		} else {
			GUIShaderCollection.useGuiShader(matrix.toMatrix44f(), color);
		}
		
		RenderEngine.draw(this, texture);
		
		GUIShaderCollection.disableGuiShader();
		
	}
	
	//----------------------------------
	
}
