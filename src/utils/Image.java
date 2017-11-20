package utils;


//Just an Object to put image data in
public class Image {
	
	private byte[] data;
	
	private int imgWidth;
	
	private int imgHeight;

	
	public byte[] getData() {
		return data;
	}
	

	public void setData(byte[] data) {
		this.data = data;
	}
	

	public int getImgWidth() {
		return imgWidth;
	}
	

	public void setImgWidth(int imgWidth) {
		this.imgWidth = imgWidth;
	}
	

	public int getImgHeight() {
		return imgHeight;
	}
	

	public void setImgHeight(int imgHeight) {
		this.imgHeight = imgHeight;
	}	

}

