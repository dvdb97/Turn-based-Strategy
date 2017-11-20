package gui;

import elements.GUI_Window;
import interaction.Window;
import rendering.GUI_Shader;

public class GUI {
	
	private static Window window;
	
	private static GUI_Shader shader;
	
	public static void init(Window win) {
		
		window = win;
		
		shader = new GUI_Shader();
		
	}
	
	
	public static void close() {
		
	}
	
	
	public static void draw(GUI_Window gui_window) {
		shader.use();
		
		gui_window.display();
		
		shader.disable();
	}

}
