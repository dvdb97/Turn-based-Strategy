package gui;

import fundamental.DefaultWindow;
import fundamental.GUIButton;
import fundamental.GUITextField;
import interaction.TileSelecter;
import layout.IGUILayoutNode.FlexDirection;
import rendering.shapes.implemented.GUIQuad;
import utils.ColorPalette;
import world.AgentAuthority;
import world.agents.Agent;
import world.gameBoard.GameBoard;

public class AgentInfoWindow extends DefaultWindow {
	
	private Agent agent;
	private String agentInfoString;
	private GUITextField text;
	private GUIButton button1;
	private GUIButton button2;
	
	//**************************** init *************************************
	public AgentInfoWindow(Agent agent) {
		super("Agent Information", 610, 100, 300, 300, FlexDirection.ROW);
							
		text = new GUITextField("Dummy", "FreeMono", 90f, 20f, 20);
//		text.setLocalXPosition(50f);
		text.setLocalYPosition(50f);
		
		button1 = new GUIButton(new GUIQuad(agent.getColor()), 30f, 20f);
		button1.setLabel("Move Agent", "FreeMono", 20);
		button1.setLocalYPosition(15f);
		button1.addOnClickListener((e) -> {
			AgentAuthority.requestToMoveAgent(this.agent, TileSelecter.getSelectedTileIndex());
			}
		);
		
		button2 = new GUIButton(new GUIQuad(agent.getColor()), 30f, 20f);
		button2.setLabel("Remove Agent", "FreeMono", 20);
		button2.setLocalYPosition(15f);
		button2.addOnClickListener((e) -> {
			AgentAuthority.deleteAgent(this.agent);
			}
		);
		
		addChild(text);
		addChild(button1);
		addChild(button2);
		
		changeAgent(agent);
		
	}
	
	public void changeAgent(Agent agent) {
		
		this.agent = agent;
		
		if (agent==null) {
			agentInfoString = "Agent not found.";
		} else {
			agentInfoString = agent.getAgentInfoString();
		}

		text.setText(agentInfoString);
		button1.setShape(new GUIQuad(agent.getColor()));
		button2.setShape(new GUIQuad(agent.getColor()));
	}
}
