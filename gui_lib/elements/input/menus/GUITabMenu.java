package elements.input.menus;

import java.util.ArrayList;

import elements.containers.GUIContainerElement;
import elements.containers.GUITab;
import elements.input.buttons.GUIToggleButton;
import math.vectors.Vector4f;
import rendering.shapes.GUIShape;
import rendering.shapes.implemented.GUIQuad;

public class GUITabMenu extends GUIContainerElement {
	
	private static int MAX_NUM_TABS = 4;
	
	private int currentTab;
	private ArrayList<GUITab> tabs;
	private ArrayList<GUIToggleButton> buttons;
	
	//************************* constructor **************************
	
	public GUITabMenu(GUIShape shape, Vector4f color, float x, float y, float width, float height) {
		super(shape, color, x, y, width, height);
		
		tabs = new ArrayList<>();
		buttons = new ArrayList<GUIToggleButton>();
		currentTab = -1;
		
	}
	
	
	
	//********************* tabs **********************************
	
	private void changeToTab(int index) {
		
		toggleCurrentButton();
		
		if(index >= 0 && index < MAX_NUM_TABS) {
			addChild(tabs.get(index));
		}
		currentTab = index;
		
	}
	
	private void changeToNoTab() {
		
		if(currentTab >= 0 && currentTab < MAX_NUM_TABS) {
			removeChild(tabs.get(currentTab));
		}
		currentTab = -1;
		
		
	}
	
	private void toggleCurrentButton() {
		
		if(currentTab >= 0 && currentTab < MAX_NUM_TABS) {
			buttons.get(currentTab).toggle();
		}
		
	}
	
	public void addTab(Vector4f color) {
		
		if(getNumTabs() >= MAX_NUM_TABS) {
			return;
		}
		
		GUITab tab = new GUITab(new GUIQuad(), color, 0.1f, -0.4f, 0.8f, 0.5f);
		tabs.add(tab);
		
		GUIToggleButton button = new GUIToggleButton(0.1f + buttons.size()*0.2f, -0.1f, 0.2f, 0.2f, ""+buttons.size());	//TODO: ugly string: ""+2
		buttons.add(button);
		button.setEnableFunction(  (element) -> changeToTab(buttons.indexOf(element)) );
		button.setDisableFunction( (element) -> changeToNoTab() );
		
		addChild(button);
		
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
		
		if (!super.isVisible()) {
			return;
		}
		
		super.render();
		
	//	if(currentTab != -1)
	//		tabs.get(currentTab).render();
		
	}
	
	
	@Override
	public void update() {
		
		super.update();
		
	//	if(currentTab != -1)
	//		tabs.get(currentTab).update();
		
	}
	
	@Override
	public boolean processInput(float cursorX, float cursorY, boolean leftMouseButtonDown, boolean rightMouseButtonDown) {
		
		if(super.processInput(cursorX, cursorY, leftMouseButtonDown, rightMouseButtonDown)) {
			return false;
		}
		
	//	if(currentTab != -1)
	//		tabs.get(currentTab).processInput(cursorX, cursorY, leftMouseButtonDown, rightMouseButtonDown);
		
		return true;
		
	}
	
	//************************ get & set ************************
	
	public int getNumTabs() {
		return tabs.size();
	}
	
	
	
}
