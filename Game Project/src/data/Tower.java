package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.DrawQuadTexRotate;
import static helpers.Clock.Delta;

import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.opengl.Texture;

import helpers.Artist;

public abstract class Tower implements Entity {

	protected float x, y, barrelX, barrelY;
	protected int barrelLength;
	protected float timeSinceLastShot;
	protected float rateOfFire;
	protected int range;
	private float angle;
	private int width, height;
	private int cost;
	protected Enemy target;
	private Texture[] textures;
	private Tile startTile;
	protected CopyOnWriteArrayList<Projectile> projectiles;
	protected boolean hasTarget, isBuffed;
	protected TowerType type;
	protected int level, maxLevel, shotsFired, totalKills;
	protected String towerName;
	
	private static int TotalTowersPlaced = 0, CurrentTowers = 0;

	public Tower(TowerType type, Tile startTile) {
		this.textures = type.textures;
		this.range = type.getLevelListRange()[1];
		this.rateOfFire = type.getLevelListFireRate()[1];
		this.startTile = startTile;
		this.x = this.startTile.getX();
		this.y = this.startTile.getY();
		this.barrelLength = type.barrelLength;
		this.barrelX = getCenterX();
		this.barrelY = getCenterY() + barrelLength;
		this.width = startTile.getWidth();
		this.height = startTile.getHeight();
		this.hasTarget = false;
		this.isBuffed = false;
		this.timeSinceLastShot = this.rateOfFire;
		this.projectiles = new CopyOnWriteArrayList<Projectile>();
		this.angle = 0f;
		this.cost = type.getLevelListUpgradeCost()[0];
		this.type = type;
		this.level = 1;
		this.maxLevel = type.maxLevel;
		this.shotsFired = 0;
		this.totalKills = 0;
		TotalTowersPlaced++;
		CurrentTowers++;
		towerName = "Tower " + Integer.toString(TotalTowersPlaced);
		System.out.println(towerName);
		System.out.println("Currently " + CurrentTowers + " towers");
		Player.TowerList.add(this);
	}
	
	public void delete() {
		Player.ModifyCash((int) (-cost * .8f));	//negative cost to add money
		Player.TowerList.remove(this);
		startTile.setHasTower(false);
		CurrentTowers--;	//There might be a way to abuse this, but it should work for now.
	}
	
	public void levelUp() {
		if (level < maxLevel){
			if (Player.ModifyCash(type.getLevelListUpgradeCost()[level])) {
				level++;
				rateOfFire = type.getLevelListFireRate()[level];
				range = type.getLevelListRange()[level];
			}	
		}
	}

	public void update() {
		target = acquireTarget();
		timeSinceLastShot += Delta();
		if (this.hasTarget) {
			angle = calculateAngle();
			if (timeSinceLastShot > rateOfFire && this.isInRange(target)) {
				shoot(target);
				timeSinceLastShot = 0;
				shotsFired++;
			}
		}
		for (Projectile p : projectiles) {
			if (p.isAlive()){
				p.update();
			}
			else{
				if (p.killedEnemy()) {
					totalKills++;
				}
				projectiles.remove(p);
			}
		}
		this.draw();
	}
	
	//Used to illustrate towers that are about to be placed
	public static void PlacementDraw(TowerType type, int x, int y){
		for (int i = 0; i < type.textures.length; i++){
			DrawQuadTex(type.textures[i], x - (type.width / 2), y - (type.height / 2), type.width, type.height);
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

	protected float calculateAngle() {
		double angleTemp = Math.atan2(target.getY() - y, target.getX() - x);
		barrelX = (float) (getCenterX() + barrelLength * Math.cos(angleTemp));	//angleTemp still in radians
		barrelY = (float) (getCenterY() + barrelLength * Math.sin(angleTemp));	//Do this before rotating texture to fit with coordinates
		return (float) Math.toDegrees(angleTemp) - 90;
	}
	
	public void rateOfFireMultiplier(float mult){
		rateOfFire = rateOfFire / mult;
		buff();
	}
	
	public void resetRateOfFire(){
		rateOfFire = type.getLevelListFireRate()[level];
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
