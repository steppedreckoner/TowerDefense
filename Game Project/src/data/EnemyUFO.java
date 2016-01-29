package data;

public class EnemyUFO extends Enemy {
	
	public static final int[] KILL_CASH_LIST = {0, 20, 30, 40, 60};
	public static final int[] KILL_EXP_LIST = {0, 25, 40, 65, 100};
	public static final int MAX_LEVEL = Math.min(KILL_CASH_LIST.length, KILL_EXP_LIST.length) - 1;

	public EnemyUFO(int level) {
		super(EnemyType.EnemyUFO, level);
		if (level > MAX_LEVEL){
			this.setLevel(MAX_LEVEL);
		}
	}
	
	@Override
	public void die() {
		alive = false;
		Player.ModifyCash(KILL_CASH_LIST[this.getLevel()]);
		Player.ModifyExp(KILL_EXP_LIST[this.getLevel()]);
	}
	
}
