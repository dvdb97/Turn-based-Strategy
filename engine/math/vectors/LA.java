package math.vectors;

//this is a light weight linear algebra library

public class LA {
	
	//------------------ math methods ----------------------
	public static float LANorm(Vector3f v) {
		return (float)Math.sqrt(v.getA()*v.getA() + v.getB()*v.getB() + v.getC()*v.getC());
	}
	
	
	public static float LANorm(Vector4f v) {
		return (float)Math.sqrt(v.getA()*v.getA() + v.getB()*v.getB() + v.getC()*v.getC() + v.getD()*v.getD());
	}
	
	//TODO: determinant
	
	//calculus
	//make sure v.length == w.length
	public static float scalarProduct(float[] v, float[] w) {
		float result = 0.0f;
		for (int i=0; i<v.length; i++) {
				result += v[i]*w[i];
		}
		
		return result;
	}
	
	
	public static float[] sum(float[] v, float[] w) {
		float[] result = new float[v.length];
		for (int i=0; i<v.length; i++) {
				result[i] = v[i] + w[i];
		}
		
		return result;
	}
	
	
	public static float[] scalarMult(float[] v, float r) {
		float[] result = new float[v.length];
		for (int i=0; i<v.length; i++) {
				result[i] = v[i]*r;
		}
		
		return result;
	}
	
	
	
}
