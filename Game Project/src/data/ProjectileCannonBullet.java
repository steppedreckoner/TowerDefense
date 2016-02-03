package data;

public class ProjectileCannonBullet extends Projectile {

	public ProjectileCannonBullet(Enemy target, float x, float y) {
		super(ProjectileType.ProjectileCannon, target, x, y);
	}
	
	@Override
	public void update() {
		calculateVelocity();
		super.update();
	}

}
