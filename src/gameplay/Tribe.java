package gameplay;

//TODO: currently static, because there is only one (the users) tribe, in future there will be more (hopefully)
public class Tribe {
	
	private static int cash = 100;
	private static int gain = 10;
	
	
	public static int getCash() {
		return cash;
	}
	
	public static int getGain() {
		return gain;
	}
	
	public static void setCash(int cash) {
		Tribe.cash = cash;
		TaskBarManager.setCashText(cash);
	}
	
	public static void setGain(int gain) {
		Tribe.gain = gain;
		TaskBarManager.setGainText(gain);
	}
	
	public static void increaseCash(int delta) {
		setCash(Tribe.cash+delta);
	}
	
	public static void increaseGain(int delta) {
		setGain(Tribe.gain+delta);
	}
	
	
	
}
