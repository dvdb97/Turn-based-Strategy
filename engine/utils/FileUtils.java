package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

	public static String loadShaderSourceCode(String path) {
		StringBuilder shaderSourceCode = new StringBuilder();
		String line;

		try (BufferedReader reader = new BufferedReader(new FileReader(new File(path)))) {
			
			while ((line = reader.readLine()) != null) {
				shaderSourceCode.append(line + "\n");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return shaderSourceCode.toString();
	}
	
	
	public static void saveGameScore(int boardWidth, int boardHeight, byte[] seed) {
		
		FileWriter fw = null;
		
		try {
			
			fw = new FileWriter( "save0" );
			
			//label																		//bytes
			fw.write("SAVE");															//4
			//file length
			fw.write((char)0 +""+(char)0 +""+(char)3 +""+(char)158);					//4
			//game version 
			fw.write("DEV" + "0");														//4
			
			//anzahl spieler
			fw.write((char)0+""+(char)0);												//2
			//size of the board
			fw.write((char)boardWidth +""+ (char)boardHeight);							//2	
			//inGame time
			fw.write((char)0+""+(char)0+""+(char)0+""+(char)0);							//4
			
			//gameboard label
			fw.write("GMBD");															//4
			//length of seed
			fw.write((char)(seed.length/256)+""+(char)(seed.length % 256));				//2
			//maybe bytes per tile
			//byte[] seed
			for (int i=0; i<seed.length; i++) {
				fw.write((char)seed[i]);
			}
			
		} catch (IOException e) {
			System.err.println("An error has occured while creating a file");
		} finally {
			if (fw != null) {
				try { fw.close(); } catch (IOException e) {
					System.err.println("An error has occured while closing the fileWriter");
				}
			}
		}
		
	}
	
}

