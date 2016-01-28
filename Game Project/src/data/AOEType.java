package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.DrawQuadTexAlpha;
import static helpers.Artist.QuickLoad;

import org.newdawn.slick.opengl.Texture;

import helpers.Artist;

public enum AOEType {
	
	FireStrike(QuickLoad("aoefirestrike"), QuickLoad("aoefirestrikeoutline"), 4f, 2.5f * Artist.TILE_SIZE, 15f){
		
		private float cooldownTimer; 
		
		@Override
		public AOE makeAOE(float x, float y) {
			return new AOEFireStrike(x, y);
		}

		@Override
		public int getListID(){
			return Player.ENEMY_LIST_ID;
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
			if (cooldownTimer <= 0){
				return 0f;
			}
			return (cooldownTimer / FireStrike.cooldown);
		}
		
		@Override
		public boolean cooldownComplete(){
			return cooldownTimer <= 0;
		}

	},
	
	TowerBuff(QuickLoad("aoetowerbuff"), QuickLoad("aoetowerbuffoutline"), 5f, 3f * Artist.TILE_SIZE, 5f){
		
		private float cooldownTimer;

		@Override
		public AOE makeAOE(float x, float y) {
			return new AOETowerBuff(x, y);
		}

		@Override
		public int getListID(){
			return Player.TOWER_LIST_ID;
		}
		
		@Override
		public void PlacementDraw(float x, float y) {
			DrawQuadTexAlpha(TowerBuff.placingTex, x - (TowerBuff.width / 2), y - (TowerBuff.height / 2), TowerBuff.width, TowerBuff.height, 1 - cooldownPercent());			
			DrawQuadTex(QuickLoad("cooldownbackground"), x - (64 / 2), y + 70, 64, 8);
			DrawQuadTex(QuickLoad("cooldownforeground"), x - (64 / 2), y + 70, 64 * (1 - cooldownPercent()), 8);
			DrawQuadTex(QuickLoad("cooldownborder"), x - (64 / 2), y + 70, 64, 8);
		}
		
		@Override
		public void resetCooldown() {
			cooldownTimer = TowerBuff.cooldown;
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
			if (cooldownTimer <= 0){
				return 0f;
			}
			return (cooldownTimer / TowerBuff.cooldown);
		}
		
		@Override
		public boolean cooldownComplete(){
			return cooldownTimer <= 0;
		}
		
	},
	
	Slow(QuickLoad("aoeslow"), QuickLoad("aoeslowoutline"), 10f, 2f * Artist.TILE_SIZE, 10f){
		
		private float cooldownTimer;

		@Override
		public AOE makeAOE(float x, float y) {
			return new AOESlow(x, y);
		}

		@Override
		public int getListID() {
			return Player.ENEMY_LIST_ID;
		}

		@Override
		public void PlacementDraw(float x, float y) {
			DrawQuadTexAlpha(Slow.placingTex, x - (Slow.width / 2), y - (Slow.height / 2), Slow.width, Slow.height, 1 - cooldownPercent());			
			DrawQuadTex(QuickLoad("cooldownbackground"), x - (64 / 2), y + 70, 64, 8);
			DrawQuadTex(QuickLoad("cooldownforeground"), x - (64 / 2), y + 70, 64 * (1 - cooldownPercent()), 8);
			DrawQuadTex(QuickLoad("cooldownborder"), x - (64 / 2), y + 70, 64, 8);
		}
		
		@Override
		public void resetCooldown() {
			cooldownTimer = Slow.cooldown;
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
			if (cooldownTimer <= 0){
				return 0f;
			}
			return (cooldownTimer / Slow.cooldown);
		}
		
		@Override
		public boolean cooldownComplete(){
			return cooldownTimer <= 0;
		}
		
	},
	
	NULL(QuickLoad("aoenull"), QuickLoad("aoenull"), 99999f, 0f, 0f){
		
		@Override
		public AOE makeAOE(float x, float y){
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

		@Override
		public int getListID() {
			return 0;
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
	
	public abstract AOE makeAOE(float x, float y);
	
	public abstract int getListID();
	public abstract void PlacementDraw(float x, float y);
	public abstract void resetCooldown();
	public abstract void incrementCooldown(float time);
	public abstract float cooldownPercent();
	public abstract boolean cooldownComplete();

}
