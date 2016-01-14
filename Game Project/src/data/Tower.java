package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;
import static helpers.Clock.Delta;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Tower implements Entity {

	protected float x;
	protected float y;
	protected float timeSinceLastShot;
	private float rateOfFire;
	private float angle;
	private int width, height, damage, range;
	protected Enemy target;
	private Texture[] textures;
	private Tile startTile;
	private CopyOnWriteArrayList<Enemy> enemies;
	protected ArrayList<Projectile> projectiles;
	private boolean hasTarget;

	public Tower(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
		this.textures = type.textures;
//		this.damage = type.damage;
		this.range = type.range;
		this.rateOfFire = type.rateOfFire;
		this.startTile = startTile;
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = startTile.getWidth();
		this.height = startTile.getHeight();
		this.enemies = enemies;
		this.hasTarget = false;
		this.timeSinceLastShot = 0f;
		this.projectiles = new ArrayList<Projectile>();
		this.angle = 0f;
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
			p.Update();
		}
		this.draw();
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

	private Enemy acquireTarget() {
		hasTarget = false;
		Enemy closest = null;
		float closestDistance = -1;
		for (Enemy e : enemies) {
			if (((findDistance(e) < closestDistance) || (closestDistance < 0)) && e.isAlive()) {
				closestDistance = findDistance(e);
				closest = e;
				hasTarget = true;
			}
		}
		return closest;
	}

	private boolean isInRange(Enemy e) {
		float totalDist = findDistance(e);
		if (totalDist < this.range) {
			return true;
		}
		return false;
	}

	private float findDistance(Enemy e) {
		float xDist = e.getX() - x;
		float yDist = e.getY() - y;
		return (float) Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
	}

	private boolean hasTarget() {
		if (this.target == null) {
			return false;
		}
		return true;
	}

	private float calculateAngle() {
		double angleTemp = Math.atan2(target.getY() - y, target.getX() - x);
		return (float) Math.toDegrees(angleTemp) - 90;
	}

	//Overide in subclasses
	
	public abstract void shoot(Enemy target);

	public void RefreshEnemies(CopyOnWriteArrayList<Enemy> newEnemies) {
		enemies = newEnemies;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
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

}
