package data;

public class ProjectileIceBullet extends Projectile {

	public ProjectileIceBullet(Enemy target, float x, float y) {
		super(ProjectileType.ProjectileIceBullet, target, x, y);
	}

	@Override
	public void doDamage(){
		super.getTarget().setSpeed(5f);
		super.doDamage();
	}
	
}
