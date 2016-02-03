package data;

import static helpers.Artist.DrawQuadTexRotate;
import static helpers.Artist.TILE_SIZE;
import static helpers.Clock.Delta;

import java.util.concurrent.CopyOnWriteArrayList;

public class ProjectileRocketNuke extends Projectile{
	
	private float currentFlightTime, angle;
	private float x0, y0, xF, yF, yBar, yMax;
	private CopyOnWriteArrayList<Enemy> enemies;
	
	private static final float TOTAL_FLIGHT_TIME = 2.5f, TOTAL_EFFECT_TIME = 3f;
	private static final float MAX_TILE_HEIGHT = -2.5f;	//Negative because lower y implies higher position

	public ProjectileRocketNuke(Enemy target, float x, float y, CopyOnWriteArrayList<Enemy> enemies) {
		super(ProjectileType.ProjectileRocketNuke, target, x, y);
		this.enemies = enemies;
		this.currentFlightTime = 0;
//		this.currentX = x;
//		this.currentY = y;
		this.x0 = x;
		this.y0 = y;
		this.xF = target.getX();
		this.yF = target.getY();
		this.yBar = (yF - y0) / 2;
		this.yMax = yBar + TILE_SIZE * MAX_TILE_HEIGHT;
	}
	
	@Override
	public void doDamage() {
		for (Enemy e : enemies) {
			e.decreaseHealth(e.getStartHealth() / 2f);
		}
		currentFlightTime = 0;
		this.setAlive(false);
	}
	
	private void calculateAngle() {
		if (xF <= x0) {
			if (currentFlightTime >= TOTAL_FLIGHT_TIME * .5f) {
				angle = 90;
			} else {
				angle = 180 - 90 * (2 * currentFlightTime / TOTAL_FLIGHT_TIME);
			}
		} else {
			if (currentFlightTime >= TOTAL_FLIGHT_TIME * .5f) {
				angle = 270;
			} else {
				angle = 180 + 90 * (2 * currentFlightTime / TOTAL_FLIGHT_TIME);
			}
		}
	}
	
	@Override
	public void draw() {
//		float scaleFactor = 1 + (float) Math.sin((currentFlightTime * Math.PI) / TOTAL_FLIGHT_TIME) * 2f;
		float yBase = ((TOTAL_FLIGHT_TIME - currentFlightTime) / TOTAL_FLIGHT_TIME) * y0 
				+ (currentFlightTime / TOTAL_FLIGHT_TIME) * yF;
		float scaleFactor = .5f + 10f * (y - yBase) / yMax;
		DrawQuadTexRotate(texture, x - (int) (texture.getImageWidth() * scaleFactor * .5), y - (int) (texture.getImageHeight() * scaleFactor * .5), (float) Math.floor(texture.getImageWidth() * scaleFactor), (float) Math.floor(texture.getImageHeight() * scaleFactor), angle);
	}
	
	@Override
	public void update() {
		currentFlightTime += Delta();
		if (currentFlightTime >= TOTAL_FLIGHT_TIME) {
			doDamage();
		}
		float preX = x;
		float preY = y;
		x = ((TOTAL_FLIGHT_TIME - currentFlightTime) / TOTAL_FLIGHT_TIME) * x0 
				+ (currentFlightTime / TOTAL_FLIGHT_TIME) * xF;
		y = ((TOTAL_FLIGHT_TIME - currentFlightTime) / TOTAL_FLIGHT_TIME) * y0 
				+ (currentFlightTime / TOTAL_FLIGHT_TIME) * yF
				+ (yMax - yBar) * currentFlightTime * (TOTAL_FLIGHT_TIME - currentFlightTime);
//		float deltaX = x - preX;
//		float deltaY = y - preY;
//		angle = (float) Math.toDegrees(Math.atan2(y - preY, x - preX)) - 90f;
//		if (0 < angle && angle < 90) { 
//			angle = 90;
//		} else if (270 < angle && angle < 360) {
//			angle = 270;
//		}
		calculateAngle();
		draw();
	}

}
