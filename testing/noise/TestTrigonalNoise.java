package noise;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import models.seeds.noise.TrigonalNoise;

class TestTrigonalNoise {
	
	final int LENGTH = 256;
	final int WIDTH = 256;
	
	final int MIN_OCT = 2;
	final int MAX_OCT = 6;
	
	final int RUNS = 100;
	
	
	TrigonalNoise trigonalNoise;

	@Test
	final void testGetValue() {
		
		int[] histogram = new int[10];
		
		boolean fail = false;
		
		for (int n=0; n<RUNS; n++) {
			
			trigonalNoise = new TrigonalNoise(LENGTH, WIDTH, MIN_OCT, MAX_OCT);
			
			for (int x=0; x<LENGTH; x++) {
				for (int y=0; y<WIDTH; y++) {
					float v =trigonalNoise.getValue(x, y);
					if (v<0 || v>=1) {
						System.out.println("bad value" + v);
						fail = true;
					}
					int h = (int)(v*10);
					histogram[h]++;
				}
			}
			
		}
		
		//print histogram
		System.out.println("histogram");
		for (int i=0; i<histogram.length; i++) {
			System.out.println("0," + i + ":" + histogram[i]);
		}
		
		
		if (fail) {
			fail("at least one value is <0 or >=1");
		}
		
	}

}
