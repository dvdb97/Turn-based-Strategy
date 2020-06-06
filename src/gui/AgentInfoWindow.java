package gui;

import java.util.ArrayList;
import java.util.List;

import fundamental.DefaultWindow;
import fundamental.GUIButton;
import fundamental.GUITextField;
import fundamental.InvisibleContainer;
import interaction.TileSelecter;
import layout.IGUILayoutNode.Direction;
import layout.IGUILayoutNode.FlexDirection;
import pathfinding.AStarSearch;
import rendering.shapes.implemented.GUIQuad;
import world.AgentAuthority;
import world.GameBoard;
import world.Tile;
import world.WorldManager;
import world.agents.Agent;

public class AgentInfoWindow extends DefaultWindow {
	
	private Agent agent;
	private String agentInfoString;
	private GUITextField text;
	private GUIButton button1;
	private GUIButton button2;
	
	//**************************** init *************************************
	public AgentInfoWindow(Agent agent) {
		super("Agent Information", 610, 100, 300, 300, FlexDirection.COLUMN);
		
		// INPUT-CONTAINER
		InvisibleContainer<GUIButton> inputContainer = new InvisibleContainer<>(100f, 50f, FlexDirection.ROW);
		button1 = new GUIButton(new GUIQuad(agent.getColor()), 40f, 90f);
		button1.setLabel("Move Agent", "FreeMono", 20);
		button1.setMargin(Direction.ALL, 5);
		button1.setPadding(Direction.ALL, 5);
		button1.addOnClickListener((e) -> {
			Tile previousTile = GameBoard.getTile(this.agent);
			if(AgentAuthority.requestToMoveAgent(this.agent, TileSelecter.getSelectedTileIndex())) {
				ArrayList<Integer> travelPathIndices = new ArrayList<>();
				agent.budget -= AStarSearch.getPathAndCosts(GameBoard.getGraph(), previousTile.getIndex(), GameBoard.getTile(this.agent).getIndex(), travelPathIndices);
				WorldManager.setPath(travelPathIndices);
				WorldManager.refreshMMColor();
				refreshAgentInfo();
			}
		});
		button2 = new GUIButton(new GUIQuad(agent.getColor()), 40f, 90f);
		button2.setLabel("Remove Agent", "FreeMono", 20);
		button2.setMargin(Direction.ALL, 5);
		button2.setPadding(Direction.ALL, 5);
		button2.addOnClickListener((e) -> {
			AgentAuthority.deleteAgent(this.agent);
			}
		);
		inputContainer.addChild(button1);
		inputContainer.addChild(button2);
		
		// OUTPUT-CONTAINER
		InvisibleContainer<GUITextField> outputContainer = new InvisibleContainer<>(100f, 50f, FlexDirection.ROW);
		text = new GUITextField("Dummy", "FreeMono", 90f, 90f, 20);
		text.setMargin(Direction.ALL, 5);
		text.setPadding(Direction.ALL, 5);
		
		outputContainer.addChild(text);
		
		addChild(inputContainer);
		addChild(outputContainer);
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
	
	private void refreshAgentInfo() {
		text.setText(agent.getAgentInfoString());
	}
}
