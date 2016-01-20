package data;

import java.util.concurrent.CopyOnWriteArrayList;

public class TowerCannonBlue extends Tower {

	public TowerCannonBlue(Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
		super(TowerType.CannonBlue, startTile, enemies);
	}

	@Override
	public void shoot(Enemy target) {
		this.timeSinceLastShot = 0;
		projectiles.add(new ProjectileCannonBullet(target, x, y));
	}

	@Override
	protected Enemy acquireTarget() {
		hasTarget = false;
		Enemy closest = null;
		for (int i = 0; i < this.enemies.size(); i++){
			if (this.isInRange(this.enemies.get(i))){
				hasTarget = true;
				return this.enemies.get(i);
			}
		}
		return closest;
	}

}
