package assets.meshes.geometry;

import math.vectors.Vector3f;

public class Triangle {
	
	private VertexLegacy[] vertices = new VertexLegacy[3];
	
	public Triangle(VertexLegacy v0, VertexLegacy v1, VertexLegacy v2) {
		vertices[0] = v0;
		vertices[1] = v1;
		vertices[2] = v2;
		
		generateNormalData();
		
	}
	
	
	private void generateNormalData() {
		
		Vector3f normal = getSurfaceNormal();
		
		for (VertexLegacy vertex : vertices) {
			vertex.addSurfaceNormal(normal);
		}
		
	}
	
	private Vector3f getSurfaceNormal() {
		
		Vector3f v0 = vertices[1].minus(vertices[0]);
		
		Vector3f v1 = vertices[2].minus(vertices[0]);
		
		return v0.cross(v1);		
	}
	
	
	public VertexLegacy getVertex(int index) {
		return vertices[index];
	}
	

}
