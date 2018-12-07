package assets;

public abstract class Deletable {
	
	public abstract void delete();

	@Override
	protected void finalize() throws Throwable {
		//TODO:this.delete();
	}

}
