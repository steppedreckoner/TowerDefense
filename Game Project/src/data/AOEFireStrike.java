package data;

import static data.WaveManager.EnemyList;
import static helpers.Artist.DrawQuadTexAlpha;
import static helpers.Clock.Delta;

public class AOEFireStrike extends AOE{
	
	private int damage;

	public AOEFireStrike(float x, float y) {
		super(AOEType.FireStrike, x, y);
		damage = 100;
	}

	@Override
	protected void doEffect() {
		for (Enemy e : EnemyList){
			if (isInRange(e.getCenterX(), e.getCenterY())){
				e.decreaseHealth(damage * Delta() / duration);
			}
		}
	}
	
	@Override
	protected void endEffect() {
		//Do nothing; damage is permanent.
	}	

	@Override
	public void draw() {
		DrawQuadTexAlpha(activeTex, x - (width / 2), y - (width / 2), width, height, (duration - timeAlive) / duration);
	}

	

}
