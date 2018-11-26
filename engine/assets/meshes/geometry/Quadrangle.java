package assets.meshes.geometry;


public class Quadrangle {
	
	public static final int NUMBER_OF_VERTICES = 6;
	
	private VertexLegacy[] vertices = new VertexLegacy[NUMBER_OF_VERTICES];
	
	//------------------------------ constructor ------------------------------
	public Quadrangle(VertexLegacy upperLeft, VertexLegacy upperRight, VertexLegacy lowerLeft, VertexLegacy lowerRight) {
		
		/*
		 * 0, 1, 2,
		 * 0, 3, 2
		 */
		
		VertexLegacy[] temp = {
			upperLeft, upperRight, lowerRight,
			upperLeft, lowerLeft, lowerRight
		};
		
		
		this.vertices = temp;
	}
	
	
	public Quadrangle(Triangle triangle0, Triangle triangle1) {
		
		vertices[0] = triangle0.getVertex(0);
		vertices[1] = triangle0.getVertex(1);
		vertices[2] = triangle0.getVertex(2);
		
		
		vertices[3] = triangle1.getVertex(0);
		vertices[4] = triangle1.getVertex(1);
		vertices[5] = triangle1.getVertex(2);
		
	}
	
	//------------------------------- get & set ---------------------------
	
	public VertexLegacy[] getVertices() {
		return vertices;
	}
	
	
	public float[] getPosData() {
		float[] data = new float[vertices.length * 3];
		
		int index = 0;
		for (VertexLegacy vertex : vertices) {
			data[index++] = vertex.getA();
			data[index++] = vertex.getB();
			data[index++] = vertex.getC();
			
		}
		
		return data;
	}
	
	
	public float[] getColData() {
		float[] data = new float[vertices.length * 4];
		
		int index = 0;
		for (VertexLegacy vertex : vertices) {
			data[index++] = vertex.getRed();
			data[index++] = vertex.getGreen();
			data[index++] = vertex.getBlue();
			data[index++] = vertex.getAlpha();
			
		}
		
		return data;
		
	}
	
	
	public int[] getIndexData() {
		int[] indexData = {
			0, 1, 2,
			0, 3, 2	
		};
		
		return indexData;
	}
	
	
	public void printData() {
		for (VertexLegacy vertex : vertices) {
			vertex.print();
		}
	}

}
