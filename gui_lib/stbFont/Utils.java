package stbFont;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import core.Application1;

public class Utils {
	
	public static int getWidthInPx(float width) {
		return (int)(width*Application1.getWindowWidth()/2f);
	}
	
	public static int getHeightInPx(float height) {
		return (int)(height*Application1.getWindowHeight()/2f);
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
	
	public static ArrayList<String> divideInLines(String text) {
		ArrayList<String> strings = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		for (int c=0; c<text.length(); c++) {
			if(text.charAt(c) ==  '\n') {
				strings.add(sb.toString());
				sb = new StringBuilder();
			} else {
				sb.append(text.charAt(c));
			}
		}
		if (sb.length() > 0) {
			strings.add(sb.toString());
		}
		return strings;
	}
	
}
