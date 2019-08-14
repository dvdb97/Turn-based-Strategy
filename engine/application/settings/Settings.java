package application.settings;

import interaction.Resolution;

public class Settings {
	
	public enum WindowMode {
		WINDOWED,
		FULLSCREEN
	}
	
	//Window setting. Maybe create a  separate WindowSettings-class later.
	public String windowTitle = "unnamed window";
	public Resolution resolution = Resolution.FULL_HD;
	public WindowMode winMode = WindowMode.WINDOWED;
	
	
	public static Settings loadSettings(String path) {
		return null;
	}
	
}
