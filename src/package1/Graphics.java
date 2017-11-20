package package1;

import assets.models.Element_Model;
import assets.models.Illuminated_Model;
import assets.models.abstractModels.Renderable;
import assets.scenes.Scene;
import input.CursorPosInput;
import input.KeyInput;
import input.MouseInputManager;
import interaction.Window;
import lwlal.Vector4f;
import rendering.RenderEngine;
import rendering.shaders.standardShaders.lightShader.LightShader;
import shaders.StandardShader;

public class Graphics {
	
	//Constants:
	
	public static enum LIGHT_MODES {
		LOW, MEDIUM, HIGH
	}
	
	public static enum WINDOW_MODES {
		FULLSCREEN, WINDOWED, WINDOWED_FULLSCREEN
	}
	
	
	//Settings:
	
	private static LIGHT_MODES light;
	
	private static WINDOW_MODES windowMode;
	
	private static int graphics;
	
	
	//The window:
	
	private static Window window;
	
	
	//Shaders:
	
	private static LightShader lightShader;
	
	
	public static void init(WINDOW_MODES windowMode, int graphics, LIGHT_MODES light) {
		
		initWindow(windowMode);
		
		initRenderer();
		
		initShaders();
		
		Graphics.windowMode = windowMode;
		
		Graphics.light = light;
		
		Graphics.graphics = graphics;
		
	}
	
	
	private static void initWindow(WINDOW_MODES windowSize) {
		
		window = new Window();
		
		//TODO: Window hints
		
		if (windowSize == WINDOW_MODES.FULLSCREEN) {
			window.createFullscreenWindow(Core.name + Core.version);
		}
		
		if (windowSize == WINDOW_MODES.WINDOWED) {
			window.createWindowedWindow(Core.name + Core.version);
		}
		
		window.setKeyInputCallback(new KeyInput());
		window.setMouseInputCallback(new MouseInputManager());
		window.setMousePosInput(new CursorPosInput());
		
	}
	
	
	private static void initRenderer() {
		
		RenderEngine.init(window);
		
		RenderEngine.setSwapInterval(1);
		
		RenderEngine.enableDepthTest();
		
		RenderEngine.setClearColor(new Vector4f(0.0f, 0.0f, 1.0f, 1.0f));
		
		//glEnable(...);
		
	}
	
	
	private static void initShaders() {
		
		//If the given light mode is HIGH go for a per fragment lighting shader else go for a per vertex shader
		lightShader = light == LIGHT_MODES.HIGH ? LightShader.createPerFragmentLightShader() : LightShader.createPerVertexLightShader();
		
	}
	
	
	public static void close() {
		RenderEngine.close();
		
		window.close();
	}
	
	
	public static void drawIlluminatedModel(Illuminated_Model model) {
		
		lightShader.use();
		
		RenderEngine.draw(model, null); //TODO: Texture usage
		
		lightShader.disable();
		
	}
	
	
	public static void drawScene(Scene scene) {
		//Manage lights and that stuff!
		
		
	}
	
	

}
