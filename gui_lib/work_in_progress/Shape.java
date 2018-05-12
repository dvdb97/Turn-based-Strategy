package work_in_progress;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

import assets.models.Element_Model;

public abstract class Shape extends Element_Model {
	
	public Shape() {
		super(GL_TRIANGLES);
	}

	public abstract boolean isHit();
	
}
