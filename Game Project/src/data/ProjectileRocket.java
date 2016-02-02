package data;

import static helpers.Artist.DrawQuadTexRotate;
import static helpers.Artist.TILE_SIZE;

import java.util.concurrent.CopyOnWriteArrayList;

public class ProjectileRocket extends Projectile{
	
	private float angle;
	private CopyOnWriteArrayList<Enemy> enemies;	//Allows for changing targets and splash damage.
	
	private static final float DAMAGE_RANGE = TILE_SIZE * 4;
	private static final float SPLASH_DAMAGE = ProjectileType.ProjectileRocket.damage;
	private static final float MAX_ACCELERATION = 2f;

	public ProjectileRocket(Enemy target, float x, float y, CopyOnWriteArrayList<Enemy> enemies) {
		super(ProjectileType.ProjectileRocket, target, x, y);
		this.angle = 0;
		this.enemies = enemies;
	}
	
	private void calculateAngle() {
		double angleTemp = Math.atan2(yVelocity, xVelocity);
		angle = (float) Math.toDegrees(angleTemp) - 90;
	}
	
	@Override
	protected void calculateVelocity() {
		float preXVelocity = xVelocity;
		float preYVelocity = yVelocity;
		super.calculateVelocity();
		float deltaX = xVelocity - preXVelocity;
		float deltaY = yVelocity - preYVelocity;
		float magnitude = (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
		if (magnitude > MAX_ACCELERATION) {
			xVelocity = preXVelocity + MAX_ACCELERATION * deltaX / magnitude;
			yVelocity = preYVelocity + MAX_ACCELERATION * deltaY / magnitude;
		}
	}
	
	
	protected float findDistance(Enemy e) {
		float xDist = e.getCenterX() - x;
		float yDist = e.getCenterY() - y;
		return (float) Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
	}
	
	public void confirmTarget() {
		if (!target.isAlive()) {
			Enemy tempTarget = null;
			float closestDistance = -1;
			for (Enemy e : enemies) {
				if (((findDistance(e) < closestDistance) || (closestDistance < 0)) && e.isAlive()) {
					closestDistance = findDistance(e);
					tempTarget = e;
				}
			}
			if (tempTarget != null) {
				target = tempTarget;
			}
		}
	}
	
	@Override
	public void draw() {
		DrawQuadTexRotate(super.texture, x, y, 48, 48, angle);
	}
	
	@Override
	public void update() {
		confirmTarget();
		this.calculateAngle();
		super.update();
	}
	
	@Override
	public void doDamage() {
		super.doDamage();
		for (Enemy e : enemies) {
			if (findDistance(e) <= DAMAGE_RANGE) {
				//If the enemy is within 1 tile of the strike, do normal splash damage.
				//Otherwise, scale by inverse square. Prevents increased damage for close enemies.
				e.decreaseHealth((float) (SPLASH_DAMAGE / Math.max(Math.pow(findDistance(e) / TILE_SIZE, 2), 1)));
			}
		}
	}

}
