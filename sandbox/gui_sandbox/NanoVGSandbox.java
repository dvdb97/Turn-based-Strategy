package gui_sandbox;

import static org.lwjgl.nanovg.NanoVGGL3.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.awt.Color;

import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.meshes.Mesh3D;
import assets.meshes.fileLoaders.FileLoader;
import assets.scene.Scene;
import container.Tab;
import container.TabMenu;
import fundamental.GUITextField;
import gui_core.GUIManager;
import gui_core.Input;
import implementations.TestCircleElement;
import implementations.TestQuadElement;
import implementations.TestRoundedRectElement;
import input.Slider;
import input.buttons.OptionSet;
import input.buttons.RadioButton;
import fundamental.GUIButton;
import fundamental.DefaultWindow;
import interaction.Window;
import interaction.input.CursorPosInput;
import interaction.input.KeyInputHandler;
import interaction.input.MouseInputManager;
import layout.IGUILayoutNode.Direction;
import layout.IGUILayoutNode.FlexDirection;
import math.vectors.Vector3f;
import output.GUIColorBox;
import rendering.RenderEngine;
import rendering.Renderer2D;
import rendering.shapes.implemented.GUIQuad;
import utils.ColorPalette;

public class NanoVGSandbox {
	
	//https://lwjglgamedev.gitbooks.io/3d-game-development-with-lwjgl/content/chapter24/chapter24.html
	
	private static Window window;
	private static long nvg;
	
