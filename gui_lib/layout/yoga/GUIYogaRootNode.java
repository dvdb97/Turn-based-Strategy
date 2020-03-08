package layout.yoga;

import static org.lwjgl.util.yoga.Yoga.*;

public class GUIYogaRootNode extends GUIYogaNode {
	
	private final long config;
	
	private int width, height;

	private GUIYogaRootNode(long layoutID, long config, int width, int height) {
		super(layoutID);
		
		this.config = config;
		this.width = width;
		this.height = height;
	}
	
	public static GUIYogaRootNode createRootNode(int width, int height) {
		long config = YGConfigNew();
		long layoutID = YGNodeNewWithConfig(config);
		
		GUIYogaRootNode node = new GUIYogaRootNode(layoutID, config, width, height);
		
		return node;
	}
	
	
	public void calculateLayout() {
		YGNodeCalculateLayout(layoutID, width, height, YGDirectionLTR);
	}

	
	@Override
	public void delete() {
		YGConfigFree(config);
		
		super.delete();
	}
	
}
