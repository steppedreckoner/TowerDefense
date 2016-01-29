package data;

public class EnemyNuke extends Enemy{
	
	public static final int[] KILL_CASH_LIST = {0, 50, 60, 70, 100};
	public static final int[] KILL_EXP_LIST = {0, 50, 70, 100, 150};
	public static final int MAX_LEVEL = Math.min(KILL_CASH_LIST.length, KILL_EXP_LIST.length) - 1;

	public EnemyNuke(int level) {
		super(EnemyType.EnemyNuke, level);
		if (level > MAX_LEVEL){
			this.setLevel(MAX_LEVEL);
		}
	}

	@Override
	protected void die() {
		alive = false;
		Player.ModifyCash(KILL_CASH_LIST[this.getLevel()]);
		Player.ModifyExp(KILL_EXP_LIST[this.getLevel()]);
	}

}
