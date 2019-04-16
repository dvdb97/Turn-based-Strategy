package pathfinding;

import java.util.List;

public class PathfindingTest {
	
	public static void main(String[] args) {
		run();
	}
	
	
	private static void run() {
		int width = 50;
		int height = 50;
		
		int startX = 0;
		int startY = 0;
		
		int endX = width - 1;
		int endY = height - 1;
		
		System.out.println("Generating board . . .");
		DemoBoard board = new DemoBoard(width, height);
		
		System.out.println("Finding path . . .");
		List<Integer> path = AStarSearch.getPath(board, startY * width + startX, endY * width + endX);
		
		System.out.println("Displaying board . . .");
		board.display(path);
	}

}
