package function;

import fundamental.GUIElement;

public interface BooleanFunction<E extends GUIElement> {
	
	boolean test(E e);
	
}
