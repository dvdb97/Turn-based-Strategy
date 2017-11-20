package elements;

import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;
import static org.lwjgl.opengl.GL11.*;

import assets.textures.Texture2D;
import rendering.GUI_Model;
import rendering.RenderEngine;
import rendering.shapes.Shape;

public abstract class Element {
	
	
	private GUI_Model model;
	private boolean needsUpdate;
	

	private float xPos;
	private float yPos;
	
	private float width;
	private float height;
	
	private Shape shape;
	private Texture2D texture;
	
	
	public Element(Shape shape, Texture2D texture, float xPos, float yPos, float width, float height) {
		this(texture, xPos, yPos, width, height);
		
		this.setShape(shape);
	}
	
	
	public Element(Texture2D texture, float xPos, float yPos, float width, float height) {
		
		this.texture = texture;
		
		this.xPos = xPos;
		this.yPos = yPos;
		
		this.width = width;
		this.height = height;
		
	}
	
	
	public Element(String texturePath, float xPos, float yPos, float width, float height) {
		
		Texture2D texture = new Texture2D(texturePath, 1, GL_LINEAR_MIPMAP_LINEAR, GL_CLAMP_TO_BORDER);
		this.texture = texture;
		
		this.xPos = xPos;
		this.yPos = yPos;
		
		this.width = width;
		this.height = height;
	}
	
	
	//Assumes that the GUI_Shader is already activated (Will be done in GUI)
	public void draw() {
		RenderEngine.draw(model, texture);
	}
	
	
	public abstract void display();
	
	
	//Loads all the geometry of this element and puts it into a model
	private void loadModel() {
		
		model = new GUI_Model();
		
		model.setPositionData(shape.getPositionData(xPos, yPos, width, height));
		
		model.setTexturePositionData(shape.getTexturePositionData());
		
		model.setElementArrayData(shape.getElementArray(0));
		
		needsUpdate = false;
	}
	
	
	//Updates the model to match the current size of the model
	public void updateModel() {
		if (!needsUpdate) {
			return;
		}
		
		this.model.setPositionData(shape.getPositionData(xPos, xPos, width, height));
		
		needsUpdate = false;
		
	}
	
	
	public abstract void move(float x, float y);
	
	
	public abstract void resize(float x, float y);
	
	
	public abstract void resize(float factor);
	
	
	public boolean isCursorOnElement(float CursorX, float CursorY) {
		
		if (CursorX > xPos + width || CursorX < xPos) {
			return false;
		}
		
		if (CursorY > yPos + width || CursorY < yPos) {
			return false;
		}
		
		return true;
		
	}
	
	
	//****************************** Get & Set ******************************
	

	public float getWidth() {
		// TODO Auto-generated method stub
		return width;
	}


	public float getHeight() {
		// TODO Auto-generated method stub
		return height;
	}


	public float getXPos() {
		// TODO Auto-generated method stub
		return xPos;
	}


	public float getYPos() {
		// TODO Auto-generated method stub
		return yPos;
	}
	
	
	protected void setXPos(float xPos) {
		this.xPos = xPos;
	}
	
	
	protected void setYPos(float yPos) {
		this.yPos = yPos;
	}
	
	
	protected void setPosition(float x, float y) {
		this.xPos = x;
		this.yPos = y;
	}
	
	
	protected void setWidth(float width) {
		this.width = width;
	}
	
	
	protected void setHeight(float height) {
		this.height = height;
	}
	
	
	public void setDimensions(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	
	public Shape getShape() {
		return shape;
	}


	public void setShape(Shape shape) {
		this.shape = shape;
		
		loadModel();
	}


	public Texture2D getTexture() {
		return texture;
	}


	public void setTexture(Texture2D texture) {
		this.texture = texture;
	}

}
