package elements.functions;

import elements.fundamental.GUIElementBase;

@FunctionalInterface
public interface GUIEventHandler {
	
	public abstract void function(GUIElementBase element);

}
