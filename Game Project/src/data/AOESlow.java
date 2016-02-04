package data;

import static data.WaveManager.EnemyList;
import static helpers.Artist.DrawQuadTexAlpha;

import java.util.ArrayList;

public class AOESlow extends AOE{

	private float speedMult;
	private ArrayList<Enemy> slowedEnemyList;

	public AOESlow(float x, float y) {
		super(AOEType.Slow, x, y);
		speedMult = .2f;
		slowedEnemyList = new ArrayList<Enemy>();
	}

	@Override
	protected void doEffect() {
		for (Enemy e : EnemyList){
			if (!e.isSlowed() && isInRange(e.getCenterX(), e.getCenterY())){
				slowedEnemyList.add(e);
				e.setSlowDuration(999f);
				e.slow(speedMult);
			}
		}
	}

	@Override
	protected void endEffect() {
		for (Enemy e : slowedEnemyList){
			e.setSlowDuration(0f);
		}
	}

	@Override
	public void draw() {
		DrawQuadTexAlpha(activeTex, x - (width / 2), y - (width / 2), width, height, (.6f * (duration - timeAlive) / duration) + .15f);
	}
	
}
