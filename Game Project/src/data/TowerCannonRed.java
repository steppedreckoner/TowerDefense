package data;

import static data.WaveManager.EnemyList;

public class TowerCannonRed extends Tower{
	
	public TowerCannonRed(Tile startTile){
		super(TowerType.CannonRed, startTile);
	}
	
	@Override
	public void shoot(Enemy target){
		this.timeSinceLastShot = 0;
		projectiles.add(new ProjectileCannonRed(target, barrelX - (ProjectileType.ProjectileRedIceBullet.texture.getImageWidth() / 2), 
				barrelY - (ProjectileType.ProjectileRedIceBullet.texture.getImageHeight() / 2), EnemyList));
	}

	@Override
	protected Enemy acquireTarget() {
		hasTarget = false;
		Enemy closest = null;
		float closestDistance = -1;
		for (Enemy e : EnemyList) {
			if (((findDistance(e) < closestDistance) || (closestDistance < 0)) && e.isAlive()) {
				closestDistance = findDistance(e);
				closest = e;
				hasTarget = true;
			}
		}
		return closest;
	}
	
}
