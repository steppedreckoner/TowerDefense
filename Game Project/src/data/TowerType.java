package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;

import java.util.concurrent.CopyOnWriteArrayList;

public enum TowerType {
	
	CannonRed(new Texture[]{QuickLoad("cannonbase"), QuickLoad("cannongun")}, 150, .2f, -50) {
		@Override
		public Tower makeTower(Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
			return new TowerCannonRed(startTile, enemies);
		}
	},
	CannonBlue(new Texture[]{QuickLoad("cannonbaseblue"), QuickLoad("cannongunblue")}, 200, 3f, -40) {
		@Override
		public Tower makeTower(Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
			return new TowerCannonBlue(startTile, enemies);
		}
	},
	IceTower(new Texture[]{QuickLoad("icetowerbase2"), QuickLoad("icetowergun")}, 300, 1f, -100) {
		@Override
		public Tower makeTower(Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
			return new TowerIce(startTile, enemies);
		}
	};
	
	Texture[] textures;
	int range;
	float rateOfFire;
	private int cost;
	
	TowerType(Texture[] textures, int range, float rateOfFire, int cost){
		this.textures = textures;
		this.range = range;
		this.rateOfFire = rateOfFire;
		this.cost = cost;
	}
	
	public int getCost(){
		return cost;
	}
	
	public abstract Tower makeTower(Tile startTile, CopyOnWriteArrayList<Enemy> enemies);
	
}
