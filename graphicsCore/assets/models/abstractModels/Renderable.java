package assets.models.abstractModels;

public interface Renderable {
	
	public abstract void render();
	
	public abstract void activateAttributes();
	
	public abstract void deactivateAttributes();
	
	public abstract void onDrawStart();
	
	public abstract void onDrawStop();

}
