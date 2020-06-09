package world.estate;

import java.util.function.Consumer;

public abstract class Estate {
	
	protected Consumer<? super Estate> func;
	
	//something that can be placed on a tile
	//only one estate per tile
	//estates are: e.g. cities, mines, etc
	
	public abstract String getInfoString();
	
	public void setCallbackFunction(Consumer<? super Estate> func) {
		this.func = func;
	}
	
}
