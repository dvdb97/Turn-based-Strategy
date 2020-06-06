package gui;

import fundamental.GUIButton;
import fundamental.GUIWindow;
import implementations.GUINumberField;
import layout.IGUILayoutNode.Direction;
import layout.IGUILayoutNode.FlexDirection;
import rendering.shapes.implemented.GUIQuad;
import utils.ColorPalette;

public class TurnGUI extends GUIWindow {
	
	GUINumberField turnCountText;
	GUINumberField cashText;
	GUINumberField gainText;
	
	public TurnGUI() {
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
			turnCountText.increaseNumber(1);
			cashText.increaseNumber(gainText.getNumber());
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
	
}
