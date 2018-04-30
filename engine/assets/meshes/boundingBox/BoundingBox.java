package assets.meshes.boundingBox;

import java.util.ArrayList;
import java.util.LinkedList;

import assets.meshes.geometry.Vertex;
import math.matrices.Matrix44f;

/**
 * 
 * @author Dario
 *
 * A box around the cloud of vertices of this mesh.
 * Can be used for hit boxes and stuff like that.
 * 
 */
public class BoundingBox {	
	
	private float minX, maxX;
	
	private float minY, maxY;
	
	private float minZ, maxZ;
	
	
	public BoundingBox(LinkedList<Vertex> vertices) {
		computeBoundingBox(vertices);
	}
	
	
	public BoundingBox(ArrayList<Vertex> vertices) {
		computeBoundingBox(vertices);
	}
	
	
	private void computeBoundingBox(Iterable<Vertex> iterable) {
		minX = minY = minZ = Float.MAX_VALUE;
		maxX = maxY = maxZ = Float.MIN_VALUE;
		
		for (Vertex vertex : iterable) {
			addPoint(vertex);
		}
		
	}
	
	
	public void addPoint(Vertex vertex) {
		//X-Axis
		if (vertex.getA() < minX) { minX = vertex.getA(); }
		
		if (vertex.getA() > maxX) { maxX = vertex.getA(); }
		
		//Y-Axis
		if (vertex.getB() < minY) { minX = vertex.getB(); }
		
		if (vertex.getB() > maxY) { maxY = vertex.getB(); }
		
		//Z-Axis
		if (vertex.getC() < minZ) { minZ = vertex.getC(); }
		
		if (vertex.getC() > maxZ) { maxZ = vertex.getC(); }
	}
	
	
	public void removePoint(Vertex vertex) {
		//TODO
	}
	
	
	public void display(Matrix44f modelMatrix, Matrix44f viewMatrix, Matrix44f projectionMatrix) {
		
		BoundingBoxRenderer.render(modelMatrix, viewMatrix, projectionMatrix);
		
	}
	
}
