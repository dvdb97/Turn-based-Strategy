package elements.functions;

import elements.GUIElementBase;

@FunctionalInterface
public interface GUIEventHandler {
	
	public abstract void function(GUIElementBase element);

}
