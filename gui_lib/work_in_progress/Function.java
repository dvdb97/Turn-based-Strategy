package work_in_progress;

//this interface is similar to java.util.function.Consumer

@FunctionalInterface
public interface Function<E extends Element> {
	
	void execute(E element);
	
	default Function<E> andThen(Function<E> function) {
		return (E element) -> {this.execute(element);function.execute(element);};
	}
	
}

