package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.DrawQuadTex;
import static helpers.Clock.Delta;

import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AOE implements Entity{
	
	protected float timeAlive, duration, range;
	protected static float cooldown;
	protected float x, y;
	protected int width, height;
	protected Texture placingTex;
	protected Texture activeTex;
	protected boolean isAlive;
	
	protected CopyOnWriteArrayList<Enemy> enemies;
	
	public AOE(AOEType type, float x, float y, CopyOnWriteArrayList<Enemy> enemies){
		this.isAlive = true;
		this.activeTex = type.activeTex;
		this.placingTex = type.placingTex;
		this.timeAlive = 0;
		this.duration = type.duration;
		this.range = type.range;
		this.cooldown = type.cooldown;
		this.x = x;
		this.y = y;
		this.width = type.width;
		this.height = type.height;
		this.enemies = enemies;
		System.out.println(x + ", " + y);
	}
	
	public void update(){
		if (isAlive){
			draw();
			doEffect();
			timeAlive += Delta();
			if (timeAlive > duration){
				isAlive = false;
			}
		}
	}
	
	protected abstract void doEffect();
	
	public abstract void draw();
	
	//Used to illustrate AOEs that are about to be placed
	public static void PlacementDraw(AOEType type, int x, int y){
		DrawQuadTex(type.placingTex, x - (type.width / 2), y - (type.height / 2), type.width, type.height);
	}
	
	protected boolean isInRange(Enemy e) {
		float totalDist = findDistance(e);
		if (totalDist < this.range) {
			return true;
		}
		return false;
	}
	
	protected float findDistance(Enemy e) {
		float xDist = e.getCenterX() - x;
		float yDist = e.getCenterY() - y;
		return (float) Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
	}
	
	public void refreshEnemies(CopyOnWriteArrayList<Enemy> newEnemies){
		enemies = newEnemies;
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
