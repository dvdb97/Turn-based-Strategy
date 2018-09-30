package assets.meshes.fileLoaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import assets.material.Material;
import assets.meshes.Mesh;
import assets.meshes.geometry.Vertex;
import math.vectors.Vector2f;
import math.vectors.Vector3f;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static org.lwjgl.glfw.GLFW.*;


public class FileLoader {
	
	/**
	 * 
	 * Loads a model from a .obj-File. The implementation is heavily inspired by 
	 * ThinMatrix' tutorial: 
	 * 
	 * https://www.youtube.com/watch?v=YKFYtekgnP8&index=10&list=PLRIWtICgwaX0u7Rf9zkZhLoLuZVfUksDP
	 * 
	 * Requirements:
	 * 
	 * 	- The model has to be triangulated in Blender as this algorithm isn't able to process quads.
	 *  - No lines have been added manually to the file as it might cause the programm to crash.
	 * 
	 * @param path The path of the file.
	 * @return Returns a Mesh
	 */
	public static Mesh loadObjFile(String path) {
		
		double timer1 = glfwGetTime();
		
		ArrayList<Vector3f> positions = new ArrayList<Vector3f>();
		
		ArrayList<Vector2f> texCoords = new ArrayList<Vector2f>();
		ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
		
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		HashMap<Vertex, Integer> vertexLookUp = new HashMap<Vertex, Integer>();
		
		ArrayList<Integer> indices = new ArrayList<Integer>();
		
		String line = "";
		
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(path)))) {
			
			while ((line = reader.readLine()) != null) {
				
				String[] currentLines = line.split(" ");
				
				if (line.startsWith("v ")) {
					
					positions.add(new Vector3f(parseFloat(currentLines[1]), parseFloat(currentLines[2]), parseFloat(currentLines[3])));
				
				} else if (line.startsWith("vt ")) {
					
					texCoords.add(new Vector2f(parseFloat(currentLines[1]), 1f - parseFloat(currentLines[2])));
				
				} else if (line.startsWith("vn ")) {
					
					normals.add(new Vector3f(parseFloat(currentLines[1]), parseFloat(currentLines[2]), parseFloat(currentLines[3])));
				
				} else if (line.startsWith("f ")) {
					
					String[] currentLine = line.split(" ");
					
					for (int i = 1; i < currentLine.length; ++i) {
						String[] vertexData = currentLine[i].split("/");
						
						processVertex(vertexData, vertices, vertexLookUp, indices, positions, texCoords, normals);
					}
				}				
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Time needed: " + (glfwGetTime() - timer1));
		
		return new Mesh(vertices, indices);
	}
	
	
	private static void processVertex(String[] vertexData, List<Vertex> vertices, HashMap<Vertex, Integer> vertexLookUp, List<Integer> indices, List<Vector3f> positions, List<Vector2f> texCoords, List<Vector3f> normals) {
		Vector3f vPosition = positions.get(parseInt(vertexData[0]) - 1);		
		Vector2f vTexCoord = vertexData[1].equals("") ? null : texCoords.get(parseInt(vertexData[1]) - 1);
		Vector3f vNormal = normals.get(parseInt(vertexData[2]) - 1);
		
		Vertex vertex = new Vertex(vPosition, vTexCoord, vNormal);
		
		if (vertexLookUp.containsKey(vertex)) {
			indices.add(vertexLookUp.get(vertex));
		} else {
			vertices.add(vertex);
			indices.add(new Integer(vertices.size() - 1));
			vertexLookUp.put(vertex, vertices.size() - 1);
		}
	}
	
	
	public static Material loadMaterialFromMTL(String file) {		
		return null;
	}

}
