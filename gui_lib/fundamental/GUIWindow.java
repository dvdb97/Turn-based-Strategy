package fundamental;

import static org.lwjgl.util.yoga.Yoga.*;

import java.util.HashMap;
import java.util.Set;

import assets.IDeletable;
import fundamental.Element.Alignment;
import gui_core.GUIManager;
import gui_core.Input;
import rendering.shapes.GUIShape;
import utils.IndexManager;

public class GUIWindow implements IContainer<Element>, IDeletable {
	
	//TODO: Revert to private final 
	private final long config;
	private final long layoutID;
	
	//Tools for managing the children.
	private IndexManager indexManager;
	private HashMap<Element, Integer> children;
	
	//Settings
	protected FlexDirection flexDirection;
	protected int x, y, width, height;
	private GUIShape shape;
	
	//States
	private boolean active = true;
	private boolean obsolete = false;
	
	public GUIWindow(GUIShape shape, int x, int y, int width, int height, FlexDirection flexDirection) {
		config = YGConfigNew();
		layoutID = YGNodeNewWithConfig(config);
		GUIManager.addWindow(this);
		
		indexManager = new IndexManager();
		children = new HashMap<Element, Integer>();
		
		this.shape = shape;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
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
	public void addChild(Element element) {
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
	public void removeChild(Element element) {
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
	public Set<Element> getChildren() {
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
		
		YGNodeCalculateLayout(layoutID, width, height, YGDirectionLTR);
		
		shape.render(x, y, width, height);
		
		children.keySet().forEach((e) -> e.render(x, y));
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
		YGNodeFreeRecursive(layoutID);
		YGConfigFree(config);
	}	
	
}
