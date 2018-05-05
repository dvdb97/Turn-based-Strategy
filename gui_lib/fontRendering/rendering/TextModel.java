package fontRendering.rendering;

import assets.meshes.geometry.VertexLegacy;
import assets.models.Element_Model;
import fontRendering.texture.FontTexture;

import static org.lwjgl.opengl.GL11.*;

public class TextModel extends Element_Model {

	public TextModel(FontTexture font) {
		super(GL_TRIANGLES);
		
		this.setTexture(font);
	}
	
	
	public TextModel(VertexLegacy[] vertices, int[] indexArray, FontTexture font) {
		this(font);
		
		this.setData(vertices, indexArray);
	}

}
