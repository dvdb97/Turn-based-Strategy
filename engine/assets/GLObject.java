package assets;

public abstract class GLObject {
	
	private final int ID;
	
	private final int TYPE; 
	
	
	public GLObject(int id, int type) {
		
		this.ID = id;
		
		this.TYPE = type;
		
	}
	
	public abstract void bind();
	
	public abstract void unbind();
	
	public int getID() {
		return ID;
	}
	
	public int getType() {
		return TYPE;
	}
	
	public abstract void delete();

}
