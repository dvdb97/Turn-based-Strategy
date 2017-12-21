package fontRendering.generators;

import assets.meshes.Mesh;
import assets.meshes.geometry.Color;
import assets.meshes.geometry.Vertex;

public class TextMeshGenerator {
	
	//TODO: Generate a mesh to map a font texture on
	
	public static Mesh generateStandardMesh(char[] text, float startX, float startY, float width, float height) {
		
		Color color = new Color(1f, 1f, 1f, 1f);
		
		float charWidth = width / text.length; 
		float charHeight = height;
		
		Vertex[] vertices = new Vertex[text.length * 4];
		int[] indexArray = new int[text.length * 6];
		
		int vertexIndex = 0;
		int indexArrayIndex = 0;
		for (int i = 0; i < text.length; ++i) {
			
			float xPos = startX + i * charWidth;
			
			vertices[vertexIndex++] = new Vertex(xPos, startY, 1f, color);
			vertices[vertexIndex++] = new Vertex(xPos + charWidth, startY, 1f, color);
			vertices[vertexIndex++] = new Vertex(xPos, startY - charHeight, 1f, color);
			vertices[vertexIndex++] = new Vertex(xPos + charWidth, startY - charHeight, 1f, color);
			
			int offset = i * 4;
			
			indexArray[indexArrayIndex++] = offset;
			indexArray[indexArrayIndex++] = offset + 1;
			indexArray[indexArrayIndex++] = offset + 2;
			
			indexArray[indexArrayIndex++] = offset + 1;
			indexArray[indexArrayIndex++] = offset + 2;
			indexArray[indexArrayIndex++] = offset + 3;
			
		}
		
		
		return new Mesh(vertices, indexArray);
		
	}	

}
