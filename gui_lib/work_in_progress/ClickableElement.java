package work_in_progress;

public abstract class ClickableElement implements ElementBase, Clickable {
	
	protected Shape shape;
	
	protected ClickableElement(Shape shape) {
		this.shape = shape;
	}
	
}
