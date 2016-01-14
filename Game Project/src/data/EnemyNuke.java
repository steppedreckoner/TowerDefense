package data;

public class EnemyNuke extends Enemy{

	public EnemyNuke(int level) {
		super(EnemyType.EnemyNuke, level);
	}

	@Override
	protected void die() {
		alive = false;
		
	}

}
