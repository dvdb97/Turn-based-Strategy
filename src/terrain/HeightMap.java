package terrain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import utils.Image;

public class HeightMap implements Generator {
	
	private int width;
	
	private int height;
	
	private byte[] data;
	
	
	public HeightMap(String path) {
		
		loadImageDataRGBA(path);
		
	}
	
	
	public void loadImageDataRGBA(String url) {
        BufferedImage image = null;
        
        int[] colorData;
        
        try {
            image = ImageIO.read(new File(url));
                                
            
            //Store all pixel ARBG values in an int array
            colorData = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
            data = new byte[image.getWidth() * image.getHeight()];
            
            
            //Extract the Red/Green/Blue values and store them in the texture data array
            int counter = 0;
            for (int y = 0; y < image.getHeight(); ++y) {
                for (int x = 0; x < image.getWidth(); ++x) {
                    
                    data[counter++] = (byte)colorData[y * image.getWidth() + x];
                    
                }
            }
                        
            this.width = image.getWidth();
            
            this.height = image.getHeight();
            
            
        } catch(IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load " + url);
        
        }
    }
	

	@Override
	public float getElevation(int x, int y) {				
		return (float)Byte.toUnsignedInt(data[y * width + x]) / (float)(Byte.MAX_VALUE);
	}
	

	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return width;
	}
	

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return height;
	}
	
	//TODO
	public float getSeaLevel() {
		return 0.0f;
	}
}

