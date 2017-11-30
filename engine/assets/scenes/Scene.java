package assets.scenes;


import java.util.LinkedList;
import assets.light.LightSource;
import assets.models.Illuminated_Model;
import assets.models.abstractModels.Renderable;


public class Scene implements Renderable {
	
	
	private LinkedList<Illuminated_Model> models;
	
	private LinkedList<LightSource> lights;
	
	
	public Scene() {
		
		models = new LinkedList<Illuminated_Model>();
		
		lights = new LinkedList<LightSource>();
		
	}
	
	
	public Scene(Illuminated_Model[] models, LightSource[] lights) {
		
		this();
		
		for (Illuminated_Model model : models) {
			
			this.models.add(model);
			
		}
		
		
		for (LightSource light : lights) {
			
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
	
	
	public void addLightSource(LightSource light) {
		
		lights.add(light);
		
	}
	
	
	public void removeLightSource(LightSource light) {
		
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
	
	
	public LinkedList<LightSource> getLights() {
		return lights;
	}

}
