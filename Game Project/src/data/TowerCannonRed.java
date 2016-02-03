package data;

import java.util.concurrent.CopyOnWriteArrayList;

public class TowerCannonRed extends Tower{
	
	private int level;
	
	public TowerCannonRed(Tile startTile, CopyOnWriteArrayList<Enemy> enemies){
		super(TowerType.CannonRed, startTile, enemies);
		this.level = 1;
	}
	
	@Override
	public void shoot(Enemy target){
		this.timeSinceLastShot = 0;
		projectiles.add(new ProjectileCannonRed(target, barrelX - (ProjectileType.ProjectileRedIceBullet.texture.getImageWidth() / 2), 
				barrelY - (ProjectileType.ProjectileRedIceBullet.texture.getImageHeight() / 2), enemies));
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
	
	public int getLevel() {
		return level;
	}
	
}
