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

public class GUIWindow implements IContainer<Element>, IDeletable {
	
	private final GUIYogaRootNode node;
	
	//Tools for managing the children.
	private HashSet<Element> children;
	
	//Settings
	protected FlexDirection flexDirection;
	protected int x, y, width, height;
	private GUIShape shape;
	
	//States
	private boolean active = true;
	private boolean obsolete = false;
	
	public GUIWindow(GUIShape shape, int x, int y, int width, int height, FlexDirection flexDirection) {
		node = GUIYogaRootNode.createRootNode(x, y, width, height);
		children = new HashSet<Element>();		
		
		this.shape = shape;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
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
	public void addChild(Element element) {
		children.add(element);
		node.setChild(element.node);
	}
	
	
	/**
	 * 
	 * Removes an element from this Container.
	 * 
	 * @param element The element to remove from this Container.
	 */
	public void removeChild(Element element) {
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
	public Set<Element> getChildren() {
		return children;
	}
	
	
	@Override
	public int getNumChildren() {
		return children.size();
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
		
		shape.render(x, y, width, height);
		
		children.forEach((e) -> e.render(x, y));
	}
	
	
	/**
	 * 
	 * Processes the input.
	 * 
	 * @param cursorX The x position of the cursor.
	 * @param cursorY The y position of the cursor.
	 * @return Returns true if this GUIWindow was effected by the user's input.
	 */
	public void processInput(Input input) {
		if (!active)
			return;
			
		getChildren().forEach((e) -> e.processInput(x, y, input));
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
