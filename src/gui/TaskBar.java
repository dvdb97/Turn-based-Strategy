package gui;

import fundamental.GUIButton;
import fundamental.GUIWindow;
import gameplay.TaskBarManager;
import implementations.GUINumberField;
import layout.IGUILayoutNode.Direction;
import layout.IGUILayoutNode.FlexDirection;
import rendering.shapes.implemented.GUIQuad;
import utils.ColorPalette;

public class TaskBar extends GUIWindow {
	
	private static GUINumberField turnCountText;
	private static GUINumberField cashText;
	private static GUINumberField gainText;
	
	public TaskBar() {
		super(new GUIQuad(ColorPalette.DARK_SLATE_GRAY), 0, 0, 400, 60, FlexDirection.ROW);
		
		int blockHeight = 80;
		int blockWidth  = 50;
		int delta = 5;
		
		int initialTurnCounter = 1;
		int initialCash = 100;
		int initialGain = 10;
		
		GUIButton button = new GUIButton(new GUIQuad(ColorPalette.GREEN_1), blockHeight, blockWidth);
		button.setLabel("Next Turn", "FreeMono", 20);
		button.setMargin(Direction.ALL, delta);
		button.setPadding(Direction.ALL, delta);
		button.addOnClickListener((e) -> {
			TaskBarManager.startNextTurn();
		});
		
		turnCountText = new GUINumberField("Turn: ", initialTurnCounter, "FreeMono", ColorPalette.GIANTS_ORANGE, blockHeight, blockWidth, 20);
		turnCountText.setMargin(Direction.ALL, delta);
		turnCountText.setPadding(Direction.ALL, delta);
		
		cashText = new GUINumberField("Cash: ", initialCash, "FreeMono", ColorPalette.GIANTS_ORANGE, blockHeight, blockWidth, 20);
		cashText.setMargin(Direction.ALL, delta);
		cashText.setPadding(Direction.ALL, delta);
		
		gainText = new GUINumberField("Gain: ", initialGain, "FreeMono", ColorPalette.GIANTS_ORANGE, blockHeight, blockWidth, 20);
		gainText.setMargin(Direction.ALL, delta);
		gainText.setPadding(Direction.ALL, delta);
		
		addChild(button);
		addChild(turnCountText);
		addChild(cashText);
		addChild(gainText);
		
	}
	
	public GUINumberField getTurnCountText() {
		return turnCountText;
	}
	
	public GUINumberField getCashText() {
		return cashText;
	}
	
	public GUINumberField getGainText() {
		return gainText;
	}
	
}
