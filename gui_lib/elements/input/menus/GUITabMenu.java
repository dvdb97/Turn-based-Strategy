package elements.input.menus;

import java.util.ArrayList;

import elements.containers.GUIContainerElement;
import elements.containers.GUITab;
import elements.input.GUIToggleButton;
import math.vectors.Vector4f;
import rendering.shapes.GUIShape;
import rendering.shapes.implemented.GUIQuad;

public class GUITabMenu extends GUIContainerElement {
	
	private static int MAX_NUM_TABS = 4;
	
	private int currentTab;
	private ArrayList<GUITab> tabs;
	
	
	//************************* constructor **************************
	
	public GUITabMenu(GUIShape shape, Vector4f color, float x, float y, float width, float height) {
		super(shape, color, x, y, width, height);
		
		currentTab = -1;
		
	}
	
	
	
	//********************* tabs **********************************
	
	private void changeToTab(int index) {
		
		currentTab = index;
		
	}
	
	
	public void addTab(Vector4f color) {
		
		GUITab tab = new GUITab(new GUIQuad(), color, 0.1f, -0.4f, 0.8f, 0.5f);
		GUIToggleButton button = new GUIToggleButton(0.1f + getNumTabs()*0.2f, -0.1f, 0.2f, 0.2f, ""+getNumTabs());	//TODO: ugly string: ""+2
		addChild(button);
		tabs.add(tab);
		
		button.setEnableFunction(  (element) -> changeToTab(indexOfChild(element)) );
		button.setDisableFunction( (element) -> changeToTab(-1) );
		
	}
	
	//****************** render, update, process input ****************
	
	//TODO:
	/*
	 * We need an interface that contains render(), update() and processInput(), so everthing a parent(GUIElementContainer) wants from his children
	 * (now the childrens are object of ElementBase)
	 * This interface is neither clickable nor visible, it is just the interface between parents and children.
	 * This interface is needed to implement a collection, set, or something, to have an opportunity to organize a parent's children
	 * 
	 */
	
	@Override
	public void render() {
		
		if (super.isVisible()) {
			return;
		}
		
		super.render();
		
		tabs.get(currentTab).render();
		
	}
	
	
	@Override
	public void update() {
		
		super.update();
		tabs.get(currentTab).update();
		
	}
	
	@Override
	public boolean processInput(float cursorX, float cursorY, boolean leftMouseButtonDown, boolean rightMouseButtonDown) {
		
		if(super.processInput(cursorX, cursorY, leftMouseButtonDown, rightMouseButtonDown)) {
			return false;
		}
		
		tabs.get(currentTab).processInput(cursorX, cursorY, leftMouseButtonDown, rightMouseButtonDown);
		
		return true;
		
	}
	
	//************************ get & set ************************
	
	public int getNumTabs() {
		return tabs.size();
	}
	
	
	
}
