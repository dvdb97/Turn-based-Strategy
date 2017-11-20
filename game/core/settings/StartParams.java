package core.settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class StartParams {
	
	private String title = "Placeholder";
	
	private String version = "Placeholder";
	
	private int windowMode = 0;
	
	private boolean debug = false;
	
	
	public StartParams(String path) {
		
		parse(loadFile(path));
		
	}
	
	
	private String loadFile(String path) {
		
		String output = "";
		
		try(BufferedReader reader = new BufferedReader(new FileReader(new File(path)))) {
			
			String s;
			
			while((s = reader.readLine()) != null) {
				
				output += s;
				
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
		return output;
			
	}
	
	
	private void parse(String text) {
		
		int index = 0;
		
		for (int i = 0; i < text.length(); ++i) {
			
			if (text.charAt(i) == ';') {
				
				String stringToParse = text.substring(index, i);
				
				index = i + 1;
				
				//Extract the title
				if (stringToParse.startsWith("title")) {
					
					int start = stringToParse.indexOf('"', 4);
					int end = stringToParse.lastIndexOf('"');
					
					this.title = stringToParse.substring(start + 1, end);
					
				}
				
				
				//Extract the version
				if (stringToParse.startsWith("version")) {
					
					int start = stringToParse.indexOf('"', 4);
					int end = stringToParse.lastIndexOf('"');
					
					this.version = stringToParse.substring(start + 1, end);
					
				}
				
			}
			
		}
		
	}

	
	
	public String getTitle() {
		return title;
	}


	public String getVersion() {
		return version;
	}


	public int getWindowMode() {
		return windowMode;
	}


	public boolean isDebug() {
		return debug;
	}


	public static void main(String[] args) {
		
		StartParams params = new StartParams("Settings/StartParameter");
		
		System.out.println(params.getTitle() + " " + params.getVersion());
		
	}
	
}
