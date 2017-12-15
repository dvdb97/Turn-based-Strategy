package fontRendering.generators;

import assets.meshes.geometry.Vertex;
import fontRendering.texture.FontTexture;

public class TextGenerator {
	
	public static void init() {
		
	}
	
	
	public static Vertex[] generateText(char[] text, float x, float y, float width, float height, FontTexture font) {
		
		//Generate text.length * 4 vertices to have a mesh for the method below.
		
		return null;
		
	}
	
	
	public static void generateText(String text, Vertex[] vertices, FontTexture font) {
		
		generateText(text.toCharArray(), vertices, font);
		
	}
	
	
	public static void generateText(char[] text, Vertex[] vertices, FontTexture font) {
		
		if(vertices.length / 4 != text.length) {
			System.err.println("The mesh isn't big enough to map the text on it!");
			
			//TODO: Maybe generate a mesh to render the text properly
			
		}
		
		
		for (int letter = 0; letter < vertices.length; ++letter) {
			
			int index = letter * 4; 
			
			font.mapToQuad(text[letter], vertices[index++], vertices[index++], vertices[index++], vertices[index]);
			
		}
		
	}

}
