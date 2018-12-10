package work_in_progress.test;

import fundamental.Element;

public interface TestFunction<E extends Element> {
	
	boolean test(E e);
	
}
