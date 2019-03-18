package datastructures;

import java.util.Arrays;

public class RingBuffer {
	
	private float[] values;
	private int pointer = 0;
	
	
	public RingBuffer(int capacity) {
		this.values = new float[capacity];
	}
	
	
	public float[] toArray() {
		return values;
	}
	
	
	public void put(float value) {
		values[pointer++ % values.length] = value;
	}
	
	
	public void put(float[] values) {
		for (float f : values) {
			put(f);
		}
	}
	
	
	public void set(int index, float value) {
		values[index] = value;
	}
	
	
	public float get(int index) {
		return values[index];
	}
	
	
	public int size() {
		return values.length;
	}
	
	
	public static void main(String[] args) {
		RingBuffer ring = new RingBuffer(5);
		
		for (int i = 0; i < 20; ++i) {
			ring.put(i);
			System.out.println(Arrays.toString(ring.toArray()));
		}
	}

}
