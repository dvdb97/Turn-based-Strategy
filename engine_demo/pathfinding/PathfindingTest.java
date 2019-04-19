package pathfinding;

import java.util.List;

public class PathfindingTest {
	
	public static void main(String[] args) {
		run();
	}
	
	
	private static void run() {		
		int width = 40;
		int height = 40;
		
		int startX = 0;
		int startY = 0;
		
		int endX = width - 1;
		int endY = height - 1;
		
		int centerX = width / 2;
		int centerY = height / 2;
		
		DemoUnit unit = new MountedUnit();
		DemoUnit mountaineer = new Mountaineer();
		
		System.out.println("Generating board . . .");
		DemoBoard board = new DemoBoard(width, height);
		
		System.out.println("Finding path . . .");
		List<Integer> path = AStarSearch.getPath(board, startY * width + startX, endY * width + endX);
		//Set<Integer> reachable = AStarSearch.getReachableNodes(board, centerY * width + centerX, 20f);
		
		System.out.println("Displaying board . . .");
		board.display(path);
	}

}
