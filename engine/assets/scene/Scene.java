package assets.scene;


import java.util.LinkedList;
import assets.light.DirectionalLight;
import assets.models.Illuminated_Model;
import assets.models.abstractModels.Renderable;


public class Scene implements Renderable {
	
	private LinkedList<Illuminated_Model> models;
	
	private LinkedList<DirectionalLight> lights;
	
	
	public Scene() {
		
		models = new LinkedList<Illuminated_Model>();
		
		lights = new LinkedList<DirectionalLight>();
		
	}
	
	
	public Scene(Illuminated_Model[] models, DirectionalLight[] lights) {
		
		this();
		
		for (Illuminated_Model model : models) {
			
			this.models.add(model);
			
		}
		
		
		for (DirectionalLight light : lights) {
			
			this.lights.add(light);
			
		}
		
	}


	@Override
	public void render() {
		for (Renderable model : models) {
			model.onDrawStart();
			
			model.activateAttributes();
			
			model.render();
			
			model.deactivateAttributes();
			
			model.onDrawStop();
		}
	}


	@Override
	public void activateAttributes() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void deactivateAttributes() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onDrawStart() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onDrawStop() {
		
	}
	
	
	public void addLightSource(DirectionalLight light) {
		
		lights.add(light);
		
	}
	
	
	public void removeLightSource(DirectionalLight light) {
		
		lights.remove(light);
		
	}
	
	
	public void removeLightSource(int index) {
		
		lights.remove(index);
	
	}
	
	
	public void addModel(Illuminated_Model model) {
		
		models.add(model);
		
	}
	
	
	public void removeModel(Illuminated_Model model) {
		
		models.remove(models);
		
	}
	
	
	public void removeModel(int index) {
		
		models.remove(index);
		
	}
	
	
	public LinkedList<DirectionalLight> getLights() {
		return lights;
	}

}
