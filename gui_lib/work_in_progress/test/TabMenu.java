package work_in_progress.test;

import java.util.ArrayList;

import assets.meshes.geometry.Color;
import dataType.AdvancedElementList;
import dataType.GUIElementMatrix;
import fundamental.Container;
import input.ToggleButton;
import rendering.shapes.GUIQuad;
import rendering.shapes.Shape;

import static utils.ColorPalette.*;

public class TabMenu extends Container {

	private static int MAX_NUM_TABS = 4;
	
	private int currentTab;
	private ArrayList<Tab> tabs;
	private AdvancedElementList<ToggleButton> buttons;
	
	
	
	//************************* constructor **************************
	
	public TabMenu(Color color, GUIElementMatrix transformationMatrix) {
		super(color, transformationMatrix);
		
		tabs = new ArrayList<>(MAX_NUM_TABS);
		buttons = new AdvancedElementList<>(MAX_NUM_TABS);
		currentTab = -1;
		
		children.add(buttons);
		
	}

	
	
	//********************* tabs **********************************
	
	public void addTab(Color color, ArrayList<Tab> tabList) {
		
		if(getNumTabs() >= MAX_NUM_TABS) {
			return;
		}
		
		Tab tab = new Tab(WHITE, new GUIElementMatrix(0.1f, -0.4f, 0.8f, 0.5f));
		tabs.add(tab);
		tabList.add(tab);
		
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