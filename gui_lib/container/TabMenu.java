package container;

import java.util.ArrayList;

import assets.meshes.geometry.Color;
import dataType.AdvancedElementList;
import dataType.GUIElementMatrix;
import fundamental.Container;
import input.ToggleButton;
import rendering.shapes.GUIQuad;
import rendering.shapes.Shape;
import stbFont.TTFBox;
import work_in_progress.TestToggleButton;

import static utils.ColorPalette.*;

public class TabMenu extends Container {

	private static int MAX_NUM_TABS = 4;
	private static final int NO_TAB = -1;
	
	private int currentTab;
	private ArrayList<Tab> tabs;
	private AdvancedElementList<ToggleButton> buttons;
	
	private float buttonHeight, buttonWidth, buttonOffsetX, buttonOffsetY;
	
	//************************* constructor **************************
	
	public TabMenu(Color color, float buttonHeight, GUIElementMatrix transformationMatrix) {
		super(color, transformationMatrix);
		
		this.buttonHeight = buttonHeight;
		buttonWidth = 1f/MAX_NUM_TABS;
		buttonOffsetX = 0f;
		buttonOffsetY = -0f;
		
		tabs = new ArrayList<>(MAX_NUM_TABS);
		buttons = new AdvancedElementList<>(MAX_NUM_TABS);
		currentTab = NO_TAB;
		
		children.add(buttons);
		
	}

	
	
	//********************* tabs **********************************
	
	public boolean addTab(Color color, String label, ArrayList<Tab> tabList) {
		
		if(getNumTabs() >= MAX_NUM_TABS) {
			return false;
		}
		
		Tab tab = new Tab(GRAY, new GUIElementMatrix(buttonOffsetX, buttonOffsetY-buttonHeight, 1f, 1f+buttonOffsetY-buttonHeight));
		tabs.add(tab);
		tabList.add(tab);
		
		TestToggleButton button = new TestToggleButton(color, new GUIElementMatrix(buttonOffsetX + buttons.size()*buttonWidth, buttonOffsetY, buttonWidth, buttonHeight));
		TTFBox buttonLabel = new TTFBox(buttonOffsetX + buttons.size()*buttonWidth, buttonOffsetY, 0.03f, label, BLACK);
		buttons.add(button);
		children.add(buttonLabel);
		button.setEnableFunc(  (element) -> changeToTab(buttons.indexOf(element)) );
		button.setDisableFunc( (element) -> changeToNoTab() );
		return true;
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