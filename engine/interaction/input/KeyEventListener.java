package interaction.input;

public interface KeyEventListener {
	
	public enum Action {
		KEY_DOWN, KEY_PRESSED, KEY_RELEASED
	}
	
	public abstract void handle(int key, Action action);

}
