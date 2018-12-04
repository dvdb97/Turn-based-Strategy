package dataType;

import java.util.ArrayList;

import fundamental.ElementBase;
import graphics.matrices.TransformationMatrix;

public class ElementList extends ArrayList<ElementBase> implements ElementBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1285077944138307000L;
	
	public ElementList() {
		super();
	}
	
	public ElementList(int initialCapacity) {
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
