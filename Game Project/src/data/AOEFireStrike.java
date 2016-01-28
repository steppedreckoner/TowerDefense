package data;

import static helpers.Artist.DrawQuadTexAlpha;
import static helpers.Clock.Delta;

import java.util.concurrent.CopyOnWriteArrayList;

public class AOEFireStrike extends AOE{
	
	private int damage;
	private static float cooldownTimer;

	public AOEFireStrike(float x, float y, CopyOnWriteArrayList<Enemy> enemies) {
		super(AOEType.FireStrike, x, y, enemies);
		damage = 100;
	}

	@Override
	protected void doEffect() {
		for (Enemy e : enemies){
			if (isInRange(e)){
				e.decreaseHealth(damage * Delta() / duration);
			}
		}
		
	}

	@Override
	public void draw() {
		DrawQuadTexAlpha(activeTex, x - (width / 2), y - (width / 2), width, height, (duration - timeAlive) / duration);
	}
	
	public void resetCoolDown(){
		cooldownTimer = cooldown;
	}
	
	public void incrementTimer(float time){
		cooldownTimer -= time;
	}
	
	public static float CooldownPercent(){
		return (cooldownTimer / cooldown);
	}
	
	public boolean cooldownComplete(){
		return (cooldownTimer <= 0);
	}

}
