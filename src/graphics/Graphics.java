package graphics;

import static org.lwjgl.glfw.GLFW.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;

import javax.xml.stream.events.StartDocument;

import interaction.Window;
import lwlal.Matrix44f;
import lwlal.Vector3f;
import lwlal.Vector4f;
import lwlal_advanced.MatrixManager;
import lwlal_advanced.TransformationMatrix;
import assets.light.LightSource;
import assets.material.Material;
import assets.material.StandardMaterial;
import assets.meshes.fileLoaders.OBJ_FileLoader;
import assets.meshes.geometry.Color;
import assets.models.*;
import assets.textures.Texture2D;
import entities.Camera;
import gui.GUI;
import gui.TestWindow;
import models.HexagonBorderMesh;
import models.HexagonBorders;
import models.TerrainCol;
import models.TriangleMesh;
import package1.GameState;
import package1.Input;
import rendering.RenderEngine;
import rendering.shaders.ShaderProgram;
import rendering.shaders.standardShaders.lightShader.LightShader;
import terrain.Generator;
import terrain.LogFunction;
import terrain.Terrain;
import utils.ColorFunction;
import visualize.CoordinateSystem;

public class Graphics {
	
	
	private ShaderProgram shader;	
	private LightShader lightShader;
	
	private LightSource light;
	
	private Window window;
	
	
	private Element_Model monkey;
	
	//TODO: temp
	private final int LENGTH = 512, WIDTH = 256;
	private Terrain terrain;
	
	private TriangleMesh triangleMesh;
	private HexagonBorderMesh hexagonBorderMesh;
	private TriangleMesh sea;
	
	private Material terrainMat;
	private Material seaMat;
	
	private CoordinateSystem coordinateSystem;
	
	//transformation matrices
	private TransformationMatrix TMTM;		//triangle mesh transformation matrix
	
	private TestWindow test;
	
	private Texture2D testTex;
	
	//TODO: maybe temporary
	private int shiftSensitivity = 40;
	
	public Graphics() {
		
		window = new Window();
		window.createWindowedWindow("Hello World!");
		
		utils.GeometryUtils.setPixelWH(window.getWidth(), window.getHeight());
		
		RenderEngine.init(window);
		RenderEngine.setSwapInterval(1);
		RenderEngine.enableDepthTest();
		RenderEngine.setClearColor(new Vector4f(0.2f, 0.4f, 1, 1));
		
		loadData();
		loadGUI();
		
		MatrixManager.createTMTM();
		TMTM = new TransformationMatrix();
		
		//TODO: temporary? added by jona 24.06.
		logics.Logics.setRXY(window.getProportions());
		
	}
	
	float tick = 0f;
	
	//This function loads all models for the render engine: 
	public void loadData() {
		
		monkey = OBJ_FileLoader.loadOBJ_File("Models/Monkey.obj", new StandardMaterial());
		
		testTex = new Texture2D("Fonts/font.jpg", 2, GL_NEAREST_MIPMAP_NEAREST, GL_REPEAT);
		
		coordinateSystem = new CoordinateSystem(100f);
		
		lightShader = LightShader.createPerFragmentLightShader();
		light = new LightSource(new Vector3f(0.0f, 0.0f, 1.0f), new Vector3f(1.0f, 1.0f, 1.0f));
		
		shader = new ShaderProgram("Shaders/shader.vert", "Shaders/shader.frag");
		
		
		terrain = new Terrain(LENGTH, WIDTH);
		LogFunction gen = new LogFunction();
		
		Vector3f stdVector = new Vector3f(1.0f, 1.0f, 1.0f);
		terrainMat = new Material(stdVector, stdVector, stdVector, new Vector3f(0.0f, 0.0f, 0.0f), 1.0f);
		seaMat = new StandardMaterial();
		
		triangleMesh = new TriangleMesh(0.1f, terrain.getElevationArray(), new TerrainCol(), terrainMat);
		
		hexagonBorderMesh = new HexagonBorderMesh(triangleMesh, 0, 0, 1);
		
		//sea TODO: temp
		float[][] seaLevel = new float[LENGTH][WIDTH];
		for (int x=0; x<LENGTH; x++) {
			for (int y=0; y<WIDTH; y++) {
				seaLevel[x][y] = 0;
			}
		}
		
		ColorFunction seaCol = new ColorFunction() {
			
			@Override
			public Color color(int x, int y, float height) {
				return new Color(0, 0.2f, 0.7f, 0.7f);
			}
		};
		
		sea = new TriangleMesh(0.1f, seaLevel, seaCol, seaMat);
		
	}
	
	
	public void loadGUI() {
		GUI.init(window);		
		test = new TestWindow("Textures/GUI/BasicWindow.jpg", 0.2f, 0.2f, 0.2f, 0.2f);
		test.setVisible(true);
		test.setMovable(true);
	}
	
	
	public void close() {
		RenderEngine.close();
		window.close();
		
		triangleMesh.delete();
		hexagonBorderMesh.delete();
		sea.delete();
	}
	
	
	
