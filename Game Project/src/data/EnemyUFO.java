package data;

public class EnemyUFO extends Enemy {

	public EnemyUFO(int level) {
		super(EnemyType.EnemyUFO, level);
	}
	
	@Override
	public void die() {
		alive = false;
		Player.modifyCash(20);
	}

}
