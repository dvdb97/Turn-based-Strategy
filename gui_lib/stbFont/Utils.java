package stbFont;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import core.Application;

public class Utils {
	
	public static int getWidthInPx(float width) {
		return (int)(width*Application.getWindowWidth()/2f);
	}
	
	public static int getHeightInPx(float height) {
		return (int)(height*Application.getWindowHeight()/2f);
	}

	public static ArrayList<Byte> bufferToList(ByteBuffer buffer) {
		
		ArrayList<Byte> list = new ArrayList<>(buffer.limit());
		for (int i=0; i<buffer.limit(); i++) {
			list.add(buffer.get(i));
		}
		return list;
	}
	
	public static ArrayList<Byte> byteZeros(int num) {
		ArrayList<Byte> list = new ArrayList<>(num);
		for (int i=0; i<num; i++) {
			list.add((byte)0);
		}
		return list;
	}
	
}
