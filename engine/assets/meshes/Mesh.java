package assets.meshes;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

import assets.Bindable;
import assets.Deletable;
import assets.IDeletable;
import assets.buffers.VertexBuffer;
import assets.material.Material;
import assets.textures.Texture;

import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

public class Mesh extends Deletable implements IRenderable {
	
	private enum Attribute {
		POSITION, COLOR, TEXCOORD, NORMAL
	};
	
	protected HashMap<Attribute, FloatBuffer> vertexData;
	
	protected HashMap<Attribute, VertexBuffer> vertexBuffers;
	
	private VertexArrayObject vao;
	
	private IntBuffer indexBuffer;
	
	private Transformable transformable;
	
	private Material material = Material.standard;
	
	private Texture texture;
	
	private int drawMode = GL_TRIANGLES;
	
	
	public Mesh() {
		this.vao = new VertexArrayObject();
		this.transformable = new Transformable();
		vertexData = new HashMap<Attribute, FloatBuffer>();
		vertexBuffers = new HashMap<Attribute, VertexBuffer>();
	}
	
	
	public Mesh(int drawMode) {
		this();
		
		this.drawMode = drawMode;
	}
	
	
	public Mesh(Material material, Texture texture) {
		this();
		
		this.material = material;
		this.texture = texture;
	}
	
	
	/**
	 * 
	 * Sets this Mesh's position data and stores it on the gpu.
	 * 
	 * @param buffer A buffer containing position data.
	 * @param size The number of floats representing a position.
	 */
	public void setPositionData(FloatBuffer buffer, int size) {
		this.setAttribute(buffer, Attribute.POSITION, 0, size);
	}
	
	
	/**
	 * 
	 * Sets this Mesh's position data and stores it on the gpu.
	 * This method assumes that a position attribut always consists
	 * of three values.
	 * 
	 * @param buffer A buffer containing position data.
	 */
	public void setPositionData(FloatBuffer buffer) {
		this.setPositionData(buffer, 3);
	}
	
	
	/**
	 * 
	 * Sets this Mesh's position data by setting a pointer to data that
	 * is already stored on the gpu.
	 * 
	 * @param buffer A vertex buffer containing position data.
	 * @param size The number of floats representing a position.
	 */
	public void setPositionVertexBuffer(VertexBuffer buffer, int size) {
		this.setVertexBuffer(buffer, Attribute.POSITION, 0, size);
	}
	
	
	/**
	 * 
	 * @return Returns this Mesh's position data as a FloatBuffer.
	 */
	public FloatBuffer getPositionData() {
		return vertexData.get(Attribute.POSITION);
	}
	
	
	/**
	 * 
	 * @return Returns this Mesh's position data as a VertexBuffer.
	 */
	public VertexBuffer getPositionVertexBuffer() {
		return vertexBuffers.get(Attribute.POSITION);
	}
	
	
	/**
	 * 
	 * Sets this Mesh's color data and stores it on the gpu.
	 * 
	 * @param buffer A buffer containing color data.
	 * @param size The number of floats representing a color.
	 */
	public void setColorData(FloatBuffer buffer, int size) {
		this.setAttribute(buffer, Attribute.COLOR, 1, size);
	}
	
	
	/**
	 * 
	 * Sets this Mesh's color data and stores it on the gpu.
	 * This method assumes that a color attribut always consists
	 * of four values.
	 * 
	 * @param buffer A buffer containing color data.
	 */
	public void setColorData(FloatBuffer buffer) {
		this.setColorData(buffer, 4);
	}
	
	
	/**
	 * 
	 * Sets this Mesh's color data by setting a pointer to data that
	 * is already stored on the gpu.
	 * 
	 * @param buffer A vertex buffer containing color data.
	 * @param size The number of floats representing a color.
	 */
	public void setColorVertexBuffer(VertexBuffer buffer, int size) {
		this.setVertexBuffer(buffer, Attribute.COLOR, 1, size);
	}
	
	
	/**
	 * 
	 * @return Returns this Mesh's color data as a FloatBuffer.
	 */
	public FloatBuffer getColorData() {
		return vertexData.get(Attribute.COLOR);
	}
	
	
	/**
	 * 
	 * @return Returns this Mesh's color data as a VertexBuffer.
	 */
	public VertexBuffer getColorVertexBuffer() {
		return vertexBuffers.get(Attribute.COLOR);
	}
	
	
	/**
	 * 
	 * Sets this Mesh's texture coordinate data and stores it on the gpu.
	 * 
	 * @param buffer A buffer containing texture coordinate data.
	 * @param size The number of floats representing a texture coordinate.
	 */
	public void setTexCoordData(FloatBuffer buffer, int size) {
		this.setAttribute(buffer, Attribute.TEXCOORD, 2, size);
	}
	
	
	/**
	 * 
	 * Sets this Mesh's texture coordinate data by setting a pointer to data that
	 * is already stored on the gpu.
	 * 
	 * @param buffer A vertex buffer containing texture coordinate data.
	 * @param size The number of floats representing a texture coordinate.
	 */
	public void setTexCoordVertexBuffer(VertexBuffer buffer, int size) {
		this.setVertexBuffer(buffer, Attribute.TEXCOORD, 2, size);
	}
	
	
	/**
	 * 
	 * @return Returns this Mesh's texture coordinate data as a FloatBuffer.
	 */
	public FloatBuffer getTexCoordData() {
		return vertexData.get(Attribute.TEXCOORD);
	}
	
	
	/**
	 * 
	 * @return Returns this Mesh's texture coordinate data as a VertexBuffer.
	 */
	public VertexBuffer getTexCoordVertexBuffer() {
		return vertexBuffers.get(Attribute.TEXCOORD);
	}
	
	
	/**
	 * 
	 * Sets this Mesh's normal data and stores it on the gpu.
	 * 
	 * @param buffer A buffer containing normal data.
	 * @param size The number of floats representing a normal.
	 */
	public void setNormalData(FloatBuffer buffer, int size) {
		this.setAttribute(buffer, Attribute.NORMAL, 3, size);
	}
	
	
	/**
	 * 
	 * Sets this Mesh's normal data by setting a pointer to data that
	 * is already stored on the gpu.
	 * 
	 * @param buffer A vertex buffer containing normal data.
	 * @param size The number of floats representing a normal.
	 */
	public void setNormalVertexBuffer(VertexBuffer buffer, int size) {
		this.setVertexBuffer(buffer, Attribute.NORMAL, 3, size);
	}
	
	
	/**
	 * 
	 * @return Returns this Mesh's normal data as a FloatBuffer.
	 */
	public FloatBuffer getNormalData() {
		return vertexData.get(Attribute.NORMAL);
	}
	
	
	/**
	 * 
	 * @return Returns this Mesh's normal data as a VertexBuffer.
	 */
	public VertexBuffer getNormalVertexBuffer() {
		return vertexBuffers.get(Attribute.NORMAL);
	}
	
	
	private void setAttribute(FloatBuffer buffer, Attribute attribute, int pos, int size) {
		this.vertexData.put(attribute, buffer);
		VertexBuffer vertexBuffer = new VertexBuffer(buffer);
		this.setVertexBuffer(vertexBuffer, attribute, pos, size);
	}
	
	
	private void setVertexBuffer(VertexBuffer buffer, Attribute attribute, int pos, int size) {
		vao.setVertexAttributePointer(buffer, pos, size, 0, 0);
		this.vertexBuffers.put(attribute, buffer);
	}
	
	
	/**
	 * 
	 * Sets the index buffer of this mesh.
	 * 
	 * @param buffer The buffer containing the indices.
	 */
	public void setIndexBuffer(IntBuffer buffer) {
		this.indexBuffer = buffer;
	}
	
	
	/**
	 * 
	 * @return Returns the mesh's index buffer.
	 */
	public IntBuffer getIndexBuffer() {
		return indexBuffer;
	}
	
	
	public Transformable getTransformable() {
		return transformable;
	}
	
	
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	
	public Material getMaterial() {
		return material;
	}
	
	
	public Texture getTexture() {
		return texture;
	}


	@Override
	public void render() {
		vao.enableVertexAttribArray();
		if (texture != null) texture.bind();
		
		glDrawElements(drawMode, indexBuffer);
		
		if (texture != null) texture.unbind();
		vao.disableVertexAttribArray();		
	}


	@Override
	public void delete() {
		for (VertexBuffer buffer : vertexBuffers.values()) {
			buffer.delete();
		}
		
		vao.delete();		
	}

}
