package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;

public enum TowerType {
	
	CannonRed(new Texture[]{QuickLoad("cannonbase"), QuickLoad("cannongun")}, 150, 0f),
	CannonBlue(new Texture[]{QuickLoad("cannonbaseblue"), QuickLoad("cannongunblue")}, 200, 3f),
	IceTower(new Texture[]{QuickLoad("icetowerbase2"), QuickLoad("icetowergun")}, 300, 4);
	
	Texture[] textures;
	int range;
	float rateOfFire;
	
	TowerType(Texture[] textures, int range, float rateOfFire){
		this.textures = textures;
//		this.damage = damage;
		this.range = range;
		this.rateOfFire = rateOfFire;
	}
	
	
}
