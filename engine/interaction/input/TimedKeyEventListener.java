package interaction.input;

import utils.Cooldown;

public abstract class TimedKeyEventListener implements KeyEventListener {

	private Cooldown cooldown;
	
	public TimedKeyEventListener(double duration) {
		cooldown = new Cooldown(duration);
	}
	
	
	@Override
	public void handle(int key) {
		if (cooldown.ended()) {
			timedHandle(key);
			cooldown.refresh();
		}
	}
	
	
	public abstract void timedHandle(int key);
	
}
