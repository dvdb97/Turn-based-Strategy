package pathfinding;

public abstract class DemoUnit {
	
	private float water, grasland, forest, mountains;
	
	public DemoUnit(float water, float grasland, float forest, float mountains) {
		this.water = water;
		this.grasland = grasland;
		this.forest = forest;
		this.mountains = mountains;
	}

	public float getWater() {
		return water;
	}

	public float getGrasland() {
		return grasland;
	}

	public float getForest() {
		return forest;
	}

	public float getMountains() {
		return mountains;
	}

}
