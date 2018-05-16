package work_in_progress;

import assets.meshes.geometry.Color;
import assets.models.Element_Model;
import assets.textures.Texture2D;
import gui_core.GUIShaderCollection;
import rendering.RenderEngine;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

public class GUIElementModel extends Element_Model {
	
	private Color color;
	private Shape shape;
	private Texture2D texture;
	
	//*************************** constructor ***********************
	
	public GUIElementModel() {
		
		super(GL_TRIANGLES);
		
	}
	
	//****************************************************************
	
	public void render(GUIElementMatrix matrix) {
		
		if (texture != null) {
			GUIShaderCollection.useGuiShader(matrix.toMatrix44f());
		} else {
			GUIShaderCollection.useGuiShader(matrix.toMatrix44f(), color);
		}
		
		RenderEngine.draw(this, texture);
		
		GUIShaderCollection.disableGuiShader();
		
	}

	
	
}
