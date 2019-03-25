package pathfinding;

import java.util.List;

public interface IGraph<E> {
	
	public List<E> getSuccessors(E node);
	
	public float getCosts(E start, E end);
	
	public float getHeuristic(E start, E end);

}
