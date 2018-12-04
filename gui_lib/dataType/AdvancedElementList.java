package dataType;

import java.util.ArrayList;

import fundamental.Element;
import fundamental.ElementBase;

//TODO: shitty name
public class AdvancedElementList<E extends Element> extends ArrayList<E> implements ElementBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4704056744142035069L;
	
	
	public AdvancedElementList(int initialCapacity) {
		super(initialCapacity);
	}
	
	@Override
	public void update(GUIElementMatrix parentMatrix) {
		
		forEach((e) -> e.update(parentMatrix));
		
	}

	@Override
	public void render() {
		
		forEach((e) -> e.render());
		
	}
	
	@Override
	public boolean processInput() {
		
	//	forEach((e) -> e.processInput());
		
		for(ElementBase e : this) {
			if (e.processInput())
				return true;
		}
		
		return false;
	}

}
