package data;

import java.util.concurrent.CopyOnWriteArrayList;

public class ProjectileCannonRed extends Projectile {

	private CopyOnWriteArrayList<Enemy> enemies;

	public ProjectileCannonRed(Enemy target, float x, float y, CopyOnWriteArrayList<Enemy> enemies) {
		super(ProjectileType.ProjectileRedIceBullet, target, x, y);
		this.enemies = enemies;
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
