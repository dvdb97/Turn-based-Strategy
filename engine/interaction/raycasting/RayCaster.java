package interaction.raycasting;

import assets.cameras.Camera;
import math.vectors.Vector2f;
import math.vectors.Vector3f;
import math.vectors.Vector4f;

public class RayCaster {

	public static <E> E getHoveredObject(Vector2f cursorPosition, Camera camera, IObjectCollection<E> objectCollection) {
		Vector4f ray_clip = new Vector4f(cursorPosition.getA(), cursorPosition.getB(), -1f, 1f);
		
		//Eye space of the given camera
		Vector4f ray_eye = camera.getInvertedProjectionMatrix().times(ray_clip);
		ray_eye = new Vector4f(ray_eye.getA(), ray_eye.getB(), -1f, 0f);
		
		//World Space
		Vector3f ray_wor = camera.getInvertedViewMatrix().times(ray_eye).toVector3f().normalize();
		
		return objectCollection.getClosestObject(camera.getPosition(), ray_wor);
	}
	
	
	public static <E> E getHoveredObject(Camera camera, IObjectCollection<E> objectCollection) {
		return getHoveredObject(new Vector2f(0f, 0f), camera, objectCollection);
	}
	
}
