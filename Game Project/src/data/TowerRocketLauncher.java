package data;

import java.util.concurrent.CopyOnWriteArrayList;

public class TowerRocketLauncher extends Tower{

	public TowerRocketLauncher(Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
		super(TowerType.RocketTower, startTile, enemies);
	}

	@Override
	protected Enemy acquireTarget() {
		hasTarget = false;
		Enemy closest = null;
		for (int i = 0; i < this.enemies.size(); i++) {
			if (this.enemies.get(i).isAlive()) {
				hasTarget = true;
				return this.enemies.get(i);
			}
		}
		return closest;
	}

	@Override
	public void shoot(Enemy target) {
		this.timeSinceLastShot = 0;
		projectiles.add(new ProjectileRocket(target, x, y, enemies));
	}

}
