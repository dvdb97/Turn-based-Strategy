package assets.meshes.standardMeshes;

import java.util.LinkedList;

import assets.meshes.Mesh;
import assets.meshes.geometry.VertexLegacy;

/**
 * 
 * @author Dario
 *
 * Creates a mesh to render a simple cube
 *
 */
public class Cube extends Mesh {

	public static Cube generate(float width, float height, float depth) {
		
		LinkedList<VertexLegacy> vertices = new LinkedList<VertexLegacy>();
		LinkedList<Integer> indices = new LinkedList<Integer>();
		
		
		for (int i = 0; i < 8; i++) {
			
			/*
			 * left vertices = {0, 2, 4, 6}  --> even vertices
			 * right vertices = {1, 3, 5, 7} --> odd vertices
			 * 
			 * near vertices = {0, 1, 2, 3}  --> i / 4 = 0
			 * far vertices = {4, 5, 6, 7}   --> i / 4 = 1
			 * 
			 * bot vertices = {2, 3, 6, 7}   --> i % 4 = {2, 3}
			 * top vertices = {0, 1, 4, 5}   --> i % 4 = {0, 1}
			 * 
			 */
			float x = (i % 2) * width;
			float y = (i / 4) * height;
			float z = ((i - (i % 2)) % 4 + 1) * depth;
			
			vertices.add(new VertexLegacy(x, y, z));
			
		}	
		
		
		int[] i = {0, 1, 2, 0, 2, 3,
				   1, 5, 7, 1, 3, 7,
				   5, 4, 6, 5, 7, 6,
				   4, 0, 2, 4, 6, 2,
				   4, 5, 1, 4, 0, 1,
				   2, 3, 7, 2, 6, 7};
		
		return new Cube(vertices, i);
		
	}
	
	
	private Cube(LinkedList<VertexLegacy> vertices, LinkedList<Integer> indices) {
		super(vertices, indices);
	}
	
	
	private Cube(LinkedList<VertexLegacy> vertices, int[] indices) {
		super(vertices, indices);
	}
	
}
