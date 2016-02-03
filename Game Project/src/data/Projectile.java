package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Clock.*;
import static helpers.Artist.*;

public abstract class Projectile implements Entity{

	protected Texture texture;
	protected float x, y, centerX, centerY;
	protected float xVelocity, yVelocity;
	protected float speed;
	private int damage, width, height;
	protected Enemy target;
	private boolean alive;

	public Projectile(ProjectileType type, Enemy target, float x, float y) {
		this.texture = type.texture;
		this.width = type.width;
		this.height = type.height;
		this.speed = type.speed;
		this.damage = type.damage;
		this.x = x;	//Handle spawn offsets automatically (Offset comes from tracking upper left corner of projectile)
		this.y = y;	//Spawn offsets are to be handled in subclasses. This will allow for projectiles to originate from the end of the gun barrel.
		this.centerX = x + (texture.getImageWidth() / 2);
		this.centerY = y + (texture.getImageHeight() / 2);
		this.target = target;
		this.alive = true;
		this.xVelocity = 0;
		this.yVelocity = 0;
		calculateVelocity();
	}
	
	//Default calculate velocity uses centers of target and projectile.
	protected void calculateVelocity() {
		calculateVelocity(centerX, centerY, target.getCenterX(), target.getCenterY());
	}
	
	//Allows for targeting between specific part of projectile and center of enemy.
	protected void calculateVelocity(float projX, float projY) {
		calculateVelocity(projX, projY, target.getCenterX(), target.getCenterY());
	}
	
	//Allows for targeting between specific parts of the projectile and enemy (e.g. rocket warhead).
	protected void calculateVelocity(float projX, float projY, float targetX, float targetY) {
		float xDistanceFromTarget = targetX - projX;
		float yDistanceFromTarget = targetY - projY;
		float magnitude = (float) Math
				.sqrt(Math.pow(xDistanceFromTarget, 2) + Math.pow(yDistanceFromTarget, 2));
		xVelocity = xDistanceFromTarget / magnitude * speed;
		yVelocity = yDistanceFromTarget / magnitude * speed;
	}
	
	protected void updatePosition() {
		x += Delta() * xVelocity;
		y += Delta() * yVelocity;
		centerX = x + (texture.getImageWidth() / 2);
		centerY = y + (texture.getImageHeight() / 2);
	}
	
	protected boolean checkCollision() {
		float deltaX = target.getCenterX() - centerX;
		float deltaY = target.getCenterY() - centerY;
		if (Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)) < Math.min(width, height)) {
			return true;
		}
		return false;
	}
	
	public void doDamage(){
		target.decreaseHealth(damage);
		this.alive = false;
	}
	
	public void draw() {
		DrawQuadTex(texture, x, y, texture.getImageWidth(), texture.getImageHeight());
	}

	//Handle velocity calculation in subclasses
	//(e.g. @Override update() { calculateVelcocity; super.update(); })
	public void update() {
		if (alive) {
			updatePosition();
			if (checkCollision()) {
				doDamage();
			}
			this.draw();
		}
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
	
	public boolean isAlive(){
		return alive;
	}


}
