package fontRendering.rendering;

import assets.meshes.geometry.Vertex;
import assets.models.Element_Model;
<<<<<<< HEAD
import fontRendering.font.FontTexture;
=======
import fontRendering.texture.FontTexture;
>>>>>>> parent of d0e5031... labeled elements

import static org.lwjgl.opengl.GL11.*;

public class TextModel extends Element_Model {

	public TextModel(FontTexture font) {
		super(GL_TRIANGLES);
		
		this.setTexture(font);
	}
	
	
	public TextModel(Vertex[] vertices, int[] indexArray, FontTexture font) {
		this(font);
		
		this.setData(vertices, indexArray);
	}

}
