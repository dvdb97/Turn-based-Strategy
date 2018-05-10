package elements.fundamental;

import assets.textures.Texture2D;
import elements.containers.GUIContainerElement;
import elements.functions.GUIEventHandler;
import gui_core.GUIManager;
import gui_core.GUIMatrixManager;
import math.matrices.Matrix44f;
import math.matrices.advanced.MatrixInversion44f;
import math.vectors.Vector4f;
import rendering.shapes.GUIShape;

public abstract class GUIElementBase implements GUIClickable {
	
	//The parent of this element
	private GUIContainerElement parent;
	
	//The dimensions of this element
	private float x, y, width, height;
	
	//The attributes of this element
	private boolean visible, resizable, movable, closeable;

	//The shape of this element
	private GUIShape shape;
	
	//The transformation matrix of this element
	private Matrix44f renderingMatrix;
	
	//The inverted transformation matrix
	private Matrix44f invertedRenderingMatrix;
	
	//The texture for rendering this element
	private Texture2D texture;
	
	//The color for rendering this element
	private Vector4f color;
	
	//Have there been any changes to this elements position and size?
	private boolean changed;		
	
	
	private GUIElementBase(GUIShape shape, Texture2D texture, Vector4f color, float x, float y, float width, float height) {
		
		this.parent = null;
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.changed = true;
		
		this.shape = shape;
		this.texture = texture;
		this.color = color;
		
		this.resizable = false;
		this.visible = true;
		this.movable = false;
		this.closeable = true;
		
	}

	
	public GUIElementBase(GUIShape shape, Texture2D texture, float x, float y, float width, float height) {
		
		this(shape, texture, null, x, y, width, height);
		
	}
	
	
	public GUIElementBase(GUIShape shape, Vector4f color, float x, float y, float width, float height) {
		
		this(shape, null, color, x, y, width, height);
		
	}
	
	
	public void render() {
		
		if (!visible) {
			return;
		}
		
		if(renderingMatrix == null) {
			System.out.println("GUI-EB: rendermatrix == null " + this);
		}
		
		shape.render(texture, color, renderingMatrix);
		
	}
	
	
	//TODO: Only save some computations by making sure the matrices are only updated when there were changes to the element
	
	/**
	 * updates rendering matrix (and its inversion)
	 */
	public void update() {
		
		updateRenderingMatrix();
		
	}
	
	
	/**
	 * updates rendering matrix (and its inversion)
	 */
	private void updateRenderingMatrix() {
		Matrix44f parentMatrix = new Matrix44f();
		
			
		this.renderingMatrix = GUIMatrixManager.generateTransformationMatrix44(x, y, width, height);

		
		if (parent != null) {
			parentMatrix = parent.getRenderingMatrix();
		}
		
		this.renderingMatrix = parentMatrix.times(renderingMatrix);
		this.invertedRenderingMatrix = MatrixInversion44f.computeMultiplicativeInverse(renderingMatrix);
	}
	
	
	/**
	 * 
	 * Processes the mouse input and checks if it effects this element.
	 * 
	 * @param cursorX The cursor x position.
	 * @param cursorY The cursor y position.
	 * @param leftMouseButtonDown Is the left mouse button pressed?
	 * @param rightMouseButtonDown Is the right mouse button pressed?
	 * @return Returns if this element is hit by this cursor.
	 */
	public boolean processInput(float cursorX, float cursorY, boolean leftMouseButtonDown, boolean rightMouseButtonDown) {
		
		//Compute the local space coordinates of the cursor position
		Vector4f vec = new Vector4f(cursorX, cursorY, 1f, 1f);
		vec = this.getInvertedRenderingMatrix().times(vec);
		
		if (shape.isHit(vec.getA(), vec.getB())) {
			
			if (leftMouseButtonDown) {
				onClick();
			} else {
				onHover();
			}
			
			return true;
			
		}
		
		return false;
		
	}
	

	/**
	 * 
	 * Saves a reference to the parent element of this element.
	 * This method will be used automatically when this element
	 * is put in a container element. 
	 * 
	 * Using this method outside of the library can be risky
	 * and is not recommended.
	 * 
	 * @param element A reference to the container element that contains this element.
	 */
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
	
	
	public GUIShape getShape() {
		return shape;
	}
	
	
	public void setColor(Vector4f color) {
		this.color = color;
	}
	
	
	public Vector4f getColor() {
		return color;
	}
	
	
	public void setTexture(Texture2D texture) {
		this.texture = texture;
	}


	public float getX() {
		return x;
	}
	
	
	public void setX(float x) {
		this.x = x;
	}


	public float getY() {
		return y;
	}

	
	public void setY(float y) {
		this.y = y;
	}


	public float getWidth() {
		return width;
	}
	
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	
	public void setHeight(float height) {
		this.height = height;
	}


	public float getHeight() {
		return height;
	}
	
	
	public Matrix44f getRenderingMatrix() {
		return renderingMatrix;
	}
	
	
	public Matrix44f getInvertedRenderingMatrix() {
		return invertedRenderingMatrix;
	}
	
	public void delete() {		
		parent.removeChild(this);
				
		onClose();
		
		this.delete();
	}

}
