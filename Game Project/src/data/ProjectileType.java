package data;

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;

public enum ProjectileType {
	
	ProjectileCannon(QuickLoad("bullet20"), 10, 400, 20),
	ProjectileIceBullet(QuickLoad("icebullet20"), 2, 250, 20),
	ProjectileRedIceBullet(QuickLoad("redicebullet20"), 1, 100, 20),
	ProjectileRocket(QuickLoad("projectilerocket"), 12, 500, 64);
	
	Texture texture;
	int damage, speed, size;
	
	ProjectileType(Texture texture, int damage, int speed, int size){
		this.texture = texture;
		this.damage = damage;
		this.speed = speed;
		this.size = size;
	}

}
