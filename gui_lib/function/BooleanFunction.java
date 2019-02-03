package function;

import fundamental.Element;

public interface BooleanFunction<E extends Element> {
	
	boolean test(E e);
	
}
