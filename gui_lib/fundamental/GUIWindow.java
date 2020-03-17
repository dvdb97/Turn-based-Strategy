package fundamental;

import java.util.HashSet;
import java.util.Set;

import assets.IDeletable;
import gui_core.GUIManager;
import gui_core.Input;
import layout.IGUILayoutNode.Alignment;
import layout.IGUILayoutNode.Direction;
import layout.IGUILayoutNode.FlexDirection;
import layout.yoga.GUIYogaRootNode;
import rendering.shapes.GUIShape;

public class GUIWindow implements IContainer<GUIElement>, IDeletable {
	
	private final GUIYogaRootNode node;
	
	//Tools for managing the children.
	private HashSet<GUIElement> children;
	
	//Settings
	protected FlexDirection flexDirection;
	private GUIShape shape;
	
	//States
	private boolean active = true;
	private boolean obsolete = false;
	
	public GUIWindow(GUIShape shape, int x, int y, int width, int height, FlexDirection flexDirection) {
		node = GUIYogaRootNode.createRootNode(x, y, width, height);
		children = new HashSet<GUIElement>();		
		
		this.shape = shape;
		setFlexDirection(flexDirection);
		GUIManager.addWindow(this);
	}
	
	
	/**
	 * 
	 * @param flexDirection Specifies the layout of this Container.
	 */
	public void setFlexDirection(FlexDirection direction) {
		node.setFlexDirection(direction);
	}
	

	/**
	 * 
	 * @return Returns the flex direction that is currently set for this Container.
	 */
	public FlexDirection getFlexDirection() {
		return node.getFlexDirection();
	}
	
	
	public void setPadding(Direction direction, int padding) {
		node.setPadding(direction, padding);
	}
	
	
	public void setPadding(Direction direction, float percent) {
		node.setPadding(direction, percent);
	}
	
	
	/**
	 * 
	 * Adds an element to this Container.
	 * 
	 * @param element The element to add to this container.
	 */
	public void addChild(GUIElement element) {
		children.add(element);
		node.setChild(element.node);
	}
	
	
	/**
	 * 
	 * Removes an element from this Container.
	 * 
	 * @param element The element to remove from this Container.
	 */
	public void removeChild(GUIElement element) {
		if (!children.contains(element)) {
			throw new GUIElementNotFoundException();
		}
		
		children.remove(element);
		node.removeChild(element.node);
	}
	
	
	/**
	 * Removes all children from this Container.
	 */
	public void removeAllChildren() {
		children.clear();
		node.removeAllChildren();
	}
	
	
	/**
	 * 
	 * @return Returns all children that are currently in this Container.
	 */
	public Set<GUIElement> getChildren() {
		return children;
	}
	
	
	@Override
	public int getNumChildren() {
		return children.size();
	}
	
	
	public void setXCoordinate(int x) {
		node.setLocalXCoordinate(x);
	}
	
	
	public int getXCoordinate() {
		return node.getLocalXCoordinate();
	}
	
	
	public void setYCoordinate(int y) {
		node.setLocalYCoordinate(y);
	}
	
	
	public int getYCoordinate() {
		return node.getLocalYCoordinate();
	}
	
	
	public void setWidth(int width) {
		node.setWidth(width);
	}
	
	
	public int getWidth() {
		return node.getWidth();
	}
	
	
	public void setHeight(int height) {
		node.setHeight(height);
	}
	
	
	public int getHeight() {
		return node.getHeight();
	}
	
	
	@Override
	public void setContentAlignment(Alignment align) {
		node.setAlignment(align);
	}
	
	
	/**
	 * 
	 * @return Returns the shape of this element.
	 */
	public GUIShape getShape() {
		return shape;
	}
	
	
	/**
	 * 
	 * @param shape Sets the shape of this element.
	 */
	public void setShape(GUIShape shape) {
		this.shape = shape;
	}
	
	
	/**
	 * Override to specify a shape for this GUIWindow.
	 */
	public void render() {
		if (!active)
			return;
		
		node.calculateLayout();
		
		shape.render(getXCoordinate(), getYCoordinate(), getWidth(), getHeight());
		
		children.forEach((e) -> e.render(getXCoordinate(), getYCoordinate()));
	}
	
	
	/**
	 * 
	 * Check if the cursor is currently targeting this window.
	 * 
	 * @param parentX The x coordinate of the parent window.
	 * @param parentY The y coordinate of the parent window.
	 * @param cursorX The x coordinate of the cursor.
	 * @param cursorY The y coordinate of the cursor.
	 * @return Returns true if the cursor is targeting this window.
	 */
	public boolean isTargeted(int cursorX, int cursorY) {
		return shape.isTargeted(getXCoordinate(), getYCoordinate(), getWidth(), getHeight(), cursorX, cursorY);
	}
	
	
	/**
	 * 
	 * Processes the input.
	 * 
	 * @param cursorX The x position of the cursor.
	 * @param cursorY The y position of the cursor.
	 * @return Returns true if this GUIWindow was effected by the user's input.
	 */
	public boolean processInput(Input input) {
		if (!active)
			return false;
		
		if (isTargeted(input.cursorX, input.cursorY)) {
			for (GUIElement element : children) {
				element.processInput(getXCoordinate(), getYCoordinate(), input);
			}
			
			return true;
		}
		
		return false;
	}
	
	
	public void deactivate() {
		active = false;
	}
	
	
	public void activate() {
		active = true;
	}
	
	
	public void close() {
		obsolete = true;
		deactivate();
	}
	
	
	public boolean isActive() {
		return active;
	}
	
	
	public boolean isObsolete() {
		return obsolete;
	}
	

	@Override
	public void delete() {
		node.delete();
	}	
	
}
