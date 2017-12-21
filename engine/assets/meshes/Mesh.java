package assets.meshes;

import assets.meshes.geometry.Vertex;

public class Mesh {
	
	private Vertex[] vertices;
	
	private int[] indexArray;
	
	
	public Mesh(Vertex[] vertices, int[] indexArray) {
		this.vertices = vertices;
		
		this.indexArray = indexArray;		
	}
	
	
	public Vertex[] getVertexArray() {
		return vertices;
	}
	
	
	public int[] getIndexArray() {
		return indexArray;
	}

}
