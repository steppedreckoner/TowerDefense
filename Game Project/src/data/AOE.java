package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.DrawQuadTex;
import static helpers.Clock.Delta;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AOE implements Entity{
	
	protected float timeAlive, duration, range;
	protected float x, y;
	protected int width, height;
	protected Texture placingTex;
	protected Texture activeTex;
	protected boolean isAlive;
	
	protected CopyOnWriteArrayList<Enemy> enemyList;
	protected ArrayList<Tower> towerList;
	
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
		enemyList = new CopyOnWriteArrayList<Enemy>();
		towerList = new ArrayList<Tower>();
	}
	
	public void setEnemyList(CopyOnWriteArrayList<Enemy> newEnemies){
		enemyList = newEnemies;
	}
	
	public void setTowerList(ArrayList<Tower> newTowers){
		towerList = newTowers;
	}
	
	public void update(){
		if (isAlive){
			draw();
			doEffect();
			timeAlive += Delta();
			if (timeAlive > duration){
				endEffect();
				isAlive = false;
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
	
	public void refreshEnemies(CopyOnWriteArrayList<Enemy> newEnemies){
		enemyList = newEnemies;
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
