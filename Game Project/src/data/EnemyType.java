package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;
import java.util.Random;

public enum EnemyType {
	
	EnemyUFO(new Texture[] 
			{QuickLoad("UFO1"),
			QuickLoad("UFO2"),
			QuickLoad("UFO3"),
			QuickLoad("UFO4"),
			QuickLoad("UFO5")}, 
			100, 60, TILE_SIZE, TILE_SIZE, false){
		@Override
		public EnemyUFO makeEnemy(int level){
			return new EnemyUFO(level);
		}
	},
	
	EnemySpacePlane(new Texture[] {QuickLoad("starfighter64")}, 20, 100, TILE_SIZE, TILE_SIZE, true){
		@Override
		public EnemySpacePlane makeEnemy(int level){
			return new EnemySpacePlane(level);
		}
	},
	
	EnemyNuke(new Texture[] {QuickLoad("nuke")}, 500, 10, TILE_SIZE, TILE_SIZE, false){
		@Override
		public EnemyNuke makeEnemy(int level){
			return new EnemyNuke(level);
		}
	};
	
	Texture[] textures;
	int startHealth, speed, width, height;
	boolean canFly;
	private static final int TOTAL_ENEMIES = values().length;
	private static Random Random = new Random();
	
	EnemyType(Texture[] textures, int startHealth, int speed, int width, int height, boolean canFly){
		this.textures = textures;
		this.startHealth = startHealth;
		this.speed = speed;
		this.width = width;
		this.height = height;
		this.canFly = canFly;
	}

	public abstract Enemy makeEnemy(int level);
	
	public static EnemyType GetRandomEnemy(){
		EnemyType type = null;
		type = values()[Random.nextInt(TOTAL_ENEMIES)];
		return type;
	}
}
