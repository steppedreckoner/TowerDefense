package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;

import java.util.concurrent.CopyOnWriteArrayList;

public enum TowerType {
	
	CannonRed(new Texture[]{QuickLoad("cannonbase"), QuickLoad("cannongun")}, 150, .2f, -50, 31) {
		@Override
		public Tower makeTower(Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
			return new TowerCannonRed(startTile, enemies);
		}
	},
	CannonBlue(new Texture[]{QuickLoad("cannonbaseblue"), QuickLoad("cannongunblue")}, 2000, 2.5f, -40, 31) {
		@Override
		public Tower makeTower(Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
			return new TowerCannonBlue(startTile, enemies);
		}
	},
	IceTower(new Texture[]{QuickLoad("icetowerbase2"), QuickLoad("icetowergun")}, 300, 1.5f, -100, 20) {
		@Override
		public Tower makeTower(Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
			return new TowerIce(startTile, enemies);
		}
	},
	
	RocketTower(new Texture[]{QuickLoad("rockettowerbase"), QuickLoad("rockettowerbarrel")}, 6000, 3f, -1, 28) {
		@Override
		public Tower makeTower(Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
			return new TowerRocketLauncher(startTile, enemies);
		}
	};
	
	Texture[] textures;
	int range, barrelLength;
	float rateOfFire;
	private int cost;
	
	TowerType(Texture[] textures, int range, float rateOfFire, int cost, int barrelLength){
		this.textures = textures;
		this.range = range;
		this.rateOfFire = rateOfFire;
		this.cost = cost;
		this.barrelLength = barrelLength;
	}
	
	public int getCost(){
		return cost;
	}
	
	public abstract Tower makeTower(Tile startTile, CopyOnWriteArrayList<Enemy> enemies);
	
}
