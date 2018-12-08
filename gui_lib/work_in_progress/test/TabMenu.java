package work_in_progress.test;

import java.util.ArrayList;

import assets.meshes.geometry.Color;
import dataType.AdvancedElementList;
import dataType.GUIElementMatrix;
import fundamental.Container;
import input.ToggleButton;
import rendering.shapes.GUIQuad;
import rendering.shapes.Shape;
import stbFont.TTFBox;

import static utils.ColorPalette.*;

public class TabMenu extends Container {

	private static int MAX_NUM_TABS = 4;
	private static final int NO_TAB = -1;
	
	private int currentTab;
	private ArrayList<Tab> tabs;
	private AdvancedElementList<ToggleButton> buttons;
	
	
	
	//************************* constructor **************************
	
	public TabMenu(Color color, GUIElementMatrix transformationMatrix) {
		super(color, transformationMatrix);
		
		tabs = new ArrayList<>(MAX_NUM_TABS);
		buttons = new AdvancedElementList<>(MAX_NUM_TABS);
		currentTab = NO_TAB;
		
		children.add(buttons);
		
	}

	
	
	//********************* tabs **********************************
	
	public void addTab(Color color, String label, ArrayList<Tab> tabList) {
		
		if(getNumTabs() >= MAX_NUM_TABS) {
			return;
		}
		
		Tab tab = new Tab(WHITE, new GUIElementMatrix(0.1f, -0.4f, 0.8f, 0.5f));
		tabs.add(tab);
		tabList.add(tab);
		
		TestToggleButton button = new TestToggleButton(color, new GUIElementMatrix(0.1f + buttons.size()*0.2f, -0.1f, 0.2f, 0.2f));
		TTFBox buttonLabel = new TTFBox(0.1f + buttons.size()*0.2f, -0.1f, 0.04f, label, BLACK);
		buttons.add(button);
		children.add(buttonLabel);
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
		
		if(currentTab != NO_TAB && currentTab < MAX_NUM_TABS) {
			children.remove(tabs.get(currentTab));
		}
		currentTab = -1;
		
		
	}
	
	
	private void toggleCurrentButton() {
		
		if(currentTab != NO_TAB && currentTab < MAX_NUM_TABS) {
			buttons.get(currentTab).toggle();
		}
		
	}
	
	
	
	//************************ get & set ************************
	
	public int getNumTabs() {
		return tabs.size();
	}
	
	
	
}