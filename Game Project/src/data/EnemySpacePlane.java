package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.DrawQuadTexRotate;

public class EnemySpacePlane extends Enemy {

	public EnemySpacePlane(int level) {
		super(EnemyType.EnemySpacePlane, level);
	}
	
	@Override
	public void die() {
		alive = false;
		Player.modifyCash(15);
	}

	@Override
	public void draw() {
		DrawQuadTexRotate(texture, this.getX(), this.getY(), this.getWidth(), this.getHeight(), (float) Math.toDegrees(Math.atan2(this.getDirections()[1], this.getDirections()[0])) + 90);
		DrawQuadTex(healthBackground, this.getX() + 5, this.getY() - 16, EnemyType.EnemySpacePlane.width - 10, 8);
		DrawQuadTex(healthForeground, this.getX() + 5, this.getY() - 16, (EnemyType.EnemySpacePlane.width - 10) * (this.getHealth() / EnemyType.EnemySpacePlane.startHealth), 8);
		DrawQuadTex(healthBorder, this.getX() + 5, this.getY() - 16, EnemyType.EnemySpacePlane.width - 10, 8);
	}

}