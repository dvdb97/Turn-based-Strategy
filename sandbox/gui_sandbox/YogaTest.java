package gui_sandbox;

import static org.lwjgl.util.yoga.Yoga.*;

public class YogaTest {
	
	//https://gist.github.com/mochja/c4788a4801885b58c1e38d7fb4c091a9
	
	public static void main(String[] args) {
		long config = YGConfigNew();
		
		long root = YGNodeNewWithConfig(config);
		YGNodeStyleSetFlexDirection(root, YGFlexDirectionRow);
		YGNodeStyleSetPadding(root, YGEdgeAll, 20);
		
		long image = YGNodeNew();
		YGNodeStyleSetWidth(image, 50);
		YGNodeStyleSetHeight(image, 50);
		YGNodeStyleSetAlignSelf(image, YGAlignCenter);
        YGNodeStyleSetMargin(image, YGEdgeEnd, 20);
        
        long text = YGNodeNew();
        YGNodeStyleSetHeight(text, 25);
        YGNodeStyleSetAlignSelf(text, YGAlignCenter);
        YGNodeStyleSetFlexGrow(text, 1);
        
        YGNodeInsertChild(root, image, 0);
        YGNodeInsertChild(root, text, 1);
        
        YGNodeCalculateLayout(root, 1920, 1080, YGDirectionLTR);
        
        System.out.println(YGNodeLayoutGetLeft(text));
		
		YGNodeFreeRecursive(root);
	}
	
}
