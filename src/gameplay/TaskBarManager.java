package gameplay;

import gui.TaskBar;
import implementations.GUINumberField;

public class TaskBarManager {
	
	private static TaskBar gui;
	private static GUINumberField turnCountText;
	private static GUINumberField cashText;
	private static GUINumberField gainText;
	
	public static void init() {
		gui = new TaskBar();
		turnCountText = gui.getTurnCountText();
		cashText = gui.getCashText();
		gainText = gui.getGainText();
	}
	
	public static void startNextTurn() {
		turnCountText.increaseNumber(1);
		Tribe.increaseCash(Tribe.getGain());
	}
	
	static void setCashText(int cash) {
		cashText.setNumber(cash);
	}
	
	static void setGainText(int gain) {
		gainText.setNumber(gain);
	}
	
}
