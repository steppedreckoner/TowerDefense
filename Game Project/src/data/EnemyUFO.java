package data;

import static helpers.Artist.QuickLoad;

public class EnemyUFO extends Enemy {

	public EnemyUFO(int level) {
			super(EnemyType.EnemyUFO, level);
		if (this.getLevel() > 1){
			this.setTexture(QuickLoad("UFO2"));
		}
	}
	
	@Override
	public void die() {
		alive = false;
		Player.ModifyCash(20);
	}
	
	
}
