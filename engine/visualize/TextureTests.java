package visualize;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import assets.models.Element_Model;
import assets.textures.Texture;
import assets.textures.Texture2D;
import utils.CustomBufferUtils;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class TextureTests {
	
	private static Element_Model texturedModel;
	
	private static Texture2D texture;
	
	
	public static void init(String texturePath) {		
		
		float[] positionData = {
			-0.25f, 0.25f, 0f, //Upper left
			 0.0f,  0.25f, 0f, //Upper right
			-0.25f, 0.0f,  0f, //Lower left
			 0.0f,  0.0f,  0f,  //Lower right
			 
			 0.0f,  0.25f, 0f,
			 0.25f, 0.25f, 0f,
			 0.0f,  0.0f, 0f,
			 0.25f, 0.0f, 0f
		};
		
		int[] indexArray = {
			0, 1, 3,
			0, 2, 3,
			
			4, 5, 7,
			4, 6, 7
		};
		
		float[] texPosData = {
			0f, 0f,
			0.1f, 0f,
			0f, 0.1f,
			0.1f, 0.1f,
			
			0.4f, 0.3f,
			0.5f, 0.3f,
			0.4f, 0.4f,
			0.5f, 0.4f
		};
		
		
		FloatBuffer posBuffer = CustomBufferUtils.createFloatBuffer(positionData);
		FloatBuffer texPosBuffer = CustomBufferUtils.createFloatBuffer(texPosData);
		IntBuffer indexBuffer = CustomBufferUtils.createIntBuffer(indexArray);
		
		texturedModel = new Element_Model(GL_TRIANGLES);
		
		texturedModel.setVertexPositionData(posBuffer, 3, GL_STATIC_DRAW);
		
		texturedModel.setVertexTexturePositionData(texPosBuffer, 2, GL_STATIC_DRAW);
		
		texturedModel.setElementArrayData(indexBuffer);
		
		texture = new Texture2D(texturePath, 1, Texture.LINEAR, Texture.REPEAT);
		
	}
	
	
	public static Element_Model getTestModel() {
		return texturedModel;
	}
	
	
	public static Texture2D getTexture() {
		return texture;
	}

}
