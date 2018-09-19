package assets.meshes.fileLoaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
	 * @param path The path of the file.
	 * @return Returns a Mesh
	 */
	public static Mesh loadObjFile(String path) {
		
		double timer1 = glfwGetTime();
		
		ArrayList<Vector3f> positions = new ArrayList<Vector3f>();
		ArrayList<Vector2f> texCoords = new ArrayList<Vector2f>();
		ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
		
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		
		String line = "";
		
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(path)))) {
			
			while ((line = reader.readLine()) != null) {
				
				String[] currentLines = line.split(" ");
				
				if (line.startsWith("v ")) {
					
					positions.add(new Vector3f(parseFloat(currentLines[1]), parseFloat(currentLines[2]), parseFloat(currentLines[3])));
				
				} else if (line.startsWith("vt ")) {
					
					texCoords.add(new Vector2f(parseFloat(currentLines[1]), parseFloat(currentLines[2])));
				
				} else if (line.startsWith("vn ")) {
					
					normals.add(new Vector3f(parseFloat(currentLines[1]), parseFloat(currentLines[2]), parseFloat(currentLines[3])));
				
				} else if (line.startsWith("f ")) {
					
					for (int i = 1; i < currentLines.length; ++i) {
						
						String[] values = currentLines[i].split("/");
						
						float[] position = new float[0];
						float[] texture = new float[0];
						float[] normal = new float[0];
						
						if (!values[0].equals("")) {
							position = positions.get(parseInt(values[0]) - 1).toArray();
						}
						
						if (!values[1].equals("")) {
							texture = texCoords.get(parseInt(values[1]) - 1).toArray();
						}
						
						if (!values[2].equals("")) {
							normal = normals.get(parseInt(values[2]) - 1).toArray();
						}
						
						vertices.add(new Vertex(position, texture, normal));
						
					}
					
					if (currentLines.length == 3) {
						indices.add(vertices.size() - 3);
						indices.add(vertices.size() - 2);
						indices.add(vertices.size() - 1);											
					} else {
						indices.add(vertices.size() - 4);
						indices.add(vertices.size() - 3);
						indices.add(vertices.size() - 2);
						
						indices.add(vertices.size() - 4);
						indices.add(vertices.size() - 2);
						indices.add(vertices.size() - 1);
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
	
	
	public static Material loadMaterialFromMTL(String file) {		
		return null;
	}

}
