package fundamental;

import static org.lwjgl.util.yoga.Yoga.*;

import java.util.HashMap;
import java.util.Set;

import gui_core.Input;
import rendering.Renderer2D;
import rendering.shapes.GUIShape;
import utils.IndexManager;

@SuppressWarnings("serial")
class GUIElementNotFoundException extends RuntimeException {}


public class GUIContainer<E extends Element> extends Element implements IContainer<E> {
	
	//Keeps track of the indices that are currently in use.
	private IndexManager indexManager;
	
	//Maps all
	private HashMap<E, Integer> children;
	
	//Settings
	private FlexDirection flexDirection;
	
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
		indexManager = new IndexManager();
		children = new HashMap<E, Integer>();
		this.maxChildren = maxChildren;
		
		setFlexDirection(flexDirection);
	}
	
	
	/**
	 * 
	 * @param flexDirection Specifies the layout of this Container.
	 */
	public void setFlexDirection(FlexDirection flexDirection) {
		this.flexDirection = flexDirection;
		
		switch (flexDirection) {
			case ROW:
				YGNodeStyleSetFlexDirection(layoutID, YGFlexDirectionRow);
				break;
			case COLUMN:
				YGNodeStyleSetFlexDirection(layoutID, YGFlexDirectionColumn);
				break;
			case ROW_REVERSE:
				YGNodeStyleSetFlexDirection(layoutID, YGFlexDirectionRowReverse);
				break;
			case COLUMN_REVERSE:
				YGNodeStyleSetFlexDirection(layoutID, YGFlexDirectionColumnReverse);
				break;
		}
	}
	
	
	/**
	 * 
	 * @return Returns the flex direction that is currently set for this Container.
	 */
	public FlexDirection getFlexDirection() {
		return flexDirection;
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
		
		int index = indexManager.getNewIndex();
		children.put(element, index);
		YGNodeInsertChild(layoutID, element.layoutID, index);
	}
	
	
	/**
	 * 
	 * Removes an element from this Container.
	 * 
	 * @param element The element to remove from this Container.
	 */
	public void removeChild(E element) {
		if (!children.containsKey(element)) {
			throw new GUIElementNotFoundException();
		}
		
		int index = children.get(element);
		indexManager.freeIndex(index);
		children.remove(element);
		YGNodeRemoveChild(layoutID, index);
	}
	
	
	/**
	 * Removes all children from this Container.
	 */
	public void removeAllChildren() {
		indexManager.freeAll();
		children.clear();
		YGNodeRemoveAllChildren(layoutID);
	}
	
	
	/**
	 * 
	 * @return Returns all children that are currently in this Container.
	 */
	public Set<E> getChildren() {
		return children.keySet();
	}
	
	
	@Override
	public int getNumChildren() {
		return children.size();
	}


	private int alignmentToYGEnum(Alignment align) {
		switch (align) {
			case AUTO:
				return YGAlignAuto;
			case CENTER:
				return YGAlignCenter;
			case BASELINE:
				return YGAlignBaseline;
			case FLEX_START:
				return YGAlignFlexStart;
			case FLEX_END:
				return YGAlignFlexEnd;
			case SPACE_AROUND:
				return YGAlignSpaceAround;
			case SPACE_BETWEEN:
				return YGAlignSpaceBetween;
			default:
				return YGAlignStretch;
		}
	}
	
	
	@Override
	public void setContentAlignment(Alignment align) {
		YGNodeStyleSetAlignItems(layoutID, alignmentToYGEnum(align));
	}
	
	
	//*****************************************************************

	
	@Override
	public void resetInputStates(Input input) {
		children.keySet().forEach((e) -> e.resetInputStates(input));
		
		super.resetInputStates(input);
	}
	

	@Override
	public boolean processInput(int parentX, int parentY, Input input) {		
		int x = parentX + getLocalXPosition();
		int y = parentY + getLocalYPosition();
		
		if (super.processInput(parentX, parentY, input)) {
			children.keySet().forEach((e) -> e.processInput(x, y, input));
			
			return true;
		}
		
		return false;
	}


	@Override
	public void render(int parentX, int parentY) {
		super.render(parentX, parentY);
		
		//Add a scissor to make sure that the children are only rendered inside the container.
		Renderer2D.saveState();
		int x = getGlobalXPosition(parentX);
		int y = getGlobalYPosition(parentY);
		Renderer2D.scissor(x, y, getWidth(), getHeight());
		
		//Render all children.
		getChildren().forEach((e) -> e.render(parentX + getLocalXPosition(), parentY + getLocalYPosition()));
		
		Renderer2D.resetScissor();
		Renderer2D.restoreState();
	}
	
}
