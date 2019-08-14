package interaction;

public class Resolution {
	
	public static final Resolution FULL_HD = new Resolution(1920, 1080);
	
	public int width, height;
	
	public Resolution(int width, int height) {
		this.width = width;
		this.height = height;
	}

}
