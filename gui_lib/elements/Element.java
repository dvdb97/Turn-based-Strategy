package elements;

import assets.models.Element_Model;
import assets.textures.Texture;
import elements.containers.ContainerElement;
import gui_core.GUIManager;
import math.vectors.Vector4f;
import rendering.shapes.Shape;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;

public abstract class Element extends Element_Model implements Clickable {
	
	private final int ID;
	
	private ContainerElement parent;
	
	private Shape shape;
	
	
	private boolean visible;
	
	private boolean resizable;
	
	private boolean movable;
	
	private boolean closeable;


	public Element(Shape shape, Texture texture, float x, float y, float width, float height) {
		super(GL_TRIANGLES);
		
		this.ID = GUIManager.generateID();
		
		this.setTexture(texture);
		
		this.setVertexPositionData(shape.getPositionData(), 2, GL_DYNAMIC_DRAW);
		this.setVertexTexturePositionData(shape.getTexPosData(), 2, GL_DYNAMIC_DRAW);
		this.setElementArrayData(shape.getIndexData());
	}
	
	
	public Element(Shape shape, Vector4f color, float x, float y, float width, float height) {
		super(GL_TRIANGLES);
		
		this.ID = GUIManager.generateID();
		
		this.setVertexPositionData(shape.getPositionData(), 2, GL_DYNAMIC_DRAW);
		this.setVertexColorData(shape.getColorData(), 3, GL_DYNAMIC_DRAW);
		this.setElementArrayData(shape.getIndexData());
	}


	public void setParent(ContainerElement element) {
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

}
