package assets.textures.utils;

public class ImageUtils {
	
	public static int[] convertRGBAtoARGB(int[] pixels) {
		
		int[] convertedPixels = new int[pixels.length];
		
		for (int i = 0; i < pixels.length; i++) {
			
			byte alpha = (byte)(pixels[i]>> 24);
			
			convertedPixels[i] = (pixels[i] << 8) + alpha;
			
		}
		
		return convertedPixels;
		
	}

}
