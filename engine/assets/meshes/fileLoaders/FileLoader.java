package assets.meshes.fileLoaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import assets.meshes.Mesh;
import assets.meshes.MeshConst.BufferLayout;
import assets.meshes.Model;
import assets.meshes.geometry.Vertex;

import static org.lwjgl.glfw.GLFW.*;


public class FileLoader {
	
	private static boolean init = false;
	
	private static Pattern positionPattern;
	
	private static Pattern texCoordsPattern;
	
	private static Pattern normalPattern;
	
	private static Pattern vertexPattern;
	
	
	private static ArrayList<float[]> positions;
	
	private static ArrayList<float[]> texCoords;
	
	private static ArrayList<float[]> normals;
	
	private static ArrayList<Vertex> vertices;
	
	private static ArrayList<Integer> indices;
	
	
	public static void init() {
		
		if (init) return;
		
		positionPattern = Pattern.compile("v (-?[0-9.]+) (-?[0-9.]+) ((-?[0-9.]+))");
		
		texCoordsPattern = Pattern.compile("vt (-?[0-9.]+) (-?[0-9.]+)");
		
		normalPattern = Pattern.compile("vn (-?[0-9.]+) (-?[0-9.]+) (-?[0-9.]+)");
		
		vertexPattern = Pattern.compile("f( ([0-9]*)/([0-9]*)/([0-9]*))( ([0-9]*)/([0-9]*)/([0-9]*))( ([0-9]*)/([0-9]*)/([0-9]*))( ([0-9]*)/([0-9]*)/([0-9]*))?");
		
		init = true;
	}
	
	
	/**
	 * 
	 * 
	 * 
	 * @param meshPath
	 * @param layout
	 * @return
	 */
	public static Mesh loadObjFile(String meshPath) {
		
		double timer1 = glfwGetTime();
		
		init();
				
		try(BufferedReader reader = new BufferedReader(new FileReader(new File(meshPath)))) {
			
			positions = new ArrayList<float[]>();
			
			texCoords = new ArrayList<float[]>();
			
			normals = new ArrayList<float[]>();
			
			vertices = new ArrayList<Vertex>();
			
			indices = new ArrayList<Integer>();
			
			String line;
			
			while ((line = reader.readLine()) != null) {
				
				if (line.startsWith("vn")) {
					getNormalData(line);
					continue;
				}	
				
				if (line.startsWith("vt")) {
					getTexPosData(line);
					continue;
				}
				
				if (line.startsWith("v")) {
					getPositionData(line);
					continue;
				}
				
				assembleFaces(line);
				
			}
			
			
		} catch(FileNotFoundException e) {
			
			e.printStackTrace();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
		System.out.println("Time needed: " + (glfwGetTime() - timer1));
		
		return new Mesh(vertices, indices);
	}
	
	
	/**
	 * 
	 * 
	 * 
	 * @param meshPath
	 * @param materialPath
	 * @param layout
	 * @return
	 */
	public static Model loadObjFile(String meshPath, String materialPath, BufferLayout layout) {
		
		init();
		 
		
		return null;
	}
	
	
	private static boolean getPositionData(String line) {
		
		Matcher matcher = positionPattern.matcher(line);
		
		float[] data = new float[3];
		
		if (matcher.find()) {
			
			for (int i = 1; i < matcher.groupCount(); ++i) {
				
				data[i - 1] = Float.parseFloat(matcher.group(i));
				
			}
			
			positions.add(data);
			
			return true;
			
		}
		
		return false;
		
	}
	
	
	/**
	 * 
	 * 
	 * 
	 * @param line
	 * @param texCoords
	 * @return
	 */
	private static boolean getTexPosData(String line) {
		
		Matcher matcher = texCoordsPattern.matcher(line);
		
		float[] data = new float[2];
		
		if (matcher.find()) {
			
			for (int i = 1; i < matcher.groupCount(); ++i) {
				
				data[i - 1] = Float.parseFloat(matcher.group(i));
				
			}
			
			texCoords.add(data);
			
			return true;
			
		}
		
		return false;
		
	}
	
	
	/**
	 * 
	 * 
	 * 
	 * @param line
	 * @param normals
	 * @return
	 */
	private static boolean getNormalData(String line) {
		
		Matcher matcher = normalPattern.matcher(line);
		
		float[] data = new float[2];
		
		if (matcher.find()) {
			
			for (int i = 1; i < matcher.groupCount(); ++i) {
				
				data[i - 1] = Float.parseFloat(matcher.group(i));
				
			}
			
			normals.add(data);
			
			return true;
			
		}
		
		return false;
		
	}
	
	
	private static void assembleFaces(String line) {
		
		Matcher matcher = vertexPattern.matcher(line);
		
		if (matcher.find()) {
			
			int position = -1;
			
			int texPos = -1;
			
			int normal = -1;
			
			int count = 0;
			
			for (int i = 1; i <= matcher.groupCount(); ++i) {
				
				if (i % 4 == 1) {					
					continue;
				}
				
				
				if (i % 4 == 2)
					if (matcher.group(i) == null)
						break;
					else
						position = parseInt(matcher.group(i));
				
				
				if (i % 4 == 3)
					if (matcher.group(i) == null)
						break;
					else
						texPos = parseInt(matcher.group(i));
				
				
				if (i % 4 == 0) {
					if (matcher.group(i) == null)
						break;
					
					normal = parseInt(matcher.group(i));
					
					vertices.add(new Vertex(position == -1 ? null : positions.get(position - 1), 
											texPos == -1 ? null : texCoords.get(texPos - 1), 
											normal == -1 ? null : normals.get(normal - 1)));
				}
				
				++count;
				
			}		
			
			if (count == 12) {
				toQuad(matcher);
			} else {
				toTriange(matcher);
			}
			
		}
		
	}
	
	
	private static int parseInt(String s) {
		return s.equals("") ? -1 : Integer.parseInt(s);
	}
	
	
	private static void toQuad(Matcher matcher) {
		indices.add(vertices.size() - 4);
		indices.add(vertices.size() - 3);
		indices.add(vertices.size() - 2);
		
		indices.add(vertices.size() - 4);
		indices.add(vertices.size() - 2);
		indices.add(vertices.size() - 1);
	}
	
	
	private static void toTriange(Matcher matcher) {
		indices.add(vertices.size() - 3);
		indices.add(vertices.size() - 2);
		indices.add(vertices.size() - 1);
	}

}
