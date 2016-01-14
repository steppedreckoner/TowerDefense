package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Clock.*;
import static helpers.Artist.*;

public abstract class Projectile implements Entity{

	private Texture texture;
	private float x, y, speed, xVelocity, yVelocity;
	private int damage, width, height;
	private Enemy target;
//	private float targetSpeed;
	private boolean alive;

	public Projectile(ProjectileType type, Enemy target, float x, float y) {
		this.texture = type.texture;
		this.x = x + ((TILE_SIZE - type.size) / 2);	//Handle offsets automatically (Offset comes from tracking upper left corner of projectile)
		this.y = y + ((TILE_SIZE - type.size) / 2);
		this.width = type.size;
		this.height = type.size;
		this.speed = type.speed;
		this.damage = type.damage;
		this.target = target;
		this.alive = true;
//		this.targetSpeed = target.getSpeed();
//		int[] directions = target.getDirections();
		this.xVelocity = 0;
		this.yVelocity = 0;
		calculateVelocity();
		// Target Prediction
		// xVelocity += directions[0] * speed;
		// yVelocity += directions[1] * speed;
	}

	private void calculateVelocity() {
		float xDistanceFromTarget = target.getX() - x + TILE_SIZE / 4;
		float yDistanceFromTarget = target.getY() - y + TILE_SIZE / 4;
		float normalizingFactor = (float) Math
				.sqrt(Math.pow(xDistanceFromTarget, 2) + Math.pow(yDistanceFromTarget, 2));
		xVelocity = xDistanceFromTarget / normalizingFactor * speed;
		yVelocity = yDistanceFromTarget / normalizingFactor * speed;
	}
	
	public void doDamage(){
		target.decreaseHealth(damage);
		this.alive = false;
	}

	public void Update() {
		if (alive) {
			calculateVelocity(); // This allows for homing

			x += Delta() * xVelocity; // "Dumb" Shooting
			y += Delta() * yVelocity;

			if (CheckCollision(x, y, width, height, target.getX(), target.getY(), target.getWidth(),
					target.getHeight())) {
				doDamage();
			}
			this.Draw();
		}
	}

	public void Draw() {
		DrawQuadTex(texture, x, y, 32, 32);
	}

	public Enemy getTarget() {
		return target;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setAlive(boolean isAlive){
		alive = isAlive;
	}

	public void update() {
		// TODO Auto-generated method stub
		
	}

	public void draw() {
		// TODO Auto-generated method stub
		
	}
}
