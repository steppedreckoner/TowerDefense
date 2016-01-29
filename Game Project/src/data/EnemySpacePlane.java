package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.DrawQuadTexRotate;

public class EnemySpacePlane extends Enemy {
	
	public static final int[] KILL_CASH_LIST = {0, 25, 35, 45, 60};
	public static final int[] KILL_EXP_LIST = {0, 40, 60, 90, 140};
	public static final int MAX_LEVEL = Math.min(KILL_CASH_LIST.length, KILL_EXP_LIST.length) - 1;

	public EnemySpacePlane(int level) {
		super(EnemyType.EnemySpacePlane, level);
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

	//This enemy type needs to point in the direction it's moving
	@Override
	public void draw() {
		DrawQuadTexRotate(texture, this.getX(), this.getY(), this.getWidth(), this.getHeight(), (float) Math.toDegrees(Math.atan2(this.getDirections()[1], this.getDirections()[0])) + 90);
		DrawQuadTex(healthBackground, this.getX() + 5, this.getY() - 16, EnemyType.EnemySpacePlane.width - 10, 8);
		DrawQuadTex(healthForeground, this.getX() + 5, this.getY() - 16, (EnemyType.EnemySpacePlane.width - 10) * (this.getHealth() / EnemyType.EnemySpacePlane.startHealth), 8);
		DrawQuadTex(healthBorder, this.getX() + 5, this.getY() - 16, EnemyType.EnemySpacePlane.width - 10, 8);
	}

}