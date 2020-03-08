package fundamental;

import java.util.Set;

import layout.IGUILayoutNode.Alignment;
import layout.IGUILayoutNode.FlexDirection;

public interface IContainer<E extends Element> {
	
	public void setFlexDirection(FlexDirection flexDirection);
	
	public FlexDirection getFlexDirection();
	
	public void addChild(E element);
	
	public void removeChild(E element);
	
	public void removeAllChildren();
	
	public Set<E> getChildren();
	
	public int getNumChildren();
	
	public void setContentAlignment(Alignment align);

}
