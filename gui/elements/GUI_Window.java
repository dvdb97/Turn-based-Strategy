package elements;

import java.util.LinkedList;

import rendering.shapes.Shape;
import assets.textures.Texture2D;
import interfaces.Clickable;

public abstract class GUI_Window extends ClickableElement implements Clickable {

	//List of all components of this window:
	private LinkedList<Element> elements = new LinkedList<Element>();
	
	//List of all clickable components of this window (subset of elements)
	private LinkedList<ClickableElement> clickableElements = new LinkedList<ClickableElement>();
	
	//**** Properties ****
	
	//Is the user allowed to move the window?
	private boolean movable = false;

	//Is the user allowed to resize the window?
	private boolean resizable = false;
	
	//If former is legal does the window keeps the same proportions if resized?
	private boolean lockedProportions  = false;
	
	//Is the window visible?
	private boolean visible = false;
	
	
	//The position of the cursor
	private float cursorX;
	private float cursorY;
	
	
	public GUI_Window(Shape shape, Texture2D texture, float xPos, float yPos, float width, float height) {
		super(shape, texture, xPos, yPos, width, height);
		// TODO Auto-generated constructor stub
	}


	public GUI_Window(String texturePath, float xPos, float yPos, float width, float height) {
		super(texturePath, xPos, yPos, width, height);
		// TODO Auto-generated constructor stub
	}


	public GUI_Window(Texture2D texture, float xPos, float yPos, float width, float height) {
		super(texture, xPos, yPos, width, height);
		// TODO Auto-generated constructor stub
	}

	
	
	//****************************** Core functionalities ******************************
	

	//Draw the window and all of its component on the screen
	@Override
	public void display() {
	
		if (!visible) {
			return;
		}
		
		draw();
		
		for (Element element : elements) {
			element.display();
		}
		
	}
		
	
	//Processes the current cursor input for the window itself and then lets all of its components interpret it
	@Override
	public void processMouseInput(float CursorX, float CursorY, boolean click) {
		//TODO: Doesn't work yet
		this.cursorX = cursorX;
		this.cursorY = cursorY;
		
		super.processMouseInput(CursorX, CursorY, click);
		
		for (ClickableElement element : clickableElements) {
			element.processMouseInput(CursorX, CursorY, click);
		}
		
	}
	
	
	//****************************** Managing the children of this window ******************************
	
	
	public int add(Element element) {
		this.elements.add(element);
		
		return this.elements.size() - 1;
	}
	
	
	public void remove(int ID) {
		if (ID >= elements.size()) {
			System.err.println("The Element that needs to be removed doesnt actually exist!");
			
			return;
		}
		
		this.elements.remove(ID);
	}
		
	
	//****************************** Moving and scaling the window ******************************	
	
	
	@Override
	public void onClick() {
		System.out.println("Click!");
		
		if (wasClicked()) {
			this.move(cursorX - getLastCursorXPosition(), cursorY - getLastCursorYPosition());
		}	
	}


	@Override
	public void move(float x, float y) {
		this.setPosition(getXPos() + x, getYPos() + y);
	}


	@Override
	public void setPosition(float x, float y) {
		
		if (!movable) {
			return;
		}
		
		System.out.println("Move!");
		
		this.setXPos(x);
		this.setYPos(y);
		
		for (Element element : elements) {
			element.setPosition(x, y);
		}
		
	}
	
	
	@Override
	public void resize(float x, float y) {
		
		if (!resizable) {
			return;
		}
		
		if (lockedProportions) {
			y = x;
		}
		
		this.setDimensions(getWidth() + x, getHeight() * y);
		
		for (Element element : elements) {
			element.resize(x, y);
		}
		
	}


	public void resize(float factor) {
		this.resize(factor, factor);		
	}

	
	//****************************** Get & Set ******************************
	
	
	public void setLockedProportions(boolean lockedProportions) {
		this.lockedProportions = lockedProportions;
	}


	public void setMovable(boolean movable) {
		this.movable = movable;
	}


	public void setScalable(boolean scalable) {
		this.resizable = scalable;
	}


	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
