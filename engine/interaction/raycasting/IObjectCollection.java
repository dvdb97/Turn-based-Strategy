package interaction.raycasting;

import math.vectors.Vector3f;

public interface IObjectCollection<E> {

	public abstract E getClosestObject(Vector3f cameraPosition, Vector3f rayDirection);
	
}
