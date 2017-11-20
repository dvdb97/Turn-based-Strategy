package rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import assets.models.Element_Model;

public class GUI_Model extends Element_Model {
	

	public GUI_Model() {
		super(GL_TRIANGLES);
	}

	
	public void setElementArrayData(IntBuffer indexBuffer) {
		super.setElementArrayData(indexBuffer);
	}

	
	public void setPositionData(FloatBuffer positionDataBuffer) {
		setVertexPositionData(positionDataBuffer, 0, 2, GL_STATIC_DRAW);
	}


	public void setTexturePositionData(FloatBuffer texturePositionDataBuffer) {
		setTexturePositionData(texturePositionDataBuffer, 1, 2, GL_STATIC_DRAW);
	}

}
