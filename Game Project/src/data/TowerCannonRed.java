package data;

import java.util.concurrent.CopyOnWriteArrayList;

public class TowerCannonRed extends Tower{
	
	public TowerCannonRed(Tile startTile, CopyOnWriteArrayList<Enemy> enemies){
		super(TowerType.CannonRed, startTile, enemies);
	}
	
	@Override
	public void shoot(Enemy target){
		this.timeSinceLastShot = 0;
		projectiles.add(new ProjectileCannonRed(target, x, y));
	}

	@Override
	protected Enemy acquireTarget() {
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
	
}
