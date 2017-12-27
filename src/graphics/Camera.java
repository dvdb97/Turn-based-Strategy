package graphics;

import math.vectors.Vector3f;
import static utils.Const.HALF_PI;


//TODO: it has to made sure that the world rotates around the camera position
//			that creates the impression the camera would rotate
//TODO: now it is static, but maybe just temporary
public class Camera {
	
	private static Vector3f position = new Vector3f(0, 0, 1f);
	//in radian measure
	private static float    pitch = 0.0f;		//up and down
	private static float    yaw   = 0.0f;		//right left
	private static float    roll  = 0.0f;		//tilt
	
	private static float zoom = 1.0f;
	
	//needed for implementation of boundary conditions (b.c.)
	private static float temp;
	
	//--------------------- basic moves ----------------------------------
	
	
	public static void move(float dx, float dy, float dz) {
		
		position = position.plus( new Vector3f(dx, dy, dz) );
	}
	
	
	public static void incrZoom(float dZoom) {
		temp = zoom + dZoom;
		
		//check bc
		if (temp > 0 && temp < 1) {
			zoom = temp;
		}
	}
	
	
	//TODO: boundary conditions (bc)
	public static void incrPitch(float dPitch) {
		temp = pitch + dPitch;
		
		//check bc
		if (temp > 0 && temp < HALF_PI) {
			pitch = temp;
		}
	}
	
	
	public static void incrYaw(float dYaw) {
		temp = yaw + dYaw;
		
		//check bc
		if (temp > -HALF_PI && temp < HALF_PI) {
			yaw = temp;
		}
	}
	
	
	public static void incrRoll(float dRoll) {
		temp = roll + dRoll;
		
		//check bc
		if (temp > -HALF_PI && temp < HALF_PI) {
			roll = temp;
		}
	}
	
	
	
	
	//------------------------ get ----------------------
	public static Vector3f getPosition() {
		
		return position.copyOf();
	}
	
	
	public static float getPitch() {
		
		return pitch;
	}
	
	
	public static float getYaw() {
		
		return yaw;
	}
	
	
	public static float getRoll() {
		
		return roll;
	}	
	
	
	public static float getZoom() {
		
		return zoom;
	}
}
