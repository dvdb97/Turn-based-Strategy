package assets.textures.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {

	public static Image loadImageRGBA(String path) {
		
		int width = 100;
		int height = 100;
		
		byte[] convertedData;
		
		
		try {
			
			BufferedImage image = ImageIO.read(new File(path));
			
			int[] imageData = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
			convertedData = new byte[image.getHeight() * image.getWidth() * 4];
			
			int index = 0;
			for (int y = 0; y < image.getHeight(); ++y) {
                for (int x = 0; x < image.getWidth(); ++x) {
                     
                	//Extract the 4 byte of the 32-Bit-Integer and store them in the array:
                    convertedData[index++] = (byte)((byte)(imageData[y * image.getWidth() + x] >> 16) & 0xFF); //RED
                    convertedData[index++] = (byte)((byte)(imageData[y * image.getWidth() + x] >> 8)  & 0xFF); //GREEN
                    convertedData[index++] = (byte)((byte)(imageData[y * image.getWidth() + x] >> 0)  & 0xFF); //BLUE
                    convertedData[index++] = (byte)((byte)(imageData[y * image.getWidth() + x] >> 24) & 0xFF); //ALPHA
                                        
                }
            }
			
			width = image.getWidth();
			height = image.getHeight();
		
		} catch (IOException e) {
			
			e.printStackTrace();
			
			return generatePlaceholderTexture(width, height);
		
		}
		
		
		return new Image(width, height, convertedData);
	}
	
	
	public static Image generatePlaceholderTexture(int width, int height) {
		byte[] imageData = new byte[height * width * 4];
		
		for (int i = 0; i < imageData.length; ++i) {
				imageData[i] = Byte.MAX_VALUE;
		}
		
		return new Image(width, height, imageData);
	}
	
	
}



