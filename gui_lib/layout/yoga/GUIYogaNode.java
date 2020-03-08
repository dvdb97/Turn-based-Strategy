package layout.yoga;

import static org.lwjgl.util.yoga.Yoga.*;

import java.util.HashMap;

import layout.IGUILayoutNode;
import layout.IGUILayoutNode.Alignment;
import layout.IGUILayoutNode.Direction;
import layout.IGUILayoutNode.FlexDirection;
import utils.IndexManager;

public class GUIYogaNode implements IGUILayoutNode {
	
	private enum CoordinateMode {
		LAYOUT, CPIXEL, CPERCENT
	}
	
	//This node's ID that is used for Yoga
	protected final long layoutID;
	
	//This node's parent node.
	private GUIYogaNode parent;
	
	//This node's child nodes.
	private IndexManager indexManager;
	private HashMap<IGUILayoutNode, Integer> children;
	private FlexDirection flexDirection;
	
	//Custom coordinates.
	private CoordinateMode modeX, modeY;
	private float xPercent, yPercent;
	private int xPos, yPos;
	
	
	protected GUIYogaNode(long layoutID) {
		this.layoutID = layoutID;
		
		init();
	}
	
	
	private void init() {
		indexManager = new IndexManager();
		children = new HashMap<IGUILayoutNode, Integer>();
		modeX = modeY = CoordinateMode.LAYOUT;
	}
	
	
	public static GUIYogaNode createNode() {
		long layoutID = YGNodeNew();
		
		GUIYogaNode node = new GUIYogaNode(layoutID);
		
		return node;
	}
	
	
	public long getLayoutID() {
		return layoutID;
	}
	
	
	@Override
	public IGUILayoutNode getParent() {
		return parent;
	}
	
	
	public void setParent(GUIYogaNode node) {
		this.parent = node;
	}
	
	
	@Override
	public void setChild(IGUILayoutNode node) {
		GUIYogaNode child = (GUIYogaNode)node;
		
		long childID = child.getLayoutID();
		
		int index = indexManager.getNewIndex();
		children.put(child, index);
		YGNodeInsertChild(layoutID, childID, index);
		
		child.setParent(this);
	}


	@Override
	public void removeChild(IGUILayoutNode node) {
		if (!children.containsKey(node)) {
			return;
		}
		
		int index = children.get(node);
		indexManager.freeIndex(index);
		children.remove(node);
		YGNodeRemoveChild(layoutID, index);
		
		((GUIYogaNode)node).setParent(null);
	}
	
	
	@Override
	public void removeAllChildren() {
		indexManager.freeAll();
		children.clear();
		YGNodeRemoveAllChildren(layoutID);
	}
	

	@Override
	public void setLocalXCoordinate(int x) {
		this.modeX = CoordinateMode.CPIXEL;
		this.xPos = x;
	}
	
	
	@Override
	public void setLocalXCoordinate(float percent) {
		this.modeX = CoordinateMode.CPERCENT;
		this.xPercent = percent;		
	}
	

	@Override
	public int getLocalXCoordinate() {
		if (modeX == CoordinateMode.LAYOUT) {
			return (int)YGNodeLayoutGetLeft(layoutID);
		}
		
		if (modeX == CoordinateMode.CPERCENT) {
			return (int)(xPercent / 100f * (parent.getWidth() - getWidth()));
		}
		
		return xPos;
	}
	

	@Override
	public void setGlobalXCoordinate(int x) {
				
	}
	

	@Override
	public int getGlobalXCoordinate() {
		return 0;
	}
	

	@Override
	public void setLocalYCoordinate(int y) {
		this.modeY = CoordinateMode.CPIXEL;
		this.yPos = y;
	}
	
	
	@Override
	public void setLocalYCoordinate(float percent) {
		this.modeY = CoordinateMode.CPERCENT;
		this.yPercent = percent;
	}
	

	@Override
	public int getLocalYCoordinate() {
		if (modeY == CoordinateMode.LAYOUT) {
			return (int)YGNodeLayoutGetTop(layoutID);
		}
		
		if (modeY == CoordinateMode.CPERCENT) {
			return (int)(yPercent / 100f * (parent.getHeight() - getHeight()));
		}
		
		return yPos;
	}
	

	@Override
	public void setGlobalYCoordinate(int y) {
		
	}
	

	@Override
	public int getGlobalYCoordinate() {
		return 0;
	}
	

	@Override
	public void setWidth(int width) {
		YGNodeStyleSetWidth(layoutID, width);
	}
	

	@Override
	public void setWidth(float percent) {
		YGNodeStyleSetWidthPercent(layoutID, percent);
	}
	

	@Override
	public int getWidth() {
		return (int)YGNodeLayoutGetWidth(layoutID);
	}
	

	@Override
	public void setHeight(int height) {
		YGNodeStyleSetHeight(layoutID, height);
	}
	

	@Override
	public void setHeight(float percent) {
		YGNodeStyleSetHeightPercent(layoutID, percent);
	}
	

	@Override
	public int getHeight() {
		return (int)YGNodeLayoutGetHeight(layoutID);
	}
	
	
	/**
	 * 
	 * Utility function that converts from a Java enum to a Yoga enum.
	 * 
	 * @param dir The java enum value.
	 * @return The corresponding Yoga enum value.
	 */
	private int directionToYGEnum(Direction dir) {
		switch (dir) {
			case ALL:
				return YGEdgeAll;
			case BOTTOM:
				return YGEdgeBottom;
			case TOP:
				return YGEdgeTop;
			case LEFT:
				return YGEdgeLeft;
			case RIGHT:
				return YGEdgeRight;
			case START:
				return YGEdgeStart;
			case END:
				return YGEdgeEnd;
			case HORIZONTAL:
				return YGEdgeHorizontal;
			default:
				return YGEdgeVertical;
		}
	}
	

	@Override
	public void setMargin(Direction direction, int margin) {
		int edge = directionToYGEnum(direction);
		YGNodeStyleSetMargin(layoutID, edge, margin);
	}
	

	@Override
	public void setMargin(Direction direction, float percent) {
		int edge = directionToYGEnum(direction);
		YGNodeStyleSetMarginPercent(layoutID, edge, percent);
	}
	

	@Override
	public void setPadding(Direction dir, int padding) {
		int edge = directionToYGEnum(dir);
		YGNodeStyleSetPadding(layoutID, edge, padding);
	}
	

	@Override
	public void setPadding(Direction dir, float percent) {
		int edge = directionToYGEnum(dir);
		YGNodeStyleSetPaddingPercent(layoutID, edge, percent);
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
	public void setAlignment(Alignment align) {
		YGNodeStyleSetAlignSelf(layoutID, alignmentToYGEnum(align));		
	}

	
	public boolean equals(GUIYogaNode node) {
		return node.getLayoutID() == layoutID;
	}


	@Override
	public void setFlexDirection(FlexDirection direction) {
		this.flexDirection = direction;
		
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


	@Override
	public FlexDirection getFlexDirection() {
		return flexDirection;
	}
	
	
	public void delete() {
		YGNodeFree(layoutID);
	}

}
