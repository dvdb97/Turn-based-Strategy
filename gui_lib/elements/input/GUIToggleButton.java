package elements.input;

import assets.textures.Texture2D;
import elements.functions.GUIEventHandler;
import elements.fundamental.GUIElementBase;
import rendering.shapes.GUIShape;
import rendering.shapes.implemented.GUIQuad;

public class GUIToggleButton extends GUIButton {
	
	
	private static final Texture2D enabledTexture = new Texture2D("res/Icons/ToggleButton_Enabled.png", 600, 200);
	
	private static final Texture2D disabledTexture = new Texture2D("res/Icons/ToggleButton_Disabled.png", 600, 200);
	
	//The relation of the buttons width and height that should be maintained
	private static final float sizeRelation = 6f / 2f;
	
	private GUIEventHandler enableFunc;
	private GUIEventHandler disableFunc;
	
	private boolean enabled;
	
	//******************************* constructor **********************************************
	
	public GUIToggleButton(float x, float y, float width, float height) {
		super(new GUIQuad(), disabledTexture, x, y, width, height);
		
		this.enabled = false;
		
		this.setNativeOnclickFunc((element) -> {
			
			enabled = !enabled; element.setTexture(enabled ? enabledTexture : disabledTexture);
			
			if(enabled) {
				if(enableFunc != null)
					enableFunc.function(element);
			} else {
				if(disableFunc != null)
					disableFunc.function(element);
			}
			
		});
		
	}
	
	
	public GUIToggleButton(float x, float y, float width, float height, String label) {
		this(x, y, width, height);
		
		this.setLabel(label);
		
	}
	
	
	//****************************** get & set ****************************************************
	
	public void setEnableFunction(GUIEventHandler func) {
		enableFunc = func;
	}
	
	public void setDisableFunction(GUIEventHandler func) {
		disableFunc = func;
	}

	@Override
	public void setOnclickFunc(GUIEventHandler onclickFunc) {
		//a toggle button has no onclick function, but an enable- and disable function instead
	}
	
	@Override
	public void setWidth(float width) {
		super.setWidth(width);
		
		super.setHeight(width / sizeRelation);
	}


	@Override
	public void setHeight(float height) {
		super.setHeight(height);
		
		super.setWidth(height * sizeRelation);
	}
	
	
	public boolean isEnabled() {
		return enabled;
	}

}
