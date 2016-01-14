package data;

import java.util.concurrent.CopyOnWriteArrayList;

public class TowerCannonBlue extends Tower{

	public TowerCannonBlue(Tile startTile, CopyOnWriteArrayList<Enemy> enemies){
		super(TowerType.CannonBlue, startTile, enemies);
	}
	
	@Override
	public void shoot(Enemy target){
		this.timeSinceLastShot = 0;
		projectiles.add(new ProjectileCannonBullet(target, x, y));
	}

}
