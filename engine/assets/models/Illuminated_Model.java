package assets.models;


import assets.material.Material;


public class Illuminated_Model extends Element_Model {
	
	private Material material;

	//---------------------------- constructor -----------------------
	
	public Illuminated_Model(int drawMode, Material material) {
		
		super(drawMode);
		
		this.material = material;
		
	}
	
	
	
	//--------------------------- get & set ---------------------------
	
	public Material getMaterial() {
		return material;
	}
	
	
	public void setMaterial(Material material) {
		this.material = material;
	}
	
}
