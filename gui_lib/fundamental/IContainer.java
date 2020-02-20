package fundamental;

import java.util.Set;

import fundamental.Element.Alignment;

public interface IContainer<E extends Element> {
	
	public enum FlexDirection {
		ROW,
		COLUMN,
		ROW_REVERSE,
		COLUMN_REVERSE
	}
	
	public void setFlexDirection(FlexDirection flexDirection);
	
	public FlexDirection getFlexDirection();
	
	public void addChild(E element);
	
	public void removeChild(E element);
	
	public void removeAllChildren();
	
	public Set<E> getChildren();
	
	public int getNumChildren();
	
	public void setContentAlignment(Alignment align);

}
