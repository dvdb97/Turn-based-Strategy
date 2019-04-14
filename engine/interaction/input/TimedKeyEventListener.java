package interaction.input;

import utils.Cooldown;

public abstract class TimedKeyEventListener implements KeyEventListener {

	private Cooldown cooldown;
	
	public TimedKeyEventListener(double duration) {
		cooldown = new Cooldown(duration);
	}
	
	
	@Override
	public void handle(int key, Action action) {
		if (cooldown.ended()) {
			timedHandle(key, action);
			cooldown.refresh();
		}
	}
	
	
	public abstract void timedHandle(int key, Action action);
	
}
