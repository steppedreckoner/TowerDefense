package data;

import static data.WaveManager.EnemyList;

public class TowerIce extends Tower{
	
	public TowerIce(Tile startTile){
		super(TowerType.IceTower, startTile);
	}
	
	@Override
	public void shoot(Enemy target) {
		this.timeSinceLastShot = 0;
		projectiles.add(new ProjectileIceBullet(target, barrelX - (ProjectileType.ProjectileIceBullet.texture.getImageWidth() / 2), 
				barrelY - (ProjectileType.ProjectileIceBullet.texture.getImageWidth() / 2), EnemyList));
	}

	@Override
	protected Enemy acquireTarget() {
		hasTarget = false;
		for (int i = 0; i < EnemyList.size(); i++){
			if (this.isInRange(EnemyList.get(i)) && !EnemyList.get(i).isSlowed()){	//Earliest spawned that is not already slowed
				hasTarget = true;
				return EnemyList.get(i);
			}
		}
		if (EnemyList.size() > 0){
			hasTarget = true;
			return EnemyList.get(0);
		}
		return null;
	}

}
