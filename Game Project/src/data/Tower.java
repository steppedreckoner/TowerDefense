package data;

import static helpers.Artist.DrawQuadTexRotate;
import static helpers.Clock.Delta;

import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.opengl.Texture;

import helpers.Artist;

public abstract class Tower implements Entity {

	protected float x, y;
	protected float timeSinceLastShot;
	private float rateOfFire;
	private float angle;
	private int width, height, range, cost;
	protected Enemy target;
	private Texture[] textures;
	private Tile startTile;
	protected CopyOnWriteArrayList<Enemy> enemies;
	protected CopyOnWriteArrayList<Projectile> projectiles;
	protected boolean hasTarget, isBuffed;
	private TowerType type;

	public Tower(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
		this.textures = type.textures;
		this.range = type.range;
		this.rateOfFire = type.rateOfFire;
		this.startTile = startTile;
		this.x = this.startTile.getX();
		this.y = this.startTile.getY();
		this.width = startTile.getWidth();
		this.height = startTile.getHeight();
		this.enemies = enemies;
		this.hasTarget = false;
		this.isBuffed = false;
		this.timeSinceLastShot = this.rateOfFire;
		this.projectiles = new CopyOnWriteArrayList<Projectile>();
		this.angle = 0f;
		this.cost = type.getCost();
		this.type = type;
	}

	public void update() {
		target = acquireTarget();
		timeSinceLastShot += Delta();
		if (this.hasTarget) {
			angle = calculateAngle();
			if (timeSinceLastShot > rateOfFire && this.isInRange(target)) {
				shoot(target);
				timeSinceLastShot = 0;
			}
		}
		for (Projectile p : projectiles) {
			if (p.isAlive()){
				p.update();
			}
			else{
				projectiles.remove(p);
			}
		}
		this.draw();
	}
	
	//Used to illustrate towers that are about to be placed
	public static void PlacementDraw(TowerType type, int x, int y){
		for (int i = 0; i < type.textures.length; i++){
			DrawQuadTexRotate(type.textures[i], x, y, 64, 64, 0);
		}
	}

	public void draw() {
		for (int i = 0; i < textures.length; i++) {
			if (i == 0) {
				DrawQuadTexRotate(textures[i], x, y, width, height, 0);
			} 
			else {
				DrawQuadTexRotate(textures[i], x, y, width, height, angle);
			}
		}
	}

	protected abstract Enemy acquireTarget();
	
	public abstract void shoot(Enemy target);

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

	private float calculateAngle() {
		double angleTemp = Math.atan2(target.getY() - y, target.getX() - x);
		return (float) Math.toDegrees(angleTemp) - 90;
	}

	public void refreshEnemies(CopyOnWriteArrayList<Enemy> newEnemies) {
		enemies = newEnemies;
	}
	
	public void rateOfFireMultiplier(float mult){
		rateOfFire = rateOfFire / mult;
		buff();
	}
	
	public void resetRateOfFire(){
		rateOfFire = type.rateOfFire;
	}
	
	public boolean isBuffed(){
		return isBuffed;
	}
	
	public void buff(){
		isBuffed = true;
	}
	
	public void debuff(){
		resetRateOfFire();
		isBuffed = false;
	}
	
	public int getCost(){
		return cost;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public float getCenterX(){
		return x + Artist.TILE_SIZE / 2;
	}
	
	public float getCenterY(){
		return y + Artist.TILE_SIZE / 2;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public Enemy getTarget(){
		return target;
	}
	
	public Tile getStartTile(){
		return startTile;
	}

}
