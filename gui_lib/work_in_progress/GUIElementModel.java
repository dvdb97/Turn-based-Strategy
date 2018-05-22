package work_in_progress;

import assets.meshes.geometry.Color;
import assets.models.Element_Model;
import assets.textures.Texture2D;
import gui_core.GUIShaderCollection;
import rendering.RenderEngine;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * a class to encapsulate shape, color and texture of a gui element
 * @author jona
 *
 */
public class GUIElementModel extends Element_Model {
	
	private Color color;
	private Shape shape;
	private Texture2D texture;
	
	//*************************** constructor ***********************
	
	public GUIElementModel() {
		
		super(GL_TRIANGLES);
		
	}
	
	//****************************************************************
	
	public boolean isHit(float cursorX, float cursorY) {
		return shape.isHit(cursorX, cursorY);
	}
	
	/**
	 * 
	 * @return Returns the position data to draw this shape
	 */
	public FloatBuffer getPositionData() {
		return shape.getPositionData();
	}
	
	
	/**
	 * 
	 * @return Returns the texture position data to draw this shape
	 */
	public FloatBuffer getTexPosData() {
		return shape.getTexPosData();
	}
	
	
	/**
	 * 
	 * @return Returns the index array to draw this shape
	 */
	public IntBuffer getIndexData() {
		return shape.getIndexData();
	}
	
	protected void loadData() {
		
		this.setVertexPositionData(getPositionData(), 3, GL_STATIC_DRAW);
		this.setVertexTexturePositionData(getTexPosData(), 2, GL_STATIC_DRAW);
		this.setElementArrayData(getIndexData());
		
	}
	
	//****************************************************************
	
	public void render(GUIElementMatrix matrix) {
		
		if (texture != null) {
			GUIShaderCollection.useGuiShader(matrix.toMatrix44f());
		} else {
			GUIShaderCollection.useGuiShader(matrix.toMatrix44f(), color.toVector4f());
		}
		
		RenderEngine.draw(this, texture);
		
		GUIShaderCollection.disableGuiShader();
		
	}

	
	
}
