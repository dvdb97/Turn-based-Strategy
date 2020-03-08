package fundamental;

import java.util.HashSet;
import java.util.Set;

import gui_core.Input;
import layout.IGUILayoutNode.Alignment;
import layout.IGUILayoutNode.FlexDirection;
import rendering.Renderer2D;
import rendering.shapes.GUIShape;

@SuppressWarnings("serial")
class GUIElementNotFoundException extends RuntimeException {}


public class GUIContainer<E extends Element> extends Element implements IContainer<E> {
	
	//Maps all
	private HashSet<E> children;
	
	//The maximum number of children this container can have.
	private int maxChildren;
	
	
	//******************** constructor *******************************
	
	/**
	 * 
	 * @param width The width of the Container in pixels.
	 * @param height The height of the Container in pixels.
	 * @param flexDirection Specifies the layout of this Container.
	 */
	protected GUIContainer(GUIShape shape, int width, int height, FlexDirection flexDirection) {
		super(shape, width, height);
		init(flexDirection, Integer.MAX_VALUE);
	}
	
	
	/**
	 * 
	 * @param width The width of the Container in pixels.
	 * @param height The height of the Container in pixels.
	 * @param flexDirection Specifies the layout of this Container.
	 * @param maxChildren The maximum number of children this container can have.
	 */
	protected GUIContainer(GUIShape shape, int width, int height, FlexDirection flexDirection, int maxChildren) {
		super(shape, width, height);
		init(flexDirection, maxChildren);
	}
	
	
	/**
	 * 
	 * @param widthPercent The width of the Container in relation to its parent.
	 * @param heightPercent The height of the Container in relation to its parent.
	 * @param flexDirection Specifies the layout of this Container.
	 */
	protected GUIContainer(GUIShape shape, float widthPercent, float heightPercent, FlexDirection flexDirection) {
		super(shape, widthPercent, heightPercent);
		init(flexDirection, Integer.MAX_VALUE);
	}
	
	
	/**
	 * 
	 * @param widthPercent The width of the Container in relation to its parent.
	 * @param heightPercent The height of the Container in relation to its parent.
	 * @param flexDirection Specifies the layout of this Container.
	 * @param maxChildren The maximum number of children this container can have.
	 */
	protected GUIContainer(GUIShape shape, float widthPercent, float heightPercent, FlexDirection flexDirection, int maxChildren) {
		super(shape, widthPercent, heightPercent);
		init(flexDirection, maxChildren);
	}
	
	
	private void init(FlexDirection flexDirection, int maxChildren) {
		children = new HashSet<E>();
		this.maxChildren = maxChildren;
		
		setFlexDirection(flexDirection);
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
	
	
	/**
	 * 
	 * Adds an element to this Container.
	 * 
	 * @param element The element to add to this container.
	 */
	public void addChild(E element) {
		if (children.size() == maxChildren) {
			System.err.println("Can't add a new child as the maximum number of children was reached!");
		}

		children.add(element);
		node.setChild(element.node);
	}
	
	
	/**
	 * 
	 * Removes an element from this Container.
	 * 
	 * @param element The element to remove from this Container.
	 */
	public void removeChild(E element) {
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
		node.removeAllChildren();
		children.clear();
	}
	
	
	/**
	 * 
	 * @return Returns all children that are currently in this Container.
	 */
	public Set<E> getChildren() {
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
	
	
	//*****************************************************************

	
	@Override
	public void resetInputStates(Input input) {
		children.forEach((e) -> e.resetInputStates(input));
		
		super.resetInputStates(input);
	}
	

	@Override
	public boolean processInput(int parentX, int parentY, Input input) {		
		int x = parentX + getLocalXPosition();
		int y = parentY + getLocalYPosition();
		
		if (isTargeted(parentX, parentY, input.cursorX, input.cursorY)) {
			children.forEach((e) -> e.processInput(x, y, input));
			
			for (Element child : children) {
				if (child.processInput(x, y, input)) {
					return true;
				}
			}
			
			updateInputStates(input);
			
			return true;
		} else {
			resetInputStates(input);
		}
		
		return false;
	}


	@Override
	public void render(int parentX, int parentY) {
		super.render(parentX, parentY);
		
		//Add a scissor to make sure that the children are only rendered inside the container.
		Renderer2D.saveState();
		int x = parentX + getLocalXPosition();
		int y = parentY + getLocalYPosition();
		Renderer2D.scissor(x, y, getWidth(), getHeight());
		
		//Render all children.
		getChildren().forEach((e) -> e.render(parentX + getLocalXPosition(), parentY + getLocalYPosition()));
		
		Renderer2D.resetScissor();
		Renderer2D.restoreState();
	}


	@Override
	public void delete() {
		children.forEach(e -> e.delete());
		
		super.delete();
	}
	
}
