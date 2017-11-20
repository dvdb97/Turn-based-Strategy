package assets.models;

import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL30.*;

import java.nio.IntBuffer;

import assets.models.abstractModels.Model;


/*
 * This model is drawn by using the command glDrawElements()
 */
public class Element_Model extends Model {	
	
	
	private IntBuffer elementBuffer;
	
	
	public Element_Model(int drawMode) {
		super(drawMode);
	}
	
	
	//****************************** Data Management ******************************
	
	public void setElementArrayData(IntBuffer indexBuffer) {
		elementBuffer = indexBuffer;
	}
	
	
	//****************************** Getters & Setters ******************************
	
	
	public IntBuffer getElementBuffer() {
		return elementBuffer;
	}	
		
	
	//****************************** Additional functions ******************************
	
	
	public void delete() {
		super.delete();
		
		glDeleteVertexArrays(getVaoID());
		
		System.out.println(this.toString() + " was deleted!");
	}

	
	@Override
	public void onDrawStart() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void onDrawStop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		glDrawElements(getDrawMode(), getElementBuffer());		
	}
}