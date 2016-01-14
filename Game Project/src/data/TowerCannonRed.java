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
}
