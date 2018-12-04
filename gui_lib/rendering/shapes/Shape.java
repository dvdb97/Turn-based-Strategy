package rendering.shapes;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import assets.meshes.Mesh;
import assets.meshes.geometry.Color;
import assets.textures.Texture2D;
import dataType.GUIElementMatrix;
import gui_core.GUIShaderCollection;
import math.vectors.Vector4f;
import rendering.RenderEngine;

//TODO: only substituted "Element_Model" by "Mesh"
public abstract class Shape extends Mesh {
	
	public Shape() {
		super(GL_TRIANGLES);
	}

	public abstract boolean isHit(float cursorX, float cursorY);
	
	
	//from GUIShape
	
	/**
	 * 
	 * Renders the Shape on the screen.
	 * 
	 * @param texture The texture that is needed to render this shape. Can be null.
	 * @param color The color that is needed to render this shape. Can be null.
	 * @param matrix A 3x3 Matrix to transform the shape when rendering.
	 */
/*	public void render(Texture2D texture, Color color, GUIElementMatrix matrix) {
		
		if (texture != null) {
			GUIShaderCollection.useGuiShader(matrix.toMatrix44f());
		} else {
			GUIShaderCollection.useGuiShader(matrix.toMatrix44f(), color.toVector4f());
		}
		
		RenderEngine.draw(this, texture);
		
		GUIShaderCollection.disableGuiShader();
		
	}*/
	
}
