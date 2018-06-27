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
	public static Mesh loadObjFile(String meshPath, BufferLayout layout) {
		
		init();
				
		try(BufferedReader reader = new BufferedReader(new FileReader(new File(meshPath)))) {
			
			positions = new ArrayList<float[]>();
			
			texCoords = new ArrayList<float[]>();
			
			normals = new ArrayList<float[]>();
			
			vertices = new ArrayList<Vertex>();
			
			indices = new ArrayList<Integer>();
			
			String line;
			
			while ((line = reader.readLine()) != null) {
				
				if (getPositionData(line)) 
					continue;
				
				if (getTexPosData(line))
					continue;
				
				if (getNormalData(line))
					continue;
				
				assembleFaces(line);
				
			}
			
			
		} catch(FileNotFoundException e) {
			
			e.printStackTrace();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
		return null;
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
			
			int count = matcher.groupCount();
			
			System.out.println("Found: " + matcher.groupCount());
			
			for (int i = 1; i <= count; ++i) {
				
				if (i % 4 == 1) {
					System.out.println(matcher.group(i));
					
					continue;
				}
				
				if (i % (count / 4) == 2)
					position = parseInt(matcher.group(i));
				
				if (i % (count / 4) == 3)
					texPos = parseInt(matcher.group(i));
				
				if (i % (count / 4) == 0) {
					normal = parseInt(matcher.group(i));
					
					vertices.add(new Vertex(position == -1 ? null : positions.get(position), 
											texPos == -1 ? null : texCoords.get(texPos), 
											normal == -1 ? null : normals.get(normal)));
					
					System.out.println(vertices.get(vertices.size() - 1));
				}
				
			}		
			
			if (matcher.groupCount() == 16) {
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
		System.out.println("Quad");
	}
	
	
	private static void toTriange(Matcher matcher) {
		System.out.println("Triangle");
	}
	
	
	public static void main(String[] args) {
		FileLoader.loadObjFile("res/models/Suzanne.obj", BufferLayout.INTERLEAVED);
	}

}
