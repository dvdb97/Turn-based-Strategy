package rendering.shapes;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public abstract class GUIShape {
	
	public abstract FloatBuffer getPositionData();
	
	public abstract FloatBuffer getTexPosData();
	
	public abstract IntBuffer getIndexData();
	
	public abstract boolean isHit(float cursorX, float cursorY);

}
