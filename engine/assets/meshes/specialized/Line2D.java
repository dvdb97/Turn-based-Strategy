package assets.meshes.specialized;

import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.meshes.Mesh2D;
import math.MathUtils;
import math.Trigonometry;
import math.vectors.Vector2f;
import utils.CustomBufferUtils;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glLineWidth;

import org.lwjgl.system.MathUtil;

public class Line2D extends Mesh2D {
	
	private float width = 1f;
	
	public Line2D() {
		super(GL_LINES);
		
		float[] pos = {
			0f, 0f, 0f, 
			1f, 0f, 0f	
		};
		
		int[] indices = {
			0, 1	
		};
		
		setPositionData(CustomBufferUtils.createFloatBuffer(pos));
		setIndexBuffer(CustomBufferUtils.createIntBuffer(indices));
	}
	
	
	public Line2D(float width) {
		this();
		setLineWidth(width);
	}
	
	
	public Line2D(Vector2f p1, Vector2f p2) {
		this();
		setPoints(p1, p2);
	}
	
	
	public Line2D(Vector2f p1, Vector2f p2, float width) {
		this(width);
		setPoints(p1, p2);
	}
	
	
	/**
	 * 
	 * Transform the line to make sure the end points of the line
	 * match the points p1 and p2.
	 * 
	 * @param p1 The left point
	 * @param p2 The right point
	 */
	public void setPoints(Vector2f p1, Vector2f p2) {
		float dx = p2.getA() - p1.getA();
		float dy = p2.getB() - p1.getB();
		float angle = (float)Math.atan(dy / dx);
		
		if (dx < 0f) {
			angle -= Trigonometry.PI;
		}
		
		float scale = MathUtils.sqrt(dx * dx + dy * dy);
		
		transformable.setTranslation(p1.getA(), p1.getB(), 0f);
		transformable.setRotation(0f, 0f, angle);
		transformable.setScaling(scale);
	}
	
	
	public void setLineWidth(float width) {
		this.width = width;
	}


	@Override
	protected void onDrawStart(Camera camera, DirectionalLight light) {
		glLineWidth(width);
		super.onDrawStart(camera, light);
	}

}
