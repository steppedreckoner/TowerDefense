package data;

import org.newdawn.slick.opengl.Texture;

import helpers.Artist;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.DrawQuadTexAlpha;
import static helpers.Artist.QuickLoad;

import java.util.concurrent.CopyOnWriteArrayList;

public enum AOEType {
	
	FireStrike(QuickLoad("aoefirestrike"), QuickLoad("aoefirestrikeoutline"), 4f, 2.5f * Artist.TILE_SIZE, 15f){
		
		private float cooldownTimer; 
		
		@Override
		public AOE makeAOE(float x, float y, CopyOnWriteArrayList<Enemy> enemies) {
			return new AOEFireStrike(x, y, enemies);
		}

		@Override
		public void PlacementDraw(float x, float y) {
			DrawQuadTexAlpha(FireStrike.placingTex, x - (FireStrike.width / 2), y - (FireStrike.height / 2), FireStrike.width, FireStrike.height, 1 - cooldownPercent());			
			DrawQuadTex(QuickLoad("cooldownbackground"), x - (64 / 2), y + 70, 64, 8);
			DrawQuadTex(QuickLoad("cooldownforeground"), x - (64 / 2), y + 70, 64 * (1 - cooldownPercent()), 8);
			DrawQuadTex(QuickLoad("cooldownborder"), x - (64 / 2), y + 70, 64, 8);
		}
		
		@Override
		public void resetCooldown() {
			cooldownTimer = FireStrike.cooldown;
		}

		@Override
		public void incrementCooldown(float time) {
			if (cooldownTimer <= 0){
				cooldownTimer = 0;
			}
			cooldownTimer -= time;
		}
		
		@Override
		public float cooldownPercent(){
			if (cooldownTimer < 0){
				return 0f;
			}
			return (cooldownTimer / FireStrike.cooldown);
		}
		
		@Override
		public boolean cooldownComplete(){
			return cooldownTimer <= 0;
		}

	},
	
	NULL(QuickLoad("aoenull"), QuickLoad("aoenull"), 99999f, 0f, 0f){
		
		@Override
		public AOE makeAOE(float x, float y, CopyOnWriteArrayList<Enemy> enemies){
			System.out.println("Tried AOENULL");
			return null;
		}

		@Override
		public void PlacementDraw(float x, float y) {
		}

		@Override
		public void resetCooldown() {
		}

		@Override
		public void incrementCooldown(float time) {
		}

		@Override
		public float cooldownPercent() {
			return 0;
		}

		@Override
		public boolean cooldownComplete() {
			return false;
		}
		
	};
	
	float duration, range, cooldown;
	int width, height;
	Texture activeTex, placingTex;
	
	AOEType(Texture activeTex, Texture placingTex, float duration, float range, float cooldown){
		this.activeTex = activeTex;
		this.placingTex = placingTex;
		this.duration = duration;
		this.range = range;
		this.cooldown = cooldown;
		this.width = activeTex.getImageWidth();
		this.height = activeTex.getImageHeight();
	}
	
	public abstract AOE makeAOE(float x, float y, CopyOnWriteArrayList<Enemy> enemies);
	
	public abstract void PlacementDraw(float x, float y);
	public abstract void resetCooldown();
	public abstract void incrementCooldown(float time);
	public abstract float cooldownPercent();
	public abstract boolean cooldownComplete();

}
