package data;

import java.util.concurrent.CopyOnWriteArrayList;

public class TowerRocketLauncher extends Tower{
	
	private int shotsSinceNuke;
	private static final int NUKE_FIRE_RATE = 5;

	public TowerRocketLauncher(Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
		super(TowerType.RocketTower, startTile, enemies);
	}

	@Override
	protected Enemy acquireTarget() {
		hasTarget = false;
		Enemy closest = null;
		for (int i = 0; i < this.enemies.size(); i++) {
			if (this.enemies.get(i).isAlive()) {
				hasTarget = true;
				return this.enemies.get(i);
			}
		}
		return closest;
	}
	
	@Override
	protected float calculateAngle() {
		float xLead = target.getDirections()[0] * target.getSpeed() * (findDistance(target) / ProjectileRocket.INITIAL_LEAD_SPEED);
		float yLead = target.getDirections()[1] * target.getSpeed() * (findDistance(target) / ProjectileRocket.INITIAL_LEAD_SPEED);
		double angleTemp = Math.atan2(target.getY() + xLead - y, target.getX() + yLead - x);
		barrelX = (float) (getCenterX() + barrelLength * Math.cos(angleTemp));	//angleTemp still in radians
		barrelY = (float) (getCenterY() + barrelLength * Math.sin(angleTemp));	//Do this before rotating texture to fit with coordinates
		return (float) Math.toDegrees(angleTemp) - 90;
	}

	@Override
	public void shoot(Enemy target) {
		this.timeSinceLastShot = 0;
		if (shotsSinceNuke >= NUKE_FIRE_RATE) {
			projectiles.add(new ProjectileRocketNuke(target, barrelX - (ProjectileType.ProjectileRocketNuke.texture.getImageWidth() / 2), 
					barrelY - (ProjectileType.ProjectileRocketNuke.texture.getImageHeight() / 2), enemies));
			shotsSinceNuke = 0;
		} else {
			projectiles.add(new ProjectileRocket(target, barrelX - (ProjectileType.ProjectileRocket.texture.getImageWidth() / 2), 
				barrelY - (ProjectileType.ProjectileRocket.texture.getImageHeight() / 2), enemies));
			shotsSinceNuke++;
		}
	}

}
