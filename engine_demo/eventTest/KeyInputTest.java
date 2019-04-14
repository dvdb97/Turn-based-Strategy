package eventTest;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import interaction.Window;
import interaction.input.KeyEventListener;
import interaction.input.KeyEventManager;
import interaction.input.KeyInputHandler;
import rendering.RenderEngine;

public class KeyInputTest {
	
	private static Window window;

	public static void main(String[] args) {
		window = new Window();
		window.createWindowedWindow("Event System Test");
		window.setKeyInputCallback(new KeyInputHandler());
		
		RenderEngine.init(window);
		RenderEngine.enableDepthTest();
		RenderEngine.setSwapInterval(1);
		
		run();
	}
	
	
	public static void run() {
		KeyEventManager keyEvents = new KeyEventManager();
		
		keyEvents.addKeyDownEventListener((key, action) -> System.err.println(key + " down!"));
		keyEvents.addKeyPressedEventListener((key, action) -> System.out.println(key));
		
		KeyInputHandler.setKeyEventManager(keyEvents);
		
		while (!KeyInputHandler.keyPressed(GLFW_KEY_ESCAPE)) {
			RenderEngine.clear();

			KeyInputHandler.pollEvents();
			
			RenderEngine.swapBuffers();
		}
	}

}
