package gui.test;

import assets.meshes.Mesh;
import fontRendering.generators.TextGenerator;
import fontRendering.generators.TextMeshGenerator;
import fontRendering.rendering.TextModel;
import graphics.shaders.ShaderManager;
import gui.font.FontCollection;
import math.matrices.Matrix44f;
import rendering.RenderEngine;

public class HelloWorld {
	
	private static TextModel model;
	
	private static Matrix44f mvpMatrix;
	
	
	public static void init() {
		
		char[] s = {
			'H', 'e', 'l', 'l', 'o', 'W', 'o', 'r', 'l', 'd'
		};
		
		model = new TextModel(FontCollection.getTimesNewRoman());
		
		Mesh textMesh = TextMeshGenerator.generateStandardMesh(s, 0.0f, 0.2f, 1.0f, 0.2f);
		
		TextGenerator.generateText(s, textMesh.getVertexArray(), FontCollection.getTimesNewRoman());
		
		model.setData(textMesh.getVertexArray(), textMesh.getIndexArray());
		
		mvpMatrix = new Matrix44f(1f, 0f, 0f, 0f,
								  0f, 1f, 0f, 0f,
								  0f, 0f, 1f, 0f,
								  0f, 0f, 0f, 1f);
		
	}
	
	
	public static void render() {
		
		ShaderManager.useFontShader(mvpMatrix);
		
		RenderEngine.draw(model, FontCollection.getTimesNewRoman());
		
		ShaderManager.disableFontShader();
		
	}

}
