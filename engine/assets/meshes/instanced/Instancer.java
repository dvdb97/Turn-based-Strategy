package assets.meshes.instanced;

import static org.lwjgl.opengl.GL31.glDrawElementsInstanced;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.HashSet;

import assets.buffers.Buffer;
import assets.buffers.VertexBuffer;
import assets.meshes.Mesh;
import math.vectors.Vector3f;


class InstanceLimitExceededException extends RuntimeException {}

class IdOutOfBoundsException extends RuntimeException {}

class IdNotReservedException extends RuntimeException {}


public abstract class Instancer extends Mesh {
	
	private VertexBuffer matrixBuffer;
	
	private int numInstances;	
	private HashMap<Integer, Instance> instances;
	
	//Keeps track of all instances that have been updated recently.
	private HashSet<Integer> updated;
	
	//The instances that have been removed will be stored in this set.
	private HashSet<Integer> unreserved;
	
	// A variable that stores the largest index that is currently used by an instance.
	private int pointer = -1;
	
	
	public Instancer(int numInstances, int drawMode) {
		super(drawMode);
		this.numInstances = numInstances;
		this.instances = new HashMap<Integer, Instance>();
		this.unreserved = new HashSet<Integer>();
		this.updated = new HashSet<Integer>();
		this.allocateMemory(numInstances);
	}
	
	/**
	 * 
	 * Creates an empty vertex buffer and uses it as the matrix buffer.
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
	 * @param id The id of the instance.
	 * @return Returns true if the given id is already reserved for another Instance.
	 */
	public boolean isReserved(int id) {
		return id <= pointer && !unreserved.contains(id);
	}
	
	
	/**
	 * 
	 * @return Returns the number of instances instantiated by this Instancer.
	 */
	public int reserved() {
		return pointer + 1 + unreserved.size();
	}
	
	
	/**
	 * 
	 * @return Returns the number of ids that haven't been reserved by an Instance yet.
	 */
	public int unreserved() {
		return numInstances - reserved();
	}
	
	
	/**
	 * 
	 * @return Returns true if it is impossible to allocate a new id without exceeding the
	 * maximum amount of instances.
	 */
	public boolean isIdAvailable() {
		return unreserved() != 0;
	}
	
	
	/**
	 * 
	 * Reserve an id for a new instance.
	 * 
	 * @return Returns the reserved id.
	 */
	private int allocateInstanceId() {
		if (!unreserved.isEmpty()) {
			int id = unreserved.iterator().next();
			unreserved.remove(id);
			return id;
		}
		
		return ++pointer;		
	}
	
	
	/**
	 * 
	 * Create a new instance. Throws an InstanceLimitExceededException when exceeding
	 * the maximum number of Instances this Instancer can create.
	 * 
	 * @return Returns the new instance. 
	 */
	public Instance addInstance() {
		if (isIdAvailable()) {
			int id = allocateInstanceId();
			
			Instance instance = new Instance(id, this);
			instances.put(id, instance);
			updated.add(id);
			
			return instance;
		} else {
			throw new InstanceLimitExceededException();
		}
	}
	
	
	/**
	 * 
	 * Create a new instance. Throws an InstanceLimitExceededException when exceeding
	 * the maximum number of Instances this Instancer can create.
	 * 
	 * @param position The position of the new instance.
	 * @param rotation The rotation of the new instance.
	 * @param scale The scale of the new instance.
	 * @return Returns the new instance.
	 */
	public Instance addInstance(Vector3f position, Vector3f rotation, Vector3f scale) {	
		if (isIdAvailable()) {
			int id = allocateInstanceId();
			
			Instance instance = new Instance(id, this);
			instance.getTransform().setTranslation(position);
			instance.getTransform().setRotation(rotation);
			instance.getTransform().setScaling(scale);
			
			instances.put(id, instance);
			
			return instance;
		} else {
			throw new InstanceLimitExceededException();
		}
	}
	
	
	/**
	 * 
	 * Deletes an Instance created by this Instancer.
	 * 
	 * @param id The id that identifies the Instance.
	 */
	public void deleteInstance(int id) {
		if (!isReserved(id)) {
			throw new IdNotReservedException();
		}
		
		instances.remove(id);
		updated.add(id);
	}
	
	
	/**
	 * 
	 * Deletes an Instance created by this Instancer.
	 * 
	 * @param instance The Instance to delete.
	 */
	public void deleteInstance(Instance instance) {
		this.deleteInstance(instance.getId());
	}
	
	
	
	/**
	 * 
	 * Adds an Instance to the set of Instances that will
	 * be updated next render call.
	 * 
	 * @param id The id that identifies the Instance.
	 */
	public void addInstanceToUpdateQueue(int id) {
		if (id >= numInstances) {
			throw new IdOutOfBoundsException();
		}
		
		if (!isReserved(id)) {
			throw new IdNotReservedException();
		}
		
		updated.add(id);
	}
	
	
	/**
	 * 
	 * Adds an Instance to the set of Instances that will
	 * be updated next render call.
	 * 
	 * @param instance The Instance to update.
	 */
	public void addInstanceToUpdateQueue(Instance instance) {
		this.addInstanceToUpdateQueue(instance.getId());
	}
	
	
	/**
	 * 
	 * @param id An id that identifies an Instance created by this Instancer.
	 * @return Returns the Instance with the given id.
	 */
	public Instance getInstanceById(int id) {
		if (id >= numInstances) {
			throw new IdOutOfBoundsException();
		}
		
		if (id > pointer || unreserved.contains(id)) {
			throw new IdNotReservedException();
		}
		
		return instances.get(id);		
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
		if (!updated.isEmpty()) {
			try {
				FloatBuffer buffer = matrixBuffer.mapBuffer(Buffer.MAP_WRITE_ONLY).asFloatBuffer();
				
				for (int index : updated) {					
					if (instances.containsKey(index)) {
						float[] matrix = instances.get(index).getTransform().getTransformationMatrix().toArray();
						
						setMatrixData(buffer, index, matrix);
					} else {
						setZeroMatrix(buffer, index);
					}
				}
				
				updated.clear();
			} finally {
				matrixBuffer.unmapBuffer();
			}
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
