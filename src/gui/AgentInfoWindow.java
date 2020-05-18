package gui;

import fundamental.DefaultWindow;
import fundamental.GUIButton;
import fundamental.GUITextField;
import interaction.TileSelecter;
import layout.IGUILayoutNode.FlexDirection;
import rendering.shapes.implemented.GUIQuad;
import utils.ColorPalette;
import world.agents.Agent;
import world.gameBoard.GameBoard;

public class AgentInfoWindow extends DefaultWindow {
	
	private Agent agent;
	private String agentInfoString;
	
	//**************************** init *************************************
	public AgentInfoWindow(Agent agent) {
		super("Agent Information", 610, 100, 300, 300, FlexDirection.COLUMN);
		
		this.agent = agent;
		
		if (agent==null) {
			agentInfoString = "Agent not found.";
		} else {
			agentInfoString = agent.getAgentInfoString();
		}
				
		GUITextField text = new GUITextField(agentInfoString, "FreeMono", 90f, 20f, 20);
		text.setLocalXPosition(50f);
		text.setLocalYPosition(50f);
		GUIButton button = new GUIButton(new GUIQuad(ColorPalette.GRAY), 30f, 20f);
		button.setLabel("Move Agent", "FreeMono", 20);
		button.setLocalXPosition(50f);
		button.addOnClickListener((e) -> {
			;
			}
		);
		
		addChild(text);
		addChild(button);
	}
	
}
