package data;

import java.util.concurrent.CopyOnWriteArrayList;

public class TowerIce extends Tower{
	
	public TowerIce(Tile startTile, CopyOnWriteArrayList<Enemy> enemies){
		super(TowerType.IceTower, startTile, enemies);
	}
	
	@Override
	public void shoot(Enemy target) {
		this.timeSinceLastShot = 0;
		projectiles.add(new ProjectileIceBullet(target, x, y));
	}

}
