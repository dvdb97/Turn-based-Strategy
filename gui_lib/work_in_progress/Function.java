package work_in_progress;

@FunctionalInterface
public interface Function<E extends Element> {
	
	public abstract void execute(E element);
	
}

