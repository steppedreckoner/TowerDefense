package data;

public enum TileType {

	Grass("grass", true, false, false), 
	Dirt("dirt", false, true, true), 
	Water("water", false, false, true),
	Start("start", false, true, true),
	Goal("goal", false, true, true),
	NULL("UFO1", false, false, false);

	String textureName;
	boolean canBuild, canGround, canFly;	//Can you build a tower, can ground troops pass and can flying troops pass resp.

	TileType(String textureName, boolean canBuild, boolean canGround, boolean canFly) {
		this.textureName = textureName;
		this.canBuild = canBuild;
		this.canGround = canGround;
		this.canFly = canFly;
	}
	
	public boolean canBuild(){
		return this.canBuild;
	}
	
	public boolean canGround(){
		return this.canGround;
	}
	
	public boolean canFly(){
		return this.canFly;
	}

}
