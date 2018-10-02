package testing;

import assets.models.Element_Model;
import assets.shaders.ShaderManager;
import assets.textures.Texture2D;
import graphics.matrices.Matrices;
import math.matrices.Matrix44f;
import rendering.RenderEngine;
import utils.CustomBufferUtils;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class TextureRenderer {
	
	private static final float[] coords = {
		0.0f, 1.0f, -1.0f,
		1.0f, 1.0f, -1.0f,
		0.0f, 0.0f, -1.0f,
		1.0f, 0.0f,	-1.0f
	};
	
	
	private static final float[] texCoords = {
		0.0f, 0.0f,
		1.0f, 0.0f,
		0.0f, 1.0f,
		1.0f, 1.0f,
	}; 
	
	
	private static final int[] indices = {
		1, 0, 3, 2	
	};
	
	
	private static Element_Model model;
	
	private static Texture2D testTexture;
	
	
	public static void init() {
		
		model = new Element_Model(GL_TRIANGLE_STRIP);
		
		model.setVertexPositionData(CustomBufferUtils.createFloatBuffer(coords), 3, GL_STATIC_DRAW);
		model.setVertexTexturePositionData(CustomBufferUtils.createFloatBuffer(texCoords), 2, GL_STATIC_DRAW);
		model.setElementArrayData(CustomBufferUtils.createIntBuffer(indices));
		
		testTexture = new Texture2D("res/fonts/Font.png");
		
	}
	
	
	public static void test() {
		render(testTexture);
	}
	
	
	public static void render(Texture2D texture) {
		ShaderManager.useTexturedMeshShaders(Matrices.getPerspectiveProjectionMatrix());
		
		RenderEngine.render(model, texture);
		
		ShaderManager.disableTexturedMeshShader();
	}

}
