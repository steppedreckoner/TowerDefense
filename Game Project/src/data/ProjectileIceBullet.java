package data;

import java.util.concurrent.CopyOnWriteArrayList;

public class ProjectileIceBullet extends Projectile {
	
	private CopyOnWriteArrayList<Enemy> enemies;

	public ProjectileIceBullet(Enemy target, float x, float y, CopyOnWriteArrayList<Enemy> enemies) {
		super(ProjectileType.ProjectileIceBullet, target, x, y);
		this.enemies = enemies;
	}

	@Override
	public void doDamage() {
		super.getTarget().setSlowDuration(3f);
		super.doDamage();
		this.getTarget().slow(.25f);
	}

	@Override
	protected boolean checkCollision() {
		for (Enemy e : enemies) {
			float deltaX = e.getCenterX() - centerX;
			float deltaY = e.getCenterY() - centerY;
			if (Math.hypot(deltaX, deltaY) < e.getWidth()) {
				target = e;
				return true;
			}
		}
		return false;
	}

}
