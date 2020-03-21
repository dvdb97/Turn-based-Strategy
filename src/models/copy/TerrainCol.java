package models.copy;

import assets.meshes.geometry.Color;
import math.vectors.Vector3f;
import models.seeds.ColorFunction;

public class TerrainCol implements ColorFunction{
	
	@Override
	public Color color(int x, int y, float elevation) {
		float e = elevation;
		
		if (e > 1.6f) {
			return new Color(0.35f+(e-1.6f), 0.30f+(e-1.6f), 0.25f+(e-1.6f), 1.0f);
			
		} else if (e > 1.0f && e <= 1.6f) {
			return new Color(0.0f+0.53f*(e-1.0f), 0.36f-0.1f*(e-1.0f), 0.04f+0.35f*(e-1.0f), 1.0f);
			
		} else if  (e > 0.3f && e <= 1.0f) {
			return new Color(0.52f-0.74f*(e-0.3f), 0.63f-0.386f*(e-0.3f), 0.13f-0.3f*(e-0.3f), 1.0f);
			
		} else if (e > 0 && e <= 0.3f) {
			return new Color(0.76f-0.8f*(e-0.0f), 0.7f-0.23f*(e-0.0f), 0.5f-1.23f*(e-0.0f), 1.0f);
			
		} else {
			return new Color(0.76f+0.5f*e, 0.7f+0.5f*e, 0.5f+0.5f*e, 1.0f);
		}
		
		
	}
	
}
