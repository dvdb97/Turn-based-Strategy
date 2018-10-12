package rendering.matrices.projectionMatrices;

import static math.Trigonometry.*;

import java.util.Arrays;

import interaction.Window;
import math.matrices.Matrix44f;
import math.vectors.Vector4f;

public class ProjectionMatrix extends Matrix44f {
	
	private float n, f, l, r, b, t;
	
	private Matrix44f inverse = null;
	
	
	private ProjectionMatrix(float n, float f, float l, float r, float b, float t) {
		this.n = n;
		this.f = f;
		this.l = l;
		this.r = r;
		this.b = b;
		this.t = t;
	}
	
	
	/**
	 * 
	 * @param n The camera's near plane.
	 * @param f The camera's far plane.
	 * @param l The camera's left plane.
	 * @param r The camera's right plane.
	 * @param b The camera's bottom plane.
	 * @param t The camera's top plane.
	 * @return Returns a perspective projection matrix.
	 */
	public static ProjectionMatrix perspective(float n, float f, float l, float r, float b, float t) {
		float a = Window.main.getProportions();
		
		l = l * a;
		r = r * a;
		
		ProjectionMatrix matrix = new ProjectionMatrix(n, f, l, r, b, t);
		
		float rl = r - l;
		float tb = t - b;
		float fn = f - n;
		
		matrix.setA1(2f * n / rl);
		matrix.setA3((r + l) / rl);
		matrix.setB2(2 * n / tb);
		matrix.setB3((t + b) / tb);
		//TODO: This will result in a 0 for default parameters. Our lu-decomposition can't handle this case.
		matrix.setC3(-(f + n + 0.000000001f) / fn);
		matrix.setC4((-2f * f * n) / fn );
		matrix.setD3(-1f);
		matrix.setD4(0f);
		
		return matrix;
	}
	
	
	/**
	 * 
	 * @return Returns a perspective projection matrix with 
	 * default values ranging from -1 to 1.
	 */
	public static ProjectionMatrix perspective() {
		return perspective(1f, -1f, -1f, 1f, -1f, 1f);
	}
	
	
	/**
	 * 
	 * @param fov The camera's vertical field of view.
	 * @param n The camera's near plane.
	 * @param f The camera's far plane.
	 * @return Returns a perspecive projection matrix.
	 */
	public static ProjectionMatrix perspective(float fov, float n, float f) {		
		float t = tan(fov / 2f) * n;
		
		//The window's aspect ratio.
		float a = Window.main.getProportions();
		
		return perspective(n, f, -t * a, t * a, -t, t);	
	}
	
	
	public static ProjectionMatrix orthographic() {
		return orthographic(1f, -1f, -1f, 1f, -1f, 1f);
	}
	
	
	/**
	 * 
	 * @param n The camera's near plane.
	 * @param f The camera's far plane.
	 * @param l The camera's left plane.
	 * @param r The camera's right plane.
	 * @param b The camera's bottom plane.
	 * @param t The camera's top plane.
	 * @return Returns a orthographic projection matrix.
	 */
	public static ProjectionMatrix orthographic(float n, float f, float l, float r, float b, float t) {
		ProjectionMatrix matrix = new ProjectionMatrix(n, f, l, r, b, t);
		
		matrix.setA1(2f / (r - l));
		matrix.setB2(2f / (t - b));
		matrix.setC3(-2f / (f -n));
		matrix.setD1(-(r + l) / (r - l));
		matrix.setD2(-(t + b) / (t - b));
		matrix.setD3(-(f + n) / (f - n));
		
		return matrix;
	}
	
	
	/**
	 * 
	 * @return Returns an array containing the frustrums corners in projected space.
	 */
	public Vector4f[] getFrustrumCorners() {		
		Vector4f[] corners = {
			//Near clipping plane
			new Vector4f(-1f, 1f, -1f, 1f),
			new Vector4f(1f, 1f, -1f, 1f),
			new Vector4f(-1f, -1f, -1f, 1f),
			new Vector4f(1f, -1f, -1f, 1f),
			
			//Far clipping plane
			new Vector4f(-1f, 1f, 1f, 1f),
			new Vector4f(1f, 1f, 1f, 1f),
			new Vector4f(-1f, -1f, 1f, 1f),
			new Vector4f(1f, -1f, 1f, 1f)
		};
		
		return corners;
	}
	
	
	/**
	 * 
	 * @return Returns the view frustrums center position in view space.
	 */
	public Vector4f getFrustrumCenter() {
		Vector4f[] corners = getFrustrumCorners();
		
		float x = 0f;
		float y = 0f; 
		float z = 0f;
		
		for (Vector4f corner : corners) {
			x += corner.getA();
			y += corner.getB();
			z += corner.getC();
		}
		
		x /= corners.length;
		y /= corners.length;
		z /= corners.length;
		
		return new Vector4f(x, y, z, 1f);
	}
	
	
	public float getAspectRatio() {
		return r / t;
	}
	
	
	public float getFieldOfView() {
		return 2f * atan(t / n); 
	}
	
	
	public float getNearPlane() {
		return n;
	}
	
	
	public float getFarPlane() {
		return f;
	}
	
	
	public float getTopPlane() {
		return t;
	}
	
	
	public float getBotPlane() {
		return b;
	}
	
	
	public float getLeftPlane() {
		return l;
	}
	
	
	public float getRightPlane() {
		return r;
	}


	@Override
	public Matrix44f inverse() {
		if (inverse == null)
			this.inverse = super.inverse();
		
		return inverse;
	}
	
}
