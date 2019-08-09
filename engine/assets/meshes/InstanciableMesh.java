package assets.meshes;

import math.vectors.Vector3f;
import static org.lwjgl.opengl.GL31.glDrawElementsInstanced;
import java.nio.FloatBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import assets.buffers.Buffer;
import assets.buffers.VertexBuffer;

public abstract class InstanciableMesh extends Mesh {
	
	//The maximum number of instances of this mesh.
	private int numInstances;
	
	private HashMap<Integer, Transformable> instances;
	
	//A Set that keeps track of instances that have been updated since the last render call
	private HashSet<Integer> updatedInstances;
	
	/*
	 * A variable that stores the largest index that is currently used
	 * by an instance.
	 */
	private int pointer;
	
	private VertexBuffer matrixBuffer;
	
	public InstanciableMesh(int numInstances, int drawMode) {
		super(drawMode);
		this.numInstances = numInstances;
		this.updatedInstances = new HashSet<Integer>();
		this.instances = new HashMap<Integer, Transformable>();
		this.allocateMemory(numInstances);
	}
	
	
	/**
	 * 
	 * Creates an enmpty vertex buffer and uses it as the matrix buffer.
	 * 
	 * @param numInstances The number of instances that is supported by this InstancedMesh.
	 */
	private void allocateMemory(int numInstances) {
		matrixBuffer = new VertexBuffer();
		matrixBuffer.setBufferData(numInstances * 4 * 4 * 4, Buffer.DYNAMIC_DRAW);
		this.setMatrixVertexBuffer(matrixBuffer, 4, 4, 4);
	}
	
	
	/**
	 * 
	 * Utility function to put an array of elements into a FloatBuffer.
	 * 
	 * @param buffer The buffer to put the values in.
	 * @param index Specifies which instance should be changed.
	 * @param matrix The values of the matrix.
	 */
	private void setMatrixData(FloatBuffer buffer, int index, float[] matrix) {
		for (int i = 0; i < matrix.length; ++i) {
			buffer.put(index * 16 + i, matrix[i]);
		}
	}
	
	
	/**
	 * 
	 * Utility function to set the matrix of an instance to zero.
	 * 
	 * @param buffer The buffer to put the values in.
	 * @param index Specifies which instance should be changed.
	 */
	private void setZeroMatrix(FloatBuffer buffer, int index) {
		for (int i = 0; i < 16; ++i) {
			buffer.put(index * 16 + i, 0f);
		} 
	}
	
	
	/**
	 * Updates all instances that have been changed since the last render call.
	 * This method will be called every time this mesh is rendered to the screen.
	 */
	public void updateInstances() {
		if (!updatedInstances.isEmpty()) {
			try {
				FloatBuffer buffer = matrixBuffer.mapBuffer(Buffer.MAP_WRITE_ONLY).asFloatBuffer();
				
				for (int index : updatedInstances) {					
					if (instances.containsKey(index)) {
						float[] matrix = instances.get(index).getTransformationMatrix().toArray();
						
						setMatrixData(buffer, index, matrix);
					} else {
						setZeroMatrix(buffer, index);
					}
				}
				
				updatedInstances.clear();
			} finally {
				matrixBuffer.unmapBuffer();
			}
		}
	}
	
	
	/**
	 * 
	 * Add a new instance.
	 * 
	 * @param index The index that identifies the instance.
	 * @param position The position of the instance.
	 * @param rotation The rotation of the instance.
	 * @param scale The scale of the instance.
	 */
	public void addInstance(int index, Vector3f position, Vector3f rotation, Vector3f scale) {
		if (index >= numInstances) {
			System.err.println("Index exceeds the maximum of possible instances!");
			return;
		}
		
		if (instances.containsKey(index)) {
			System.err.println("The index " + index + "is already reserved!");
			return;
		}
		
		Transformable transformable = new Transformable(position
				, rotation, scale);
		
		instances.put(index, transformable);
		updatedInstances.add(index);
		
		if (index > pointer) {
			pointer = index;
		}
	}
	
	
	/**
	 * 
	 * Deletes the instance with the given index.
	 * 
	 * @param index The index that identifies the instance.
	 */
	public void deleteInstance(int index) {
		if (index >= numInstances) {
			System.err.println("Index exceeds the maximum of possible instances!");
			return;
		}		
		
		if (!instances.containsKey(index)) {
			System.err.println("The index " + index + "isn't used by any instance yet!");
			return;
		}
		
		instances.remove(index);
		updatedInstances.add(index);
		
		if (index == pointer) {
			pointer = Collections.max(instances.keySet());
		}
	}
	
	
	@Override
	public void render() {
		updateInstances();
		
		getVAO().enableVertexAttribArray();
		
		glDrawElementsInstanced(getDrawMode(), getIndexBuffer(), pointer + 1);
		
		getVAO().disableVertexAttribArray();
	}

}
