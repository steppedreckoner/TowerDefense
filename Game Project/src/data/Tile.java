package data;

import org.newdawn.slick.opengl.Texture;

import static data.TileGrid.GetTile;
import static helpers.Artist.*;

public class Tile {
	private float x, y; 
	private int width, height;
	private Texture texture;
	private TileType type;
	private boolean hasTower;
	
	public Tile(float x, float y, int width, int height, TileType type){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.type = type;
		this.texture = QuickLoad(type.textureName);
		this.hasTower = false;
	}
	
	public void draw(){
		DrawQuadTex(texture, x, y, width, height);
	}
	
	public boolean equals(Tile t){
		if (t.getType() == this.getType() &&
				t.getHeight() == this.getHeight() &&
				t.getWidth() == this.getWidth() &&
				t.getXPlace() == this.getXPlace() &&
				t.getYPlace() == this.getYPlace()){
			return true;
		}
		return false;
	}
	
	public Tile[] getNeighbors(){
		Tile[] neighbors = new TileDist[4];
		neighbors[0] = GetTile(this.getXPlace(), this.getYPlace() - 1);
		neighbors[1] = GetTile(this.getXPlace() + 1, this.getYPlace());
		neighbors[2] = GetTile(this.getXPlace(), this.getYPlace() + 1);
		neighbors[3] = GetTile(this.getXPlace() - 1, this.getYPlace());
		return neighbors;
	}
	
	public boolean canGround(){
		return this.type.canGround;
	}
	
	public boolean canFly(){
		return this.type.canFly;
	}
	
	public boolean canBuild(){
		return this.type.canBuild;
	}

	public float getX() {
		return x;
	}
	
	public float getCenterX(){
		return x + TILE_SIZE / 2;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}
	
	public float getCenterY(){
		return y + TILE_SIZE / 2;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public int getXPlace(){
		return (int) x / TILE_SIZE;
	}
	
	public int getYPlace(){
		return (int) y / TILE_SIZE;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public TileType getType() {
		return type;
	}

	public void setType(TileType type) {
		this.type = type;
	}

	public boolean hasTower(){
		return hasTower;
	}
	
	public void setHasTower(boolean b){
		hasTower = b;
	}
	
}
