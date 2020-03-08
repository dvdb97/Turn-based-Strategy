package layout.yoga;

import static org.lwjgl.util.yoga.Yoga.*;

public class GUIYogaRootNode extends GUIYogaNode {
	
	private final long config;
	
	private int x, y;
	private int width, height;

	private GUIYogaRootNode(long layoutID, long config, int x, int y, int width, int height) {
		super(layoutID);
		
		this.config = config;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}
	
	public static GUIYogaRootNode createRootNode(int x, int y, int width, int height) {
		long config = YGConfigNew();
		long layoutID = YGNodeNewWithConfig(config);
		
		GUIYogaRootNode node = new GUIYogaRootNode(layoutID, config, x, y, width, height);
		
		return node;
	}
	
	
	public void calculateLayout() {
		YGNodeCalculateLayout(layoutID, width, height, YGDirectionLTR);
	}
	
	@Override
	public int getGlobalXCoordinate() {
		return x;
	}

	@Override
	public int getGlobalYCoordinate() {
		return y;
	}

	@Override
	public void delete() {
		YGConfigFree(config);
		
		super.delete();
	}
	
}
