package rendering.shaders;

public class CodeBuffer {
	
	private char[] text;
	
	private int position;
	
	
	public CodeBuffer() {
		
		position = 0;
		
		text = new char[100];
		
	}
	
	
	private void increaseSize() {
		
		char[] newText = new char[text.length * 10];
		
		for (int i = 0; i < text.length; ++i) {
			
			newText[i] = text[i];
			
		}
		
		text = newText;
		
	}
	
	
	public void add(String s) {
		
		if (position + s.length() >= text.length) {
			increaseSize();
		}
		
		for (int i = 0; i < s.length(); ++i) {
			
			text[position + i] = s.charAt(i);
			
		}
		
		position += s.length();
		
	}
	
	
	public String toString() {
		return new String(text);
	}
	
	
	public int getSize() {
		return text.length;
	}
	
	
	public void print() {
		System.out.println(toString());
	}

}
