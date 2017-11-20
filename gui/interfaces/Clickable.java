package interfaces;

public interface Clickable {
	
	public abstract void onClick();
	
	public abstract void onHover();
	
	public abstract void onLeave();
	
	public abstract void onHold();
	
	public abstract void onRelease();

	void processMouseInput(float CursorX, float CursorY, boolean click);

}
