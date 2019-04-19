package interaction.input;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import interaction.input.KeyEventListener.Action;

public class KeyEventManager {
	
	//key-specific event listeners that will be called when the specified key was pressed.
	private HashMap<Integer, List<KeyEventListener>> keyDownEventListener;
	
	//Key-independent event listeners that will be called when any key was pressed.
	private List<KeyEventListener> keyIndependentKeyDownListener;
	
	
	//Key-specific event listeners that will be called when the specified key is pressed for a while.
	private HashMap<Integer, List<KeyEventListener>> keyPressedEventListener;
	
	//Key-independent event listeners that will be called when any key is pressed for a while.
	private List<KeyEventListener> keyIndependentKeyPressedListener;
	
	
	//Key-specific event listeners that will be called when the specified key is released.
	private HashMap<Integer, List<KeyEventListener>> keyReleasedEventListener;
	
	//Key-independent event listeners that will be called when any key is released.
	private List<KeyEventListener> keyIndependentKeyReleasedListener;
	
	
	public KeyEventManager() {
		keyDownEventListener = new HashMap<Integer, List<KeyEventListener>>();
		keyIndependentKeyDownListener = new LinkedList<KeyEventListener>();
		
		keyPressedEventListener = new HashMap<Integer, List<KeyEventListener>>();
		keyIndependentKeyPressedListener = new LinkedList<KeyEventListener>();
		
		keyReleasedEventListener = new HashMap<Integer, List<KeyEventListener>>();
		keyIndependentKeyReleasedListener = new LinkedList<KeyEventListener>();
	}
	
	
	/**
	 * 
	 * Adds a listener that will be called in case of an event with the given key and action.
	 * 
	 * @param key The key that needs to be involved in the event.
	 * @param action The action that will trigger the event.
	 * @param listener The listener to add to the event.
	 */
	public void addKeyEventListener(int key, Action action, KeyEventListener listener) {
		switch (action) {
			case KEY_DOWN:
				addKeyDownEventListener(key, listener);
				break;
			case KEY_PRESSED:
				addKeyPressedEventListener(key, listener);
				break;
			case KEY_RELEASED:
				addKeyReleasedEventListener(key, listener);
				break;
		}
	}
	
	
	/**
	 * 
	 * Adds a listener that will be called in case of an event with the given action. It is 
	 * independent of the key that is involved. 
	 * 
	 * E.g. a listener added to the action KEY_RELEASE will be called whenever a key is released.
	 * 
	 * @param action The action that will trigger the event.
	 * @param listener The listener to add to the event.
	 */
	public void addKeyEventListener(Action action, KeyEventListener listener) {
		switch (action) {
		case KEY_DOWN:
			addKeyDownEventListener(listener);
			break;
		case KEY_PRESSED:
			addKeyPressedEventListener(listener);
			break;
		case KEY_RELEASED:
			addKeyReleasedEventListener(listener);
			break;
		}
	}
	
	
	public void addKeyDownEventListener(int key, KeyEventListener listener) {
		if (keyDownEventListener.containsKey(key)) {
			keyDownEventListener.get(key).add(listener);
		} else {
			keyDownEventListener.put(key, new LinkedList<KeyEventListener>());
			keyDownEventListener.get(key).add(listener);
		}
	}
	

	public void removeKeyDownEventListener(int key, KeyEventListener listener) {
		if (keyDownEventListener.containsKey(key)) {
			keyDownEventListener.get(key).remove(key);
		}
	}
	
	
	public void addKeyDownEventListener(KeyEventListener listener) {
		keyIndependentKeyDownListener.add(listener);
	}
	
	
	public void removeKeyDownEventListener(KeyEventListener listener) {
		keyIndependentKeyDownListener.remove(listener);
	}
	
	
	public void addKeyPressedEventListener(int key, KeyEventListener listener) {
		if (keyPressedEventListener.containsKey(key)) {
			keyPressedEventListener.get(key).add(listener);
		} else {
			keyPressedEventListener.put(key, new LinkedList<KeyEventListener>());
			keyPressedEventListener.get(key).add(listener);
		}
	}
	
	
	public void removeKeyPressedEventListener(int key, KeyEventListener listener) {
		if (keyPressedEventListener.containsKey(key)) {
			keyPressedEventListener.get(key).remove(key);
		}
	}
	
	
	public void addKeyPressedEventListener(KeyEventListener listener) {
		keyIndependentKeyPressedListener.add(listener);
	}
	
	
	public void removeKeyPressedEventListener(KeyEventListener listener) {
		keyIndependentKeyPressedListener.remove(listener);
	}
	
	
	public void addKeyReleasedEventListener(int key, KeyEventListener listener) {
		if (keyReleasedEventListener.containsKey(key)) {
			keyReleasedEventListener.get(key).add(listener);
		} else {
			keyReleasedEventListener.put(key, new LinkedList<KeyEventListener>());
			keyReleasedEventListener.get(key).add(listener);
		}
	}
	
	
	public void removeKeyReleasedEventListener(int key, KeyEventListener listener) {
		if (keyReleasedEventListener.containsKey(key)) {
			keyReleasedEventListener.get(key).remove(key);
		}
	}
	
	
	public void addKeyReleasedEventListener(KeyEventListener listener) {
		keyIndependentKeyReleasedListener.add(listener);
	}
	
	
	public void removeKeyReleasedEventListener(KeyEventListener listener) {
		keyIndependentKeyReleasedListener.remove(listener);
	}
	
	
	public void triggerKeyDownEvent(int key) {
		for (KeyEventListener listener : keyIndependentKeyDownListener) {
			listener.handle(key);
		}
		
		if (keyDownEventListener.containsKey(key)) {
			for (KeyEventListener listener : keyDownEventListener.get(key)) {
				listener.handle(key);
			}
		}
	}
	
	
	public void triggerKeyPressedEvent(int key) {
		for (KeyEventListener listener : keyIndependentKeyPressedListener) {
			listener.handle(key);
		}
		
		if (keyPressedEventListener.containsKey(key)) {
			for (KeyEventListener listener : keyPressedEventListener.get(key)) {
				listener.handle(key);
			}
		}
	}
	
	
	public void triggerKeyReleasedEvent(int key) {
		for (KeyEventListener listener : keyIndependentKeyReleasedListener) {
			listener.handle(key);
		}
		
		if (keyReleasedEventListener.containsKey(key)) {
			for (KeyEventListener listener : keyReleasedEventListener.get(key)) {
				listener.handle(key);
			}
		}
	}
}
