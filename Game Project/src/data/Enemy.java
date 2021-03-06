package data;

import static helpers.Artist.*;
import static helpers.Clock.Delta;

import org.newdawn.slick.opengl.Texture;

public abstract class Enemy implements Entity {

	private int width, height, level;
	private float x, y, speed, health, startHealth;
	protected Texture texture, healthBackground, healthForeground, healthBorder;
	private Tile startTile;
	private boolean first, canFly;
	protected boolean alive;
	private Pathfinder pathfinder;
	private boolean isSlowed;
	private float slowDuration, slowTime;
	private EnemyType type;

	// Used in a constructor to automatically scale difficulty within a single
	// enemy type
	private static final float[] LevelMultipliers = { 1f, 1.05f, 1.1f, 1.2f, 1.5f, 2f };

	private int[] directions;

	public Enemy(EnemyType type, int level) {
		this.type = type;
		this.setLevel(level);
		this.canFly = type.canFly;
		this.pathfinder = new Pathfinder(canFly);
		if (level >= type.textures.length){
			this.texture = type.textures[type.textures.length - 1];
		} else {
			this.texture = type.textures[level - 1];
		}
		this.healthBackground = QuickLoad("healthbackground");
		this.healthForeground = QuickLoad("healthforeground");
		this.healthBorder = QuickLoad("healthborder");
		this.startTile = TileGrid.GetStartTile();
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = type.width;
		this.height = type.height;
		this.startHealth = type.startHealth * LevelMultipliers[level - 1];
		this.health = type.startHealth * LevelMultipliers[level - 1];
		this.speed = type.speed * LevelMultipliers[level - 1];
		this.directions = new int[2];
		this.directions[0] = 0; // X direction
		this.directions[1] = 0; // Y direction
		this.first = true;
		this.alive = true;
	}

	public void update() {
		if (isSlowed && (slowTime >= slowDuration)){
			isSlowed = false;
			speed = type.speed * LevelMultipliers[level - 1];
		}
		else{
			slowTime += Delta();
		}
		this.navigate();		
	}
	
	private void navigate(){
		pathfinder.checkCP(x, y);
		directions = pathfinder.determineDirection();
		if (directions[0] >= -2) {	//ensures end hasn't been reached
			x += Delta() * directions[0] * speed;
			y += Delta() * directions[1] * speed;
		} else {
			endReached();
		}
	}

	public void decreaseHealth(float f) {
		this.health -= f;
		// Sees if health is below 0, and ensures an enemy can only be killed once
		if (this.health <= 0 && this.isAlive()) {
			this.die();
		}
	}

	// Different enemies give different bonuses
	protected abstract void die();
	
	// Alternate die method for when an enemy reaches the end of the maze
	private void endReached() {
		alive = false;
		Player.ModifyLives(-1);
	}

	public void draw() {
		DrawQuadTex(texture, x, y, width, height);
		DrawQuadTex(healthBackground, x + 5, y - 16, width - 10, 8);
		DrawQuadTex(healthForeground, x + 5, y - 16, (width - 10) * (health / startHealth), 8);
		DrawQuadTex(healthBorder, x + 5, y - 16, width - 10, 8);
	}

	public boolean isSlowed(){
		return isSlowed;
	}
	
	public void slow(float mult){
		if (!isSlowed){
			speed = speed * mult;
			isSlowed = true;
		}
		slowTime = 0f;
	}
	
	public void setSlowDuration(float duration){
		slowDuration = duration;
	}
	
	public float getStartHealth() {
		return startHealth;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}
	
	public float getCenterX(){
		return x + (texture.getImageWidth() / 2);
	}
	
	public float getCenterY(){
		return y + (texture.getImageHeight() / 2);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public Tile getStartTile() {
		return startTile;
	}

	public void setStartTile(Tile startTile) {
		this.startTile = startTile;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public boolean isAlive() {
		return alive;
	}

	public int[] getDirections() {
		return directions;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
