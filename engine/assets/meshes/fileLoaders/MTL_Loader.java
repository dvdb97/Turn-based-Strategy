package assets.meshes.fileLoaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import assets.material.Material;
import math.vectors.Vector3f;

public class MTL_Loader {

	public static Material loadMaterialFromMTL(String file) {		
		
		Vector3f ambient = new Vector3f(1f, 1f, 1f);
		Vector3f diffuse = new Vector3f(1f, 1f, 1f);
		Vector3f specular = new Vector3f(1f, 1f, 1f);
		Vector3f emission = new Vector3f(1f, 1f, 1f);
		
		
		String nextLine = "";
		
		
		try(BufferedReader reader = new BufferedReader(new FileReader(new File(file)))) {
			
			while ((nextLine = reader.readLine()) != null) {
				
				if (nextLine.startsWith("Ka")) {
					ambient = extractData(nextLine);
					
					continue;
				}
				
				if (nextLine.startsWith("Kd")) {
					diffuse = extractData(nextLine);
					
					continue;
				}
				
				if (nextLine.startsWith("Ks")) {
					specular = extractData(nextLine);
					
					continue;
				}
				
				if (nextLine.startsWith("Ke")) {
					emission = extractData(nextLine);
					
					continue;
				}
				
				
			}
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		
		return new Material(emission, ambient, diffuse, specular, 1.0f);
	}
	
	
	private static Vector3f extractData(String line) {
		Vector3f vec = new Vector3f(0, 0, 0);
		
		String subString = "";
		int numberCounter = 0;
		
		int firstIndex = 2;
		int lastIndex = 2;
		
		while (lastIndex < line.length()) {
			
			/*
			 * While the char at the position lastIndex isn't a space and the lastIndex is still in the range of the line 
			 * increase lastIndex
			 */
			while(line.charAt(lastIndex) != ' ' && lastIndex < line.length() - 1) {
				
				lastIndex++;
				
			}
			
			
			subString = line.substring(firstIndex, lastIndex + 1);
			
			if (numberCounter == 0) {
				vec.setA(Float.parseFloat(subString));
			}
			
			if (numberCounter == 1) {
				vec.setB(Float.parseFloat(subString));
			}
			
			if (numberCounter == 2) {
				vec.setC(Float.parseFloat(subString));
			}
			
			firstIndex = ++lastIndex;
			
			numberCounter++;
			
		}
		
		
		
		return vec;
	}
	
	
}
