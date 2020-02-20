package function;

public interface GUIDragListener {
	
	void execute(int x, int y, int cursorX, int cursorY);
	
	default GUIDragListener andThen(GUIDragListener function) {
		return (int x, int y, int cursorX, int cursorY) -> {this.execute(x, y, cursorX, cursorY);function.execute(x, y, cursorX, cursorY);};
	}

}
