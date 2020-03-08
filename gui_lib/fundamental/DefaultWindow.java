package fundamental;

import assets.meshes.geometry.Color;
import gui_core.Input;
import layout.IGUILayoutNode.Direction;
import layout.IGUILayoutNode.FlexDirection;
import rendering.shapes.implemented.GUIExitButton;
import rendering.shapes.implemented.GUIQuad;
import utils.ColorPalette;

public class DefaultWindow extends GUIWindow {
	
	private GUIContainer<Element> taskBar;
	private GUIContainer<Element> content;
	
	public DefaultWindow(String title, int x, int y, int width, int height, FlexDirection flexDirection) {
		super(new GUIQuad(ColorPalette.GRAY), x, y, width, height, FlexDirection.COLUMN);
		
		//Set up the task bar
		taskBar = new GUIContainer<Element>(new GUIQuad(ColorPalette.WHITE), width, 50, FlexDirection.ROW_REVERSE);
		
		Button exitButton = new Button(new GUIExitButton(ColorPalette.ZERO, ColorPalette.BLACK), 30, 30);
		exitButton.setMargin(Direction.ALL, 10);
		exitButton.addMouseEnterListener((Input input) -> exitButton.getShape().setColor(ColorPalette.GRAY));
		exitButton.addMouseLeaveListener((Input input) -> exitButton.getShape().setColor(ColorPalette.ZERO));
		exitButton.addLeftMouseButtonReleaseListener((Input input) -> this.close());
		
		//TextField titleField = new TextField(title, "FreeMono", 50, 30, 30);
		//titleField.setMargin(Direction.ALL, 10);
		
		taskBar.addChild(exitButton);
		//taskBar.addChild(titleField);
		taskBar.addDragListener((Input input) -> move(input.dx, input.dy));
		
		content = new InvisibleContainer<Element>(width, height - 50, flexDirection);
		
		super.addChild(taskBar);
		super.addChild(content);		
	}
	
	
	private void move(int dx, int dy) {
		x += dx;
		y += dy;
	}
	
	
	public void setTaskBarColor(Color color) {
		taskBar.getShape().setColor(color);
	}
	
	
	public void setContentAreaColor(Color color) {
		content.getShape().setColor(color);
	}


	@Override
	public void addChild(Element element) {
		content.addChild(element);
	}


	@Override
	public void removeChild(Element element) {
		content.removeChild(element);
	}


	@Override
	public void removeAllChildren() {
		content.removeAllChildren();
	}


	@Override
	public int getNumChildren() {
		return content.getNumChildren();
	}

}
