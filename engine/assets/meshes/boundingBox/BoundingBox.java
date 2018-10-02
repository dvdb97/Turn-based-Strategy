package assets.meshes.boundingBox;

import java.util.List;

import javax.xml.crypto.dsig.Transform;

import assets.meshes.Transformable;
import assets.meshes.geometry.Vertex;
import assets.shaders.ShaderLoader;
import assets.shaders.ShaderProgram;
import math.matrices.Matrix44f;
import math.vectors.Vector3f;

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
	
	private Vector3f centerPoint;
	
	private Transformable transform;
	
	
	private static BoundingBoxMesh mesh = null;
	
	private static ShaderProgram bbShader = null;
	
	
	/**
	 * 
	 * Create a bounding box around this point cloud
	 * 
	 * @param vertices The vertices of the mesh
	 */
	public BoundingBox(List<Vertex> vertices) {
		computeBoundingBox(vertices);
	}
	
	
	/**
	 * 
	 * Create a bounding box around this point cloud
	 * 
	 * @param vertices The vertices of the mesh
	 * @param targetList A reference to a list. Appends all vertices of the array to this list. 
	 * Can come in handy as the class "Mesh" stores vertices in lists.
	 */
	public BoundingBox(Vertex[] vertices, List<Vertex> targetList) {
		computeBoundingBox(vertices, targetList);
	}
	
	
	private void computeBoundingBox(Iterable<Vertex> iterable) {
		minX = minY = minZ = Float.MAX_VALUE;
		maxX = maxY = maxZ = Float.MIN_VALUE;
		
		for (Vertex vertex : iterable) {
			addPoint(vertex);
		}
		
		centerPoint = new Vector3f((minX + maxX) / 2f, (minY + maxY) / 2f, (minZ + maxZ) / 2f);
		
		transform = new Transformable();
		
		System.out.println("X: " + minX + " to " + maxX);
		System.out.println("Y: " + minY + " to " + maxY);
		System.out.println("Z: " + minZ + " to " + maxZ);
		System.out.println("Center point: " + centerPoint);
		
		if (mesh == null) {
			mesh = new BoundingBoxMesh();
		}
		
		if (bbShader == null) {
			bbShader = ShaderLoader.loadShader("Shaders/StandardShaders/BBShader.vert", 
					   						   "Shaders/StandardShaders/BBShader.frag");	
		}		
	}
	
	
	private void computeBoundingBox(Vertex[] vertices, List<Vertex> targetList) {
		minX = minY = minZ = Float.MAX_VALUE;
		maxX = maxY = maxZ = Float.MIN_VALUE;
		
		for (Vertex vertex : vertices) {
			addPoint(vertex);
			
			targetList.add(vertex);
		}
		
		centerPoint = new Vector3f((minX + maxX) / 2f, (minY + maxY) / 2f, (minZ + maxZ) / 2f);
		
		System.out.println("X: " + minX + " to " + maxX);
		System.out.println("Y: " + minY + " to " + maxY);
		System.out.println("Z: " + minZ + " to " + maxZ);
		System.out.println("Center point: " + centerPoint.toString());
		
		if (mesh == null) {
			mesh = new BoundingBoxMesh();
		}
		
		if (bbShader == null) {
			bbShader = ShaderLoader.loadShader("Shaders/StandardShaders/BBShader.vert", 
					   						   "Shaders/StandardShaders/BBShader.frag");	
		}
	}
	
	
	/**
	 * 
	 * Add a new vertex to the bounding box
	 * 
	 * @param vertex The new vertex 
	 */
	public void addPoint(Vertex vertex) {
		//X-Axis
		if (vertex.getXPos() < minX) { minX = vertex.getXPos(); }
		
		if (vertex.getXPos() > maxX) { maxX = vertex.getXPos(); }
		
		//Y-Axis
		if (vertex.getYPos() < minY) { minY = vertex.getYPos(); }
		
		if (vertex.getYPos() > maxY) { maxY = vertex.getYPos(); }
		
		//Z-Axis
		if (vertex.getZPos() < minZ) { minZ = vertex.getZPos(); }
		
		if (vertex.getZPos() > maxZ) { maxZ = vertex.getZPos(); }
	}
	
	
	/**
	 * 
	 * Remove an existing vertex from the bounding box. If the vertex
	 * was one of the edge vertices of the bounding box, the bounding box
	 * will be recomputed
	 * 
	 * @param vertices A reference to the vertices of the mesh.
	 * @param vertex The vertex that has to be removed
	 */
	public void removePoint(List<Vertex> vertices, Vertex vertex) {
		
		if (vertex.getXPos() == minX || vertex.getXPos() == maxX) {
			computeBoundingBox(vertices);
			
			return;
		}
		
		
		if (vertex.getYPos() == minY || vertex.getYPos() == maxY) {
			computeBoundingBox(vertices);
			
			return;
		}
		
		
		if (vertex.getZPos() == minZ || vertex.getZPos() == maxZ) {
			computeBoundingBox(vertices);
			
			return;
		}
		
	}
	
	
	public Vector3f[] getIntersections(/* TODO */) {
		return null;
	}
	
	
	public void render(Transformable meshTransform, Matrix44f view, Matrix44f projection) {
		transform.setTranslation(centerPoint.plus(meshTransform.getTranslation()));
		transform.setRotation(meshTransform.getRotation());
		Vector3f s = meshTransform.getScaling();
		transform.setScaling(new Vector3f(s.getA() * (maxX - minX) / 2f, s.getB() * (maxY - minY) / 2f, s.getC() * (maxZ - minZ) / 2f));
		
		bbShader.use();		
		bbShader.setMVPMatrix(transform.getTransformationMatrix(), view, projection);
		
		mesh.render();
		
		bbShader.disable();
	}
	
}
