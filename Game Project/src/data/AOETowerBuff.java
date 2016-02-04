package data;

import static data.Player.TowerList;
import static helpers.Artist.DrawQuadTexAlpha;

public class AOETowerBuff extends AOE{
	
	private float rofIncrease;
//	private float damageIncrease;

	public AOETowerBuff(float x, float y) {
		super(AOEType.TowerBuff, x, y);
		rofIncrease = 2f;
	}

	@Override
	protected void doEffect() {
		for (Tower t : TowerList){
			if (isInRange(t.getCenterX(), t.getCenterY()) && !t.isBuffed()){
				t.rateOfFireMultiplier(rofIncrease);
			}
		}
	}
	
	@Override
	protected void endEffect() {
		for (Tower t : TowerList){
			if (t.isBuffed()){
				t.debuff();
			}
		}
	}

	@Override
	public void draw() {
		DrawQuadTexAlpha(activeTex, x - (width / 2), y - (width / 2), width, height, .75f * (duration - timeAlive) / duration);
	}

}
