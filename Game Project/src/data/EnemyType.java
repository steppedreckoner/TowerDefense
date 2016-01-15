package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;

public enum EnemyType {
	
	EnemyUFO(QuickLoad("UFO"), 100, 60, TILE_SIZE, TILE_SIZE, false){
		@Override
		public EnemyUFO makeEnemy(int level){
			return new EnemyUFO(level);
		}
	},
	
	EnemyUFO2(QuickLoad("UFO"), 130, 75, TILE_SIZE, TILE_SIZE, false){
		@Override
		public EnemyUFO makeEnemy(int level){
			return new EnemyUFO(level);
		}
	},
	
	EnemySpacePlane(QuickLoad("starfighter64"), 20, 100, TILE_SIZE, TILE_SIZE, true){
		@Override
		public EnemySpacePlane makeEnemy(int level){
			return new EnemySpacePlane(level);
		}
	},
	
	EnemyNuke(QuickLoad("nuke"), 500, 10, TILE_SIZE, TILE_SIZE, false){
		@Override
		public EnemyNuke makeEnemy(int level){
			return new EnemyNuke(level);
		}
	};
	
	Texture texture;
	int startHealth, speed, width, height;
	boolean canFly;
	
	EnemyType(Texture texture, int startHealth, int speed, int width, int height, boolean canFly){
		this.texture = texture;
		this.startHealth = startHealth;
		this.speed = speed;
		this.width = width;
		this.height = height;
		this.canFly = canFly;
	}

	public abstract Enemy makeEnemy(int level);
}