	public boolean exitRequested() {
		return window.keyPressed(GLFW_KEY_ESCAPE);
	}
	
	
	public void displayGame() {
		RenderEngine.clear();
		
		tick += 0.0005f;
		
		glLineWidth(GameState.zoom * 3.0f + 1.0f);
		
		
		Matrix44f mvpMatrix = RenderEngine.getProjectionMatrix().times(RenderEngine.getViewmatrix()).times(TMTM);
		
		
		RenderEngine.refreshViewMatrix();
		
		
		//Draw illuminated models:
		lightShader.use();
			lightShader.prepareForRendering(TMTM, RenderEngine.getViewmatrix(), RenderEngine.getProjectionMatrix(), Camera.getPosition(), light, new StandardMaterial());
		
			RenderEngine.draw(triangleMesh, null);			
			RenderEngine.draw(sea, null);
			
			//RenderEngine.draw(monkey, null);
		
		lightShader.disable();
		
		
		//Draw not illuminated models:
		shader.use();
		
			shader.setUniformMatrix4fv("mvpMatrix", mvpMatrix.toArray());
		
			//RenderEngine.draw(hexagonBorderMesh, null);
			
			RenderEngine.draw(coordinateSystem, null);
		
		shader.disable();
		
		
		test.processMouseInput((float)window.getMouseXPos(), (float)window.getMouseYPos(), window.isMouseLeftClicked());
		
		//GUI.draw(test);
		
		RenderEngine.swapBuffers();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void handleUserInput() {
		
		//short: resetAll();
		Input.resetMouseBooleans();
		Input.resetWASDBooleans();
		Input.resetHotkeyBooleans();
		Input.resetArrowBooleans();
		
		double mouseX = window.getMouseXPos();
		double mouseY = window.getMouseYPos();
		
		//tileInformationWindow.handleUserInput(window.getNormalizedCursorXPos(), window.getNormalizedCursorYPos(), false);
		
		Input.mousePosX = (int) mouseX;
		Input.mousePosY = (int) mouseY;
		
		if (mouseX < window.getWidth() / shiftSensitivity) {
			Input.mouseAtLeftEdge = true;
		}
		if (mouseX > window.getWidth() * (1.0 - 1.0 / shiftSensitivity)) {
			Input.mouseAtRightEdge = true;
		}
		if (mouseY < window.getWidth() / shiftSensitivity) {
			Input.mouseAtUpperEdge = true;
		}
		if (mouseY > window.getWidth() * (1.0 - 1.0 / shiftSensitivity)) {
			Input.mouseAtLowerEdge = true;
		}
		if (window.isMouseLeftClicked()) {
			Input.mouseLeftClick = true;
		}
		if (window.isMouseRightClicked()) {
			Input.mouseRightClick = true;
		}
		
		if (window.keyPressed(GLFW_KEY_W)) {
			Input.wPressed = true;
		}
		if (window.keyPressed(GLFW_KEY_A)) {
			Input.aPressed = true;
		}
		if (window.keyPressed(GLFW_KEY_S)) {
			Input.sPressed = true;
		}
		if (window.keyPressed(GLFW_KEY_D)) {
			Input.dPressed = true;
		}
		if (window.keyPressed(GLFW_KEY_R)) {
			Input.rPressed = true;
		}
		if (window.keyPressed(GLFW_KEY_F)) {
			Input.fPressed = true;
		}
		if (window.keyPressed(GLFW_KEY_T)) {
			Input.tPressed = true;
		}
		if (window.keyPressed(GLFW_KEY_G)) {
			Input.gPressed = true;
		}
		
		if (window.keyPressed(GLFW_KEY_C)) {
			Input.cPressed = true;
		}
		if (window.keyPressed(GLFW_KEY_V)) {
			Input.vPressed = true;
		}
		if (window.keyPressed(GLFW_KEY_M)) {
			Input.mPressed = true;
		}
		if (window.keyPressed(GLFW_KEY_B)) {
			Input.bPressed = true;
		}
		
		if (window.keyPressed(GLFW_KEY_LEFT)) {
			Input.leftArrow = true;
		}
		if (window.keyPressed(GLFW_KEY_RIGHT)) {
			Input.rightArrow = true;
		}
		if (window.keyPressed(GLFW_KEY_UP)) {
			Input.upArrow = true;
		}
		if (window.keyPressed(GLFW_KEY_DOWN)) {
			Input.downArrow = true;
		}
		
	}
	
}
