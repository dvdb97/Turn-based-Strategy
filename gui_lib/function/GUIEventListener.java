package function;

import gui_core.Input;

public interface GUIEventListener {
	
	void execute(Input input);
	
	default GUIEventListener andThen(GUIEventListener function) {
		return (Input input) -> {this.execute(input);function.execute(input);};
	}

}
