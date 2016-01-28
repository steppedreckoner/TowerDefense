package data;

public class ProjectileIceBullet extends Projectile {

	public ProjectileIceBullet(Enemy target, float x, float y) {
		super(ProjectileType.ProjectileIceBullet, target, x, y);
	}

	@Override
	public void doDamage(){
		super.getTarget().setSlowDuration(3f);
		super.doDamage();
		this.getTarget().slow(.25f);
	}
	
}
