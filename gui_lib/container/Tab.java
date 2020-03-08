package container;

import assets.meshes.geometry.Color;
import fundamental.GUIContainer;
import layout.IGUILayoutNode.FlexDirection;
import fundamental.Element;
import rendering.shapes.implemented.GUIQuad;

/**
 * 
 * Tab is just an container (so far)
 * it has the name "tab", because i need an non-abstract element container to implement an tab menu
 * 
 * important (in relation to TabMenu): Tab does not contain the button you are pressing to switch to this tab
 * 
 * @author jona
 */
public class Tab extends GUIContainer<Element> {
	
	/**
	 * 
	 * @param color The color of the tab.
	 * @param flexDirection Specifies the layout of this Container.
	 */
	public Tab(Color color, FlexDirection flexDirection) {
		super(new GUIQuad(color), 100f, 100f, flexDirection);
	}
	
}
