package assets.meshes.instanced;

import assets.IDeletable;
import assets.meshes.Transformable;
import math.vectors.Vector3f;

public class Instance implements IDeletable {
	
	private final int ID;
	private Instancer instancer;
	private Transformable transform;
	
	public Instance(int id, Instancer instancer) {
		this.ID = id;
		this.instancer = instancer;
		this.transform = new Transformable();
	}
	
	public Instance(int id, Instancer instancer, Vector3f position, Vector3f rotation, Vector3f scale) {
		this.ID = id;
		this.instancer = instancer;
		this.transform = new Transformable(position, rotation, scale);
	}
	
	public Transformable getTransform() {
		instancer.addInstanceToUpdateQueue(ID);
		return transform;
	}
	
	public int getId() {
		return ID;
	}

	@Override
	public void delete() {
		instancer.deleteInstance(ID);
	}

}
