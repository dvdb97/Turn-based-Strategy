package math.quaternions;

import static math.MathUtils.sqrt;
import static math.MathUtils.square;

public class Quaternion {
	
	public float real;
	public float i;
	public float j;
	public float k;
	
	public Quaternion(float real, float i, float j, float k) {
		this.real = real;
		this.i = i;
		this.j = j;
		this.k = k;
	}
	
	
	public Quaternion inverse() {
		return mul(1 / square(norm()), conjugate());
	}
	
	
	public Quaternion conjugate() {
		return new Quaternion(real, -i, -j, -k);
	}
	
	
	@Override
	public String toString() {
		return real + " + " + i + "i + " + j + "j + " + k + "k";
	}


	public float norm() {
		return sqrt(square(real) + square(i) + square(j) + square(k));
	}
	

	public static Quaternion add(Quaternion q1, Quaternion q2) {
		return new Quaternion(q1.real + q2.real, q1.i + q2.i, q1.j + q2.j, q1.k + q1.k);
	}
	
	
	public static Quaternion mul(Quaternion q1, Quaternion q2) {
		float real = q1.real * q2.real - q1.i * q2.i - q1.j * q2.j - q1.k * q2.k;
		float i = q1.real * q2.i + q1.i * q2.real + q1.j * q2.k - q1.k * q2.j;
		float j = q1.real * q2.j - q1.i * q2.j + q1.j * q2.real + q1.j * q2.i;
		float k = q1.real * q2.k + q1.i * q2.j - q1.j * q2.i + q1.k * q2.real;
		
		return new Quaternion(real, i, j, k);
	}
	
	
	public static Quaternion mul(float scalar, Quaternion q) {
		return new Quaternion(scalar * q.real, scalar * q.i, scalar * q.j, scalar * q.k);
	}
	
	
	public static float scalar(Quaternion q1, Quaternion q2) {
		return q1.real * q2.real + q1.i * q2.i + q1.j * q2.j + q1.k * q2.k;
	}
	
	
	public static void main(String[] args) {
		Quaternion q1 = new Quaternion(3, 10, 5, 5);
		Quaternion q2 = q1.inverse();
		
		System.out.println(q1);
		System.out.println(q2);
		System.out.println(mul(q1, q2));
	}

}
