package interaction.input;

@FunctionalInterface
public interface KeyEventListener {
	
	//TODO: may not be neccessary
	public enum Action {
		KEY_DOWN, KEY_PRESSED, KEY_RELEASED
	}
	
	public abstract void handle(int key);

}
