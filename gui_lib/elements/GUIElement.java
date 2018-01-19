package elements;

import assets.models.Element_Model;
import assets.textures.Texture;
import assets.textures.Texture2D;
import elements.containers.GUIContainerElement;
import gui_core.GUIManager;
import math.matrices.Matrix33f;
import math.vectors.Vector4f;
import rendering.RenderEngine;
import rendering.shapes.GUIShape;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;

public abstract class GUIElement extends Element_Model implements Clickable {
	
	private final int ID;
	
	private GUIContainerElement parent;
	
	private GUIShape shape;
	
	private Matrix33f renderingMatrix;
	
	private Texture2D texture;
	
	private Vector4f color;
	
	private boolean updated;
	
	
	private float x, y;
	
	private float width, height;
	
	
	private boolean visible;
	
	private boolean resizable;
	
	private boolean movable;
	
	private boolean closeable;


	public GUIElement(GUIShape shape, Texture2D texture, float x, float y, float width, float height) {
		super(GL_TRIANGLES);
		
		this.ID = GUIManager.generateID();
		
		this.texture = texture;
		this.color = null;
		
		this.setVertexPositionData(shape.getPositionData(), 2, GL_DYNAMIC_DRAW);
		this.setVertexTexturePositionData(shape.getTexPosData(), 2, GL_DYNAMIC_DRAW);
		this.setElementArrayData(shape.getIndexData());
	}
	
	
	public GUIElement(GUIShape shape, Vector4f color, float x, float y, float width, float height) {
		super(GL_TRIANGLES);
		
		this.ID = GUIManager.generateID();
		
		this.texture = null;
		this.color = color;
		
		this.setVertexPositionData(shape.getPositionData(), 2, GL_DYNAMIC_DRAW);
		this.setElementArrayData(shape.getIndexData());
	}
	
	
	public void render() {
		
		if (texture != null) {
			GUIManager.useGuiShader(renderingMatrix);
		} else {
			GUIManager.useGuiShader(renderingMatrix, color);
		}
		
		RenderEngine.draw(this, texture);
		
		GUIManager.disableGuiShader();
		
	}
	

	public void setParent(GUIContainerElement element) {
		this.parent = element;
	}
	
	
	public boolean isVisible() {
		return visible;
	}


	public void setVisible(boolean visible) {
		this.visible = visible;
	}


	public boolean isResizable() {
		return resizable;
	}


	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}


	public boolean isMovable() {
		return movable;
	}


	public void setMovable(boolean movable) {
		this.movable = movable;
	}


	public boolean isCloseable() {
		return closeable;
	}


	public void setCloseable(boolean closeable) {
		this.closeable = closeable;
	}


	public float getX() {
		return x;
	}


	public float getY() {
		return y;
	}


	public float getWidth() {
		return width;
	}


	public float getHeight() {
		return height;
	}
	
	
	public Matrix33f getRenderingMatrix() {
		return renderingMatrix;
	}

}
