package gui;

import java.util.List;

import fundamental.DefaultWindow;
import fundamental.GUIButton;
import fundamental.GUITextField;
import fundamental.InvisibleContainer;
import interaction.TileSelecter;
import layout.IGUILayoutNode.Direction;
import layout.IGUILayoutNode.FlexDirection;
import rendering.shapes.implemented.GUIQuad;
import world.AgentAuthority;
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
		super("Agent Information", 5, 700, 500, 300, FlexDirection.COLUMN);
		
		// OUTPUT-CONTAINER
		InvisibleContainer<GUITextField> outputContainer = new InvisibleContainer<>(100f, 50f, FlexDirection.ROW);
		text = new GUITextField("Dummy", "FreeMono", 90f, 90f, 20);
		text.setMargin(Direction.ALL, 5);
		text.setPadding(Direction.ALL, 5);
		outputContainer.addChild(text);
				
		// INPUT-CONTAINER
		InvisibleContainer<GUIButton> inputContainer = new InvisibleContainer<>(100f, 50f, FlexDirection.ROW);
		button1 = new GUIButton(new GUIQuad(agent.getColor()), 30f, 90f);
		button1.setLabel("Move Agent", "FreeMono", 20);
		button1.setMargin(Direction.ALL, 5);
		button1.setPadding(Direction.ALL, 5);
		button1.addOnClickListener((e) -> {
			List<Integer> path = AgentAuthority.requestToMoveAgent(this.agent, TileSelecter.getSelectedTileIndex());
			if( path != null) {
				WorldManager.setPath(path);
				WorldManager.refreshMMColor();
			}
		});
		button2 = new GUIButton(new GUIQuad(agent.getColor()), 30f, 90f);
		button2.setLabel("Remove Agent", "FreeMono", 20);
		button2.setMargin(Direction.ALL, 5);
		button2.setPadding(Direction.ALL, 5);
		button2.addOnClickListener((e) -> {
			AgentAuthority.deleteAgent(this.agent);
			this.close();
			}
		);
		inputContainer.addChild(button1);
		inputContainer.addChild(button2);
		

		addChild(outputContainer);
		addChild(inputContainer);
		changeAgent(agent);
	}
	
	public void changeAgent(Agent newAgent) {
		
		if (newAgent==null)
			return;
		
		if (this.agent != null) {this.agent.setCallbackFunction(null);}
		newAgent.setCallbackFunction((a) -> refreshAgentInfo());
		
		agentInfoString = newAgent.getAgentInfoString();
		text.setText(agentInfoString);
		button1.setShape(new GUIQuad(newAgent.getColor()));
		button2.setShape(new GUIQuad(newAgent.getColor()));
		
		this.agent = newAgent;		
	}
	
	private void refreshAgentInfo() {
		text.setText(agent.getAgentInfoString());
	}
	
	@Override
	public void close() {
		GameGUIManager.deleteAgentInfoWindow();
		super.close();
	}
	
}
