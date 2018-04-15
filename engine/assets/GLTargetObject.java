package assets;

public abstract class GLTargetObject extends GLObject {
	
	private final int TYPE; 
	
	public GLTargetObject(int id, int type) {
		super(id);
		this.TYPE = type;
	}

	public int getType() {
		return TYPE;
	}
	
}
