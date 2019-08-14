package application;

import application.settings.Settings;
import application.settings.Settings.WindowMode;
import interaction.Window;
import interaction.input.KeyInputHandler;
import interaction.input.MouseInputHandler;
import rendering.RenderEngine;

public abstract class Application {
	
	private Window window;
	private Settings settings;
	
	public Application(Settings settings) {
		this.settings = settings;
		
		setUpWindow();
		setUpRenderer();
	}
	
	
	private void setUpWindow() {
		window = new Window();
		
		if (settings.winMode == WindowMode.WINDOWED) {
			window.createWindowedWindow(settings.windowTitle, settings.resolution);
		} else {
			window.createWindowedWindow(settings.windowTitle, settings.resolution);
		}
		
		window.setKeyInputCallback(new KeyInputHandler());
		window.setMouseInputCallback(new MouseInputHandler());
	}
	
	
	private void setUpRenderer() {
		RenderEngine.init(window);
		RenderEngine.enableDepthTest();
		RenderEngine.setSwapInterval(1);
	}

}
