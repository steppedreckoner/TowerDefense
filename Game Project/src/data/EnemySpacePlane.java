package data;

public class EnemySpacePlane extends Enemy {

	public EnemySpacePlane(int level) {
		super(EnemyType.EnemySpacePlane, level);
	}
	
	@Override
	public void die() {
		alive = false;
		Player.modifyCash(15);
	}

}