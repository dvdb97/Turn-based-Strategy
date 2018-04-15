package assets;

public abstract class GLObject {
	
	private final int ID;
	
	
	public GLObject(int id) {
		this.ID = id;
	}
	
	
	public int getID() {
		return ID;
	}
	
	public abstract void bind();
	
	public abstract void unbind();
	
	public abstract void delete();

}
