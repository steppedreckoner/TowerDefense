package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;

public enum ProjectileType {
	
	ProjectileCannon(QuickLoad("bullet"), 10, 300, 20, 20),
	ProjectileIceBullet(QuickLoad("icebullet"), 2, 250, 20, 20),
	ProjectileRedIceBullet(QuickLoad("redicebullet"), 1, 100, 20, 20),
	ProjectileRocket(QuickLoad("projectilerocket"), 12, 750, 12, 64),
	ProjectileRocketNuke(QuickLoad("projectilerocketnuke"), 0, 0, 12, 64);
	
	Texture texture;
	int damage, speed, width, height;
	
	ProjectileType(Texture texture, int damage, int speed, int width, int height){
		this.texture = texture;
		this.damage = damage;
		this.speed = speed;
		this.width = width;
		this.height = height;
	}

}
