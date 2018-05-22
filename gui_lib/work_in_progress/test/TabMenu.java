package work_in_progress.test;

import java.util.ArrayList;

import assets.meshes.geometry.Color;
import elements.containers.GUITab;
import elements.input.buttons.GUIToggleButton;
import math.vectors.Vector4f;
import rendering.shapes.GUIShape;
import rendering.shapes.implemented.GUIQuad;
import work_in_progress.AdvancedElementList;
import work_in_progress.Container;
import work_in_progress.ElementList;
import work_in_progress.GUIElementMatrix;
import work_in_progress.Quad;
import work_in_progress.Shape;
import work_in_progress.ToggleButton;

public class TabMenu extends Container {

	private static int MAX_NUM_TABS = 4;
	
	private int currentTab;
	private ArrayList<Tab> tabs;
	private AdvancedElementList<ToggleButton> buttons;
	
	
	
	//************************* constructor **************************
	
	public TabMenu(Shape shape, Color color, GUIElementMatrix transformationMatrix) {
		super(shape, color, transformationMatrix);
		
		tabs = new ArrayList<>(MAX_NUM_TABS);
		buttons = new AdvancedElementList<>(MAX_NUM_TABS);
		currentTab = -1;
		
		children.add(buttons);
		
	}

	
	
	//********************* tabs **********************************
	
	public void addTab(Color color) {
		
		if(getNumTabs() >= MAX_NUM_TABS) {
			return;
		}
		
		Tab tab = new Tab(new Quad(), color, new GUIElementMatrix(0.1f, -0.4f, 0.8f, 0.5f));
		tabs.add(tab);
		
		TestToggleButton button = new TestToggleButton(color, new GUIElementMatrix(0.1f + buttons.size()*0.2f, -0.1f, 0.2f, 0.2f));
		buttons.add(button);
		button.setEnableFunc(  (element) -> changeToTab(buttons.indexOf(element)) );
		button.setDisableFunc( (element) -> changeToNoTab() );
		
	}
	
	
	private void changeToTab(int index) {
		
		toggleCurrentButton();
		
		if(index >= 0 && index < MAX_NUM_TABS) {
			children.add(tabs.get(index));
		}
		currentTab = index;
		
	}
	
	
	private void changeToNoTab() {
		
		if(currentTab >= 0 && currentTab < MAX_NUM_TABS) {
			children.remove(tabs.get(currentTab));
		}
		currentTab = -1;
		
		
	}
	
	
	private void toggleCurrentButton() {
		
		if(currentTab >= 0 && currentTab < MAX_NUM_TABS) {
			buttons.get(currentTab).toggle();
		}
		
	}
	
	
	
	//************************ get & set ************************
	
	public int getNumTabs() {
		return tabs.size();
	}
	
	
	
}