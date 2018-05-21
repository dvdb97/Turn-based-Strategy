package work_in_progress;

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
