package container;

import assets.meshes.geometry.Color;
import fundamental.GUIContainer;
import fundamental.GUIElement;
import fundamental.InvisibleContainer;
import input.buttons.OptionSet;
import input.buttons.ToggleButton;
import layout.IGUILayoutNode.FlexDirection;
import rendering.shapes.implemented.GUIQuad;
import utils.ColorPalette;

public class TabMenu extends GUIContainer<GUIElement> {
	
	//The buttons to choose the tabs.
	private OptionSet<ToggleButton> buttons;
	
	//The container that will contain the tabs.
	private GUIContainer<Tab> tabContainer;
	
	//The color of the tabs that will be added to this TabMenu.
	private Color tabColor;
	
	
	/**
	 * 
	 * @param width The width of the element in pixels.
	 * @param height The height of the element in pixels.
	 * @param flexDirection Specifies the layout of this Container. In this case it sets the buttons
	 * to be horizontal or vertical.
	 * @param tabColor The color of the tabs that will be added to this TabMenu.
	 */
	public TabMenu(int width, int height, FlexDirection flexDirection, Color tabColor) {
		super(new GUIQuad(ColorPalette.ZERO), width, height, flexDirection, 2);
		init(tabColor);
	}
	
	
	/**
	 * 
	 * @param widthPercent The width of the element in relation to the parent.
	 * @param heightPercent The height of the element in relation to the parent.
	 * @param flexDirection Specifies the layout of this Container. In this case it sets the buttons
	 * to be horizontal or vertical.
	 * @param tabColor The color of the tabs that will be added to this TabMenu.
	 */
	public TabMenu(float widthPercent, float heightPercent, FlexDirection flexDirection, Color tabColor) {
		super(new GUIQuad(ColorPalette.ZERO), widthPercent, heightPercent, flexDirection, 2);
		init(tabColor);
	}
	
	
	private void init(Color tabColor) {
		if (getFlexDirection() == FlexDirection.COLUMN) {
			buttons = new OptionSet<ToggleButton>(100f, 10f, FlexDirection.ROW);
			tabContainer = new InvisibleContainer<Tab>(100f, 90f, FlexDirection.ROW);
		} else {
			buttons = new OptionSet<ToggleButton>(10f, 100f, FlexDirection.COLUMN);
			tabContainer = new InvisibleContainer<Tab>(90f, 100f, FlexDirection.ROW);
		}
		
		addChild(buttons);
		addChild(tabContainer);
		
		this.tabColor = tabColor;
	}
	
	
	private ToggleButton createButton(String label, Tab tab) {
		ToggleButton button;
		
		float newSize = 100f / (getNumTabs() + 1);
		
		if (getFlexDirection() == FlexDirection.COLUMN) {
			button = new ToggleButton(new GUIQuad(ColorPalette.DARK_SLATE_GRAY), newSize, 100f);
			
			//Rescale all buttons.
			buttons.getChildren().forEach((e) -> e.setWidth(newSize));
		} else {						
			button = new ToggleButton(new GUIQuad(ColorPalette.DARK_SLATE_GRAY), 100f, newSize);
			
			//Rescale all buttons.
			buttons.getChildren().forEach((e) -> e.setHeight(newSize));
		}
		
		button.setLabel(label, "FreeMono", 20);

		button.addOnMouseEnterListener((e) -> button.getShape().setColor(ColorPalette.GRAY));
		button.addOnMouseLeaveListener((e) -> {
			if (!button.isEnabled())
				button.getShape().setColor(ColorPalette.DARK_SLATE_GRAY);
		});
		button.addEnableListener((e) -> changeTab(tab));	
		button.addEnableListener((e) -> button.getShape().setColor(tabColor));
		button.addDisableListener((e) -> button.getShape().setColor(ColorPalette.DARK_SLATE_GRAY));
		
		return button;
	}
	
	
	/**
	 * 
	 * Add a new tab to the TabMenu.
	 * 
	 * @param label The label for the new tab.
	 * @param tab The new tab to add.
	 */
	public void addTab(String label, Tab tab) {		
		buttons.addButton(createButton(label, tab));
	}
	
	
	/**
	 * 
	 * Add a new tab to the TabMenu as define it as the default tab making
	 * it the first tab to be displayed without any input of the user.
	 * 
	 * @param label The label for the new tab.
	 * @param tab The new tab to add.
	 */
	public void addDefaultTab(String label, Tab tab) {		
		buttons.addDefaultButton(createButton(label, tab));
	}
	
	
	private void changeTab(Tab tab) {
		tabContainer.removeAllChildren();
		tabContainer.addChild(tab);
	}
	
	
	/**
	 * 
	 * @return Returns the number of tabs to choose from.
	 */
	public int getNumTabs() {
		return buttons.getNumChildren();
	}
	
}