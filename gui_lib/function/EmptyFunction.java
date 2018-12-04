package function;

import fundamental.Element;

/**
 * an implementation of the FunctionalInterface Function, that does nothing when executed
 * 
 * @author jona
 *
 */
class EmptyFunction implements Function<Element> {
	
	@Override
	public void execute(Element element) {
		//do nothing
	}

}
