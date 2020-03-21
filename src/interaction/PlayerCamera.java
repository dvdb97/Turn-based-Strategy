package interaction;

import assets.cameras.Camera;
import interaction.input.KeyEventManager;
import interaction.input.KeyInputHandler;

public class PlayerCamera extends Camera {
	
	private boolean enabled = true;
	
	private float moveSpeed = 0.1f;
	private float rotSpeed = 0.1f;
	
	
	public PlayerCamera() {
		KeyEventManager keyManager = KeyInputHandler.getKeyEventManager();
		
		//Forward movement
		keyManager.addKeyPressedEventListener(KeyBindings.getMapTranslationForward(), i -> move(0f, moveSpeed, 0f));
		
		//Backward movement
		keyManager.addKeyPressedEventListener(KeyBindings.getMapTranslationBackward(), i -> move(0f, -moveSpeed, 0f));
		
		//Rightward movement
		keyManager.addKeyPressedEventListener(KeyBindings.getMapTranslationRight(), i -> move(moveSpeed, 0f, 0f));
		
		//Leftward movement
		keyManager.addKeyPressedEventListener(KeyBindings.getMapTranslationLeft(), i -> move(-moveSpeed, 0f, 0f));
		
		//Zoom in
		keyManager.addKeyPressedEventListener(KeyBindings.getMapTranslationDown(), i -> move(0f, 0f, -moveSpeed));
		
		//Zoom out
		keyManager.addKeyPressedEventListener(KeyBindings.getMapTranslationUp(), i -> move(0f, 0f, moveSpeed));
		
		//Pitch forward
		keyManager.addKeyPressedEventListener(KeyBindings.getCameraPitchForward(), i -> pitch(0.1f * rotSpeed));
		
		//Pitch backwards
		keyManager.addKeyPressedEventListener(KeyBindings.getCameraPitchBackward(), i -> pitch(-0.1f * rotSpeed));
	}

}
