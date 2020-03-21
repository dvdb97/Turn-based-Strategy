package eventTest;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import interaction.Window;
import interaction.input.KeyEventListener;
import interaction.input.KeyEventManager;
import interaction.input.KeyInputHandler;
import rendering.Renderer;

public class KeyInputTest {
	
	private static Window window;

	public static void main(String[] args) {
		window = new Window();
		window.createWindowedWindow("Event System Test");
		window.setKeyInputCallback(new KeyInputHandler());
		
		Renderer.init(window);
		Renderer.enableDepthTest();
		Renderer.setSwapInterval(1);
		
		run();
	}
	
	
	public static void run() {
		KeyEventManager keyEventManager = new KeyEventManager();
		
		keyEventManager.addKeyDownEventListener((key) -> System.err.println(key + " down!"));
		keyEventManager.addKeyPressedEventListener((key) -> System.out.println(key));
		
		KeyInputHandler.addKeyEventManager(keyEventManager);
		
		while (!KeyInputHandler.keyPressed(GLFW_KEY_ESCAPE)) {
			Renderer.clear();

			KeyInputHandler.pollEvents();
			
			Renderer.swapBuffers();
		}
	}

}
