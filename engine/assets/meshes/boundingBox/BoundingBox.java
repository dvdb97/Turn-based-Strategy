package assets.meshes.boundingBox;

import java.util.ArrayList;
import java.util.LinkedList;

import assets.meshes.geometry.Vertex;
import assets.meshes.geometry.VertexLegacy;
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
		if (vertex.getXPos() < minX) { minX = vertex.getXPos(); }
		
		if (vertex.getXPos() > maxX) { maxX = vertex.getXPos(); }
		
		//Y-Axis
		if (vertex.getYPos() < minY) { minX = vertex.getYPos(); }
		
		if (vertex.getYPos() > maxY) { maxY = vertex.getYPos(); }
		
		//Z-Axis
		if (vertex.getZPos() < minZ) { minZ = vertex.getZPos(); }
		
		if (vertex.getZPos() > maxZ) { maxZ = vertex.getZPos(); }
	}
	
	
	public void removePoint(Vertex vertex) {
		//TODO
	}
	
	
	public void display(Matrix44f modelMatrix, Matrix44f viewMatrix, Matrix44f projectionMatrix) {
		
		BoundingBoxRenderer.render(modelMatrix, viewMatrix, projectionMatrix);
		
	}
	
}
