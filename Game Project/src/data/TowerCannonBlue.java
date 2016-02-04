package data;

import static data.WaveManager.EnemyList;

public class TowerCannonBlue extends Tower {

	public TowerCannonBlue(Tile startTile) {
		super(TowerType.CannonBlue, startTile);
	}

	@Override
	public void shoot(Enemy target) {
		this.timeSinceLastShot = 0;
		projectiles.add(new ProjectileCannonBullet(target, barrelX - (ProjectileType.ProjectileCannon.texture.getImageWidth() / 2), 
				barrelY - (ProjectileType.ProjectileCannon.texture.getImageWidth() / 2)));
	}

	@Override
	protected Enemy acquireTarget() {
		hasTarget = false;
		Enemy target = null;
		for (int i = 0; i < EnemyList.size(); i++){
			if (this.isInRange(EnemyList.get(i))){
				hasTarget = true;
				return EnemyList.get(i);
			}
		}
		return target;
	}

}
