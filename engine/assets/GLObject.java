package assets;

public abstract class GLObject implements Bindable {
	
	private final int ID;
	
	public GLObject(int id) {
		this.ID = id;
	}
	
	public int getID() {
		return ID;
	}

}
