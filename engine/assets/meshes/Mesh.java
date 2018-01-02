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
	
	
	public void print() {
		
		for (Vertex vert : vertices) {
			System.out.println("Position:");
			vert.print();
			
			if (vert.isTextured()) {
				System.out.println("Texture:");
				vert.getTexturePositions().print();
			}
			
		}
		
	}

}
