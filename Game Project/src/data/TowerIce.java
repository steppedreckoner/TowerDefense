package data;

import java.util.concurrent.CopyOnWriteArrayList;

public class TowerIce extends Tower{
	
	public TowerIce(Tile startTile, CopyOnWriteArrayList<Enemy> enemies){
		super(TowerType.IceTower, startTile, enemies);
	}
	
	@Override
	public void shoot(Enemy target) {
		this.timeSinceLastShot = 0;
		projectiles.add(new ProjectileIceBullet(target, barrelX - (ProjectileType.ProjectileIceBullet.texture.getImageWidth() / 2), 
				barrelY - (ProjectileType.ProjectileIceBullet.texture.getImageWidth() / 2), enemies));
	}

	@Override
	protected Enemy acquireTarget() {
		hasTarget = false;
		for (int i = 0; i < this.enemies.size(); i++){
			if (this.isInRange(this.enemies.get(i)) && !this.enemies.get(i).isSlowed()){	//Earliest spawned that is not already slowed
				hasTarget = true;
				return this.enemies.get(i);
			}
		}
		if (this.enemies.size() > 0){
			hasTarget = true;
			return this.enemies.get(0);
		}
		return null;
	}

}
