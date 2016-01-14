package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.DrawQuadTexRotate;
import static helpers.Artist.QuickLoad;
import static helpers.Artist.TILE_SIZE;
import static helpers.Clock.Delta;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.opengl.Texture;

public class TowerCannon {

	private float x, y, timeSinceLastShot, rateOfFire, angle;
	private int width, height, damage, range;
	private Texture baseTexture, cannonTexture;
	private Tile startTile;
	private ArrayList<Projectile> projectiles;
	private CopyOnWriteArrayList<Enemy> enemies;
	private Enemy target;
	private int targetIndex;
	private boolean hasTarget;

	public TowerCannon(Texture baseTexture, Tile startTile, int damage, int range, CopyOnWriteArrayList<Enemy> enemies) {
		this.baseTexture = baseTexture;
		this.cannonTexture = QuickLoad("cannongun");
		this.startTile = startTile;
		this.y = startTile.getY();
		this.x = startTile.getX();
		this.width = (int) startTile.getWidth();
		this.height = (int) startTile.getHeight();
		this.damage = damage;
		this.range = range;
		this.rateOfFire = 2;
		this.timeSinceLastShot = 0;
		this.projectiles = new ArrayList<Projectile>();
		this.enemies = enemies;
		targetIndex = 0;
		this.hasTarget = false;
	}

	public void RefreshEnemies(CopyOnWriteArrayList<Enemy> newEnemies) {
		enemies = newEnemies;
	}

	private Enemy acquireTarget() {
		hasTarget = false;
		Enemy closest = null;
		float closestDistance = -1;
		for (Enemy e : enemies) {
			if (findDistance(e) < closestDistance || closestDistance < 0 && e.isAlive()) {
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

	private void Shoot() {
		timeSinceLastShot = 0;
		projectiles.add(
				new ProjectileCannonBullet(target, x + TILE_SIZE / 4, y + TILE_SIZE / 4));
	}

	public void Update() {
		target = acquireTarget();
		timeSinceLastShot += Delta();
		if (this.hasTarget) {
			angle = calculateAngle();
			if (timeSinceLastShot > rateOfFire && this.isInRange(target)) {
				Shoot();
			}
		}
		for (Projectile p : projectiles) {
			p.Update();
		}
		Draw();
	}

	public void Draw() {
		DrawQuadTex(baseTexture, x, y, width, height);
		DrawQuadTexRotate(cannonTexture, x, y, width, height, angle);
	}

}
