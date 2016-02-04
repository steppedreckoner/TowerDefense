package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Clock.Delta;

import org.newdawn.slick.opengl.Texture;

public abstract class AOE implements Entity{
	
	protected float timeAlive, duration, range;
	protected float x, y;
	protected int width, height;
	protected Texture placingTex;
	protected Texture activeTex;
	protected boolean isAlive;
	
	private static int TotalAOEPlaced = 0;
	
	public AOE(AOEType type, float x, float y){
		this.isAlive = true;
		this.activeTex = type.activeTex;
		this.placingTex = type.placingTex;
		this.timeAlive = 0;
		this.duration = type.duration;
		this.range = type.range;
		this.x = x;
		this.y = y;
		this.width = type.width;
		this.height = type.height;
		System.out.println(x + ", " + y);
		Player.AOEList.add(this);
		TotalAOEPlaced++;
	}
	
	public void update(){
		if (isAlive){
			draw();
			doEffect();
			timeAlive += Delta();
			if (timeAlive > duration){
				die();
			}
		}
	}
	
	protected abstract void doEffect();
	protected abstract void endEffect();	
	public abstract void draw();
	
	//Used to illustrate AOEs that are about to be placed
	public static void PlacementDraw(AOEType type, int x, int y){
		DrawQuadTex(type.placingTex, x - (type.width / 2), y - (type.height / 2), type.width, type.height);
	}
	
	private void die() {
		endEffect();
		isAlive = false;
		Player.AOEList.remove(this);
	}
	
	protected boolean isInRange(float xCoord, float yCoord) {
		float totalDist = findDistance(xCoord, yCoord);
		if (totalDist < this.range) {
			return true;
		}
		return false;
	}
	
	protected float findDistance(float xCoord, float yCoord) {
		float xDist = xCoord - x;
		float yDist = yCoord - y;
		return (float) Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
	}
	
	public static int GetTotalAOEPlaced() {
		return TotalAOEPlaced;
	}
	
	public boolean isAlive(){
		return isAlive;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void setX(float x) {
	}

	@Override
	public void setY(float y) {
	}

	@Override
	public void setWidth(int width) {
	}

	@Override
	public void setHeight(int height) {
	}

}