	public static void initOpengl() {
		window = new Window();
		window.createFullscreenWindow("NanoVG Sandbox");
		window.setKeyInputCallback(new KeyInputHandler());
		window.setMousePosInput(new CursorPosInput(window));
		window.setMouseInputCallback(new MouseInputManager());
		glfwWindowHint(GLFW_SAMPLES, 4);
		
		RenderEngine.init(window);
		RenderEngine.enableDepthTest();
		glEnable(GL_STENCIL_TEST);
	}
	
	
	public static void initNVG() {
		nvg = nvgCreate(NVG_ANTIALIAS | NVG_STENCIL_STROKES);
		
		if (nvg == NULL) {
			throw new RuntimeException("Could not init nvg!");
		}
	}
	
	
	public static void run() {
		Renderer2D.init();
		GUIManager.init(window);
		
		Mesh3D mesh = new Mesh3D();
		FileLoader.loadObjFile(mesh, "res/models/Suzanne.obj");
		mesh.getMaterial().castShadows = false;
		
		DirectionalLight light = new DirectionalLight(new Vector3f(1f, 1f, 1f));
		Camera camera = new Camera(new Vector3f(0f, 0f, 5f));
		Scene scene = new Scene(camera, light, null);
		
		Renderer2D.loadFont("ARIALBD", "res/fonts/ARIALBD.TTF");
		
		DefaultWindow sq_window = new DefaultWindow("DemoWindow", 100, 100, 500, 500, FlexDirection.ROW);
		sq_window.setTaskBarColor(ColorPalette.BLUE_1);
		
		TabMenu tabMenu = new TabMenu(100f, 100f, FlexDirection.COLUMN, ColorPalette.WHITE);
		
		
		//############################## Tab 1 ##############################
		Tab tab1 = new Tab(ColorPalette.WHITE,  FlexDirection.ROW);
		
		TestCircleElement circle = new TestCircleElement(100, 100);
		circle.setMargin(Direction.ALL, 10);
		circle.setLocalYPosition(50f);
		
		TestQuadElement quad = new TestQuadElement(100, 100);
		quad.setMargin(Direction.ALL, 10);
		quad.setLocalYPosition(50f);
		
		TestRoundedRectElement roundedRect = new TestRoundedRectElement(100, 100, 20);
		roundedRect.setMargin(Direction.ALL, 10);
		roundedRect.setLocalYPosition(50f);
		
		tab1.addChild(circle);
		tab1.addChild(quad);
		tab1.addChild(roundedRect);
		
		
		//############################## Tab 2 ##############################
		
		Tab tab2 = new Tab(ColorPalette.WHITE, FlexDirection.ROW);
		
		GUIColorBox colorBox = new GUIColorBox(80f, 80f, ColorPalette.WHITE);
		colorBox.setMargin(Direction.ALL, 10);
		
		RadioButton radio1 = new RadioButton(30);
		radio1.setMargin(Direction.ALL, 10);
		radio1.addEnableListener((e) -> colorBox.setColor(ColorPalette.RED));
		
		RadioButton radio2 = new RadioButton(30);
		radio2.setMargin(Direction.ALL, 10);
		radio2.addEnableListener((e) -> colorBox.setColor(ColorPalette.GREEN));
		
		RadioButton radio3 = new RadioButton(30);
		radio3.setMargin(Direction.ALL, 10);
		radio3.addEnableListener((e) -> colorBox.setColor(ColorPalette.BLUE));
		
		OptionSet<RadioButton> set = new OptionSet<RadioButton>(50, 400, FlexDirection.COLUMN);
		set.setMargin(Direction.ALL, 10);
		
		set.addDefaultButton(radio1);
		set.addButton(radio2);
		set.addButton(radio3);
		
		tab2.addChild(set);
		tab2.addChild(colorBox);
		
		
		//############################## Tab 3 ##############################
		
		Tab tab3 = new Tab(ColorPalette.WHITE, FlexDirection.COLUMN);
		
		GUITextField number = new GUITextField("0%", "FreeMono", 100f, 10f, 30);
		
		Slider slider = new Slider(new GUIQuad(ColorPalette.GRAY), new GUIQuad(ColorPalette.BLACK), 80f, 10f, FlexDirection.ROW);
		slider.setLocalYPosition(50f);
		slider.setLocalXPosition(50f);
		
		slider.addEventListener(e -> {
			number.setText(slider.getValue() + "%");
		});
		
		tab3.addChild(number);
		tab3.addChild(slider);
		
		
		//############################## Tab 3 ##############################
		
		Tab tab4 = new Tab(ColorPalette.WHITE, FlexDirection.COLUMN);
		
		
		//############################## Tab 4 ##############################
		
		Tab tab5 = new Tab(ColorPalette.WHITE, FlexDirection.COLUMN);
		
		String notHovering = "This is a multi-line text. It starts a new line if the text is too long to fit in only one line.";
		String hovering = "Hovering! Hovering! Hovering! Hovering! Hovering! Hovering!";
		
		GUITextField text = new GUITextField(notHovering, "FreeMono", 100f, 30f, 30);
		text.setMargin(Direction.ALL, 10);
		tab5.addChild(text);
		
		GUIButton button = new GUIButton(new GUIQuad(ColorPalette.BLUE), 100, 30);
		button.setMargin(Direction.ALL, 10);
		//button.addOnMouseEnterListener((Input input) -> text.setText(hovering));
		//button.addOnMouseLeaveListener((Input input) -> text.setText(notHovering));
		button.addOnClickListener((Input input) -> RenderEngine.takeScreenshot("screenshots/", "png"));
		tab5.addChild(button);
		
		
		//############################## TabMenu ##############################
		
		tabMenu.addDefaultTab("Shapes Demo", tab1);
		tabMenu.addTab("Radio Button Menu", tab2);
		tabMenu.addTab("Empty tab", tab3);
		tabMenu.addTab("Empty tab 2", tab4);
		tabMenu.addTab("TextTab", tab5);
		sq_window.addChild(tabMenu);
		
		
		double timer = glfwGetTime();
		int frames = 0;
		
		while (!KeyInputHandler.keyPressed(GLFW_KEY_ESCAPE)) {
			
			if (glfwGetTime() - timer >= 1) {
				System.out.println(frames);
				frames = 0;
				timer = glfwGetTime();
			}
			
			RenderEngine.clear();
			
			Renderer2D.beginFrame(window);
			GUIManager.processInput();
			GUIManager.render();
			glEnable(GL_DEPTH_TEST);
			
			mesh.render(scene);
			RenderEngine.swapBuffers();
			frames++;
		}
		
		GUIManager.delete();
		mesh.delete();
		Renderer2D.delete();
	}
	
	
	public static void main(String[] args) {
		initOpengl();
		initNVG();
		run();
	}

}
