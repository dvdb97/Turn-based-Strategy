package charts;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwGetTime;

import java.util.LinkedList;
import java.util.Random;

import assets.cameras.Camera;
import assets.cameras.Camera.ProjectionType;
import assets.material.Material;
import assets.meshes.geometry.Color;
import assets.scene.Scene;
import interaction.Window;
import interaction.input.KeyInput;
import math.vectors.Vector3f;
import output.charts.FunctionGraph2D;
import output.charts.Group;
import output.charts.PieChart;
import output.charts.graph.Graph;
import rendering.RenderEngine;
import rendering.RenderQueue;
import timer.Cooldown;

public class GraphTest {

	private static Window window;

	public static void main(String[] args) {
		window = new Window();
		window.createFullscreenWindow("Parenting Test");
		window.setKeyInputCallback(new KeyInput());
		
		RenderEngine.init(window);
		RenderEngine.enableDepthTest();
		RenderEngine.setSwapInterval(1);
		
		run3();
	}
	
	public static void run1() {		
		Camera camera = new Camera(new Vector3f(0f, 0f, 1f));
		Scene scene = new Scene(camera, null, null);
		
		FunctionGraph2D graph = new FunctionGraph2D(100);
		graph.setMaterial(new Material(Color.WHITE));
		
		FunctionGraph2D graph2 = new FunctionGraph2D(1000);
		graph2.setMaterial(new Material(Color.WHITE));
		graph2.transformable.setTranslation(0f, -0.5f, 0f);
		graph2.transformable.setScaling(-1f, 0.5f, 1f);
		
		Cooldown timer = new Cooldown(0.1f);
		Random random = new Random();
		
		while (!KeyInput.keyPressed(GLFW_KEY_ESCAPE)) {
			RenderEngine.clear();
			
			if (timer.isFinished()) {
				graph.setSample((float)random.nextFloat());
				graph2.setSample((float)Math.sin(0.2f * glfwGetTime()));
				timer.start();
			}
				
			graph.render(scene);
			graph2.render(scene);
			
			RenderEngine.swapBuffers();
		}
		
		graph.delete();
		graph2.delete();
	}
	
	
	public static void run2() {
		Camera camera = new Camera(new Vector3f(0f, 0f, 1f));
		Scene scene = new Scene(camera, null, null);
		
		LinkedList<Group> groups = new LinkedList<Group>();
		groups.add(new Group("A", new Color(0.2f, 0.2f, 0.2f, 1f), 1));
		groups.add(new Group("B", new Color(0.4f, 0.4f, 0.4f, 1f), 1));
		groups.add(new Group("C", new Color(0.6f, 0.6f, 0.6f, 1f), 1));
		groups.add(new Group("D", new Color(0.8f, 0.8f, 0.8f, 1f), 1));
		
		PieChart chart = new PieChart(groups);
		
		Cooldown timer = new Cooldown(0.2f);
		Random random = new Random();
		
		while (!KeyInput.keyPressed(GLFW_KEY_ESCAPE)) {
			RenderEngine.clear();
			
			if (timer.isFinished()) {
				float value = (float)random.nextGaussian() * 1f;
				
				if (value < -1f) {
					chart.incrementGroup("A");
				} else if (value < 0f && value > -1f) {
					chart.incrementGroup("B");
				} else if (value > 0f && value < 1f) {
					chart.incrementGroup("C");
				} else if (value > 1f) {
					chart.incrementGroup("D");
				}
			}
			
			chart.render(scene);
						
			RenderEngine.swapBuffers();
		}
		
		chart.delete();
	}
	
	
	public static void run3() {
		Camera camera = new Camera(ProjectionType.ORTHOGRAPHIC);
		RenderQueue renderQueue = new RenderQueue(new Scene(camera, null, null));
		
		Graph graph = new Graph();
		
		float camSpeed = 0.1f;
		KeyInput.addKeyEvent(GLFW_KEY_A, (key) -> camera.move(-camSpeed, 0f, 0f));
		KeyInput.addKeyEvent(GLFW_KEY_D, (key) -> camera.move(camSpeed, 0f, 0f));
		KeyInput.addKeyEvent(GLFW_KEY_W, (key) -> camera.move(0f, camSpeed, 0f));
		KeyInput.addKeyEvent(GLFW_KEY_S, (key) -> camera.move(0f, -camSpeed, 0f));
		KeyInput.addKeyEvent(GLFW_KEY_Q, (key) -> camera.zoom(camSpeed, camSpeed, 0f));
		KeyInput.addKeyEvent(GLFW_KEY_E, (key) -> camera.zoom(-camSpeed, -camSpeed, 0f));
		
		graph.addNode("A", -0.5f, 0.5f);
		graph.addNode("B", 0f, 0.5f);
		graph.addNode("C", 0.5f, 0.5f);
		graph.addNode("D", -0.5f, 0f);
		graph.addNode("E", 0f, 0f);
		graph.addNode("F", 0.5f, 0f);
		graph.addNode("G", -0.5f, -0.5f);
		graph.addNode("H", 0f, -0.5f);
		graph.addNode("I", 0.5f, -0.5f);
		
		graph.addEdge("A", "D");
		graph.addEdge("A", "E");
		graph.addEdge("B", "D");
		graph.addEdge("B", "E");
		graph.addEdge("B", "F");
		graph.addEdge("C", "E");
		graph.addEdge("D", "E");
		graph.addEdge("D", "G");
		graph.addEdge("E", "I");
		graph.addEdge("F", "G");
		graph.addEdge("F", "I");
		graph.addEdge("G", "H");
		
		renderQueue.addMeshes(graph.getMeshes());
		
		while (!KeyInput.keyPressed(GLFW_KEY_ESCAPE)) {
			RenderEngine.clear();
			KeyInput.pollEvents();
			
			renderQueue.render();
			
			RenderEngine.swapBuffers();
		}
		
		renderQueue.delete();
	}
	
}
