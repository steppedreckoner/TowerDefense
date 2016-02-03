package data;

import static helpers.Artist.DrawQuadTexRotate;
import static helpers.Artist.TILE_SIZE;
import static helpers.Clock.Delta;

import java.util.concurrent.CopyOnWriteArrayList;

public class ProjectileRocket extends Projectile{
	
	private float currentSpeed, angle, warheadX, warheadY;
	private CopyOnWriteArrayList<Enemy> enemies;	//Allows for changing targets and splash damage.
	
	public static final float INITIAL_LEAD_SPEED = ProjectileType.ProjectileRocket.speed * .7f;
	private static final float WARHEAD_DIST_FROM_CENTER = 29;
	private static final float HITBOX_SIZE = 12;
	private static final float DAMAGE_RANGE = TILE_SIZE * 4;
	private static final float SPLASH_DAMAGE = ProjectileType.ProjectileRocket.damage;
	private static final float MAX_ACCELERATION = 2 * ProjectileType.ProjectileRocket.speed;	//In 1 second, proj can accelerate by twice the proj max speed pixels/s

	public ProjectileRocket(Enemy target, float x, float y, CopyOnWriteArrayList<Enemy> enemies) {
		super(ProjectileType.ProjectileRocket, target, x, y);
		this.enemies = enemies;
		this.angle = 0;
		this.currentSpeed = INITIAL_LEAD_SPEED;
	}
	
	private void calculateAngle() {
		double angleTemp = Math.atan2(yVelocity, xVelocity);
		angle = (float) Math.toDegrees(angleTemp) - 90;
	}
	
	//Gives a max acceleration so rockets can't turn instantaneously
	//and don't spawn at full speed.
	@Override
	protected void calculateVelocity() {
		float preXVelocity = xVelocity;
		float preYVelocity = yVelocity;
		if (target.isAlive()) {
			//Target approximately where the enemy should be when the rocket gets
			//there, assuming the enemy does not change direction.
			super.calculateVelocity(warheadX, warheadY, 
					target.getCenterX() + target.getDirections()[0] * target.getSpeed() * (findDistance(target) / currentSpeed),
					target.getCenterY() + target.getDirections()[1] * target.getSpeed() * (findDistance(target) / currentSpeed));
		} else {
			this.setAlive(false);
		}
		calculateAngle();
		float deltaVX = xVelocity - preXVelocity;
		float deltaVY = yVelocity - preYVelocity;
		float magnitude = (float) Math.hypot(deltaVX, deltaVY);
		
//																	Air Resistance: 0-1		 
		float maxAccel = (float) (Delta() * MAX_ACCELERATION * (1 - (.9 * currentSpeed / speed)));
		if (magnitude > maxAccel) {
			//Include Delta() to keep acceleration based on time and not refresh rate.
			xVelocity = preXVelocity + maxAccel * deltaVX / magnitude;
			yVelocity = preYVelocity + maxAccel * deltaVY / magnitude;
		}
		currentSpeed = (float) Math.hypot(xVelocity, yVelocity);
	}
	
	private void updateWarheadPosition() {
		warheadX = (float) (centerX + (Math.cos(Math.toRadians(angle + 90)) * WARHEAD_DIST_FROM_CENTER));
		warheadY = (float) (centerY + (Math.sin(Math.toRadians(angle + 90)) * WARHEAD_DIST_FROM_CENTER));
	}
	
	@Override
	protected boolean checkCollision() {
		if (findDistance(target) < HITBOX_SIZE) {
			return true;
		}
		return false;
	}
	
	protected float findDistance(Enemy e) {
		float xDist = e.getCenterX() - warheadX;
		float yDist = e.getCenterY() - warheadY;
		return (float) Math.hypot(xDist, yDist);
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
		DrawQuadTexRotate(super.texture, x, y, texture.getImageWidth(), texture.getImageHeight(), angle);
	}
	
	@Override
	public void update() {
		confirmTarget();
		updateWarheadPosition();
		calculateVelocity();
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
