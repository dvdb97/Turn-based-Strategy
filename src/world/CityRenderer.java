package world;

import java.io.File;
import java.util.LinkedList;
import java.util.Random;

import assets.cameras.Camera;
import assets.light.DirectionalLight;
import assets.meshes.IRenderable;
import assets.meshes.Mesh3D;
import assets.meshes.fileLoaders.FileLoader;
import assets.scene.Scene;
import datastructures.Tuple;
import math.vectors.Vector3f;

public class CityRenderer implements IRenderable {
	
	private Mesh3D[] cityMeshes;
	private LinkedList<Tuple<Mesh3D, Vector3f>> cities;
	
	private Random random;
	
	
	/**
	 * 
	 * @param cityModels An array of models that will be randomly chosen to display a city.
	 */
	public CityRenderer(Mesh3D[] cityModels) {
		this.cityMeshes = cityModels;
		this.cities = new LinkedList<Tuple<Mesh3D, Vector3f>>();
		this.random = new Random();
	}
	
	
	/**
	 * 
	 * @param url The url of the folder containing all meshes.
	 */
	public CityRenderer(String url) {
		this(loadMeshes(url));
	}
	
	
	private static Mesh3D[] loadMeshes(String url) {
		File file = new File(url);
		String[] paths = file.list();
		Mesh3D[] meshes = new Mesh3D[paths.length];
		
		for (int i = 0; i < paths.length; ++i) {
			meshes[i] = new Mesh3D();
			FileLoader.loadObjFile(meshes[i], paths[i]);
		}
		
		return meshes;
	}
	
	
	/**
	 * 
	 * Add a new city to this renderer reusing a random mesh from the cityModels
	 * array.
	 * 
	 * @param position The position of the city in world space.
	 */
	public void addCity(Vector3f position) {
		//Select a random mesh from the array of city models.
		cities.add(new Tuple<Mesh3D, Vector3f>(cityMeshes[random.nextInt(cityMeshes.length)], position));
	}
	

	@Override
	public void render() {
		for (Tuple<Mesh3D, Vector3f> city : cities) {
			city.x.transformable.setTranslation(city.y);
			city.x.render();
		}
	}
	

	@Override
	public void render(Camera camera, DirectionalLight light) {
		for (Tuple<Mesh3D, Vector3f> city : cities) {
			city.x.transformable.setTranslation(city.y);
			city.x.render(camera, light);
		}
	}
	

	@Override
	public void render(Scene scene) {
		for (Tuple<Mesh3D, Vector3f> city : cities) {
			city.x.transformable.setTranslation(city.y);
			city.x.render(scene);
		}
	}

}
