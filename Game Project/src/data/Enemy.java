package data;

import static helpers.Artist.*;
import static helpers.Clock.Delta;
import static data.TileGrid.*;

import org.newdawn.slick.opengl.Texture;

public abstract class Enemy implements Entity {

	private int width, height;
	private float x, y, speed, health, startHealth;
	private Texture texture, healthBackground, healthForeground, healthBorder;
	private Tile startTile;
	private boolean first;
	protected boolean alive;
	
	//Used in a constructor to automatically scale difficulty within a single enemy type
	private static final float[] LevelMultipliers = {1f, 1.05f, 1.1f, 1.2f, 1.5f, 2f};
	
	private int[] directions;
	private Tile nextCP;

	public Enemy(Texture texture, TileGrid grid, int width, int height, float health, float speed) {
		this.texture = texture;
		this.healthBackground = QuickLoad("healthbackground");
		this.healthForeground = QuickLoad("healthforeground");
		this.healthBorder = QuickLoad("healthborder");
		this.startTile = TileGrid.GetStartTile();
		this.x = startTile.getX();
		this.y = startTile.getY();
		this.width = width;
		this.height = height;
		this.health = health;
		this.startHealth = health;
		this.speed = speed;
		this.directions = new int[2];
		this.directions[0] = 0; // X direction
		this.directions[1] = 0; // Y direction
		findNextD(startTile);	//Sets directions based on start tile
		nextCP = findNextCP(startTile, directions);
		this.first = true;
		this.alive = true;
	}
	
	public Enemy(EnemyType type, int level){
		this.texture = type.texture;
		this.healthBackground = QuickLoad("healthBackground");
		this.healthForeground = QuickLoad("healthForeground");
		this.healthBorder = QuickLoad("healthBorder");
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
		findNextD(startTile);	//Sets directions based on start tile
		nextCP = findNextCP(startTile, directions);
		this.first = true;
		this.alive = true;
	}
	
	public void update() {
		//Checks if this has reached its current CP
		if (checkpointReached()) {
			//Finds the direction of the next CP. Returns true if a new direction exists.
			//Returns false if end of maze is reached (no new direction found)
			if (findNextD(nextCP)){
				nextCP = findNextCP(nextCP, directions);
			}
			else{
				endReached();
				return;
			}
		}
		x += Delta() * directions[0] * speed;
		y += Delta() * directions[1] * speed;
	}

	//Check if Enemy is at (very close to) next checkpoint
	private boolean checkpointReached() {
		boolean reached = false;
		Tile t = nextCP;
		if (x > t.getX() - 3 && x < t.getX() + 3 && y > t.getY() - 3 && y < t.getY() + 3) {

			reached = true;
			x = t.getX();
			y = t.getY();

		}

		return reached;
	}

	// Find the next corner and the new direction that needs to be taken there
	//Whole system should be redone with Pathfinder class
	private Tile findNextCP(Tile s, int[] dir) {
		Tile next = null;
		boolean found = false;
		int counter = 1;	//Keeps track of how many tiles away we're looking
		while (!found) {
			if (s.getXPlace() + dir[0] * counter == GetTilesWide()
					|| s.getYPlace() + dir[1] * counter == GetTilesHigh() || s.getType() != GetTile(s.getXPlace() + dir[0] * counter, s.getYPlace() + dir[1] * counter).getType()) {

				found = true;
				counter -= 1;
				next = GetTile(s.getXPlace() + dir[0] * counter, s.getYPlace() + dir[1] * counter);
			}

			counter++;
		}
		return next;
	}

	// Given a tile, which direction should an enemy travel. Returns false if no direction is found
	private boolean findNextD(Tile s) {
		boolean foundNextD = true;
		Tile u = GetTile(s.getXPlace(), s.getYPlace() - 1);
		Tile r = GetTile(s.getXPlace() + 1, s.getYPlace());
		Tile d = GetTile(s.getXPlace(), s.getYPlace() + 1);
		Tile l = GetTile(s.getXPlace() - 1, s.getYPlace());
		
		//Check if square in a given direction matches type, and ensures it's not the square we just came from
		if (s.getType() == u.getType() && !(directions[0] == 0 && directions[1] == 1)) {
			this.directions[0] = 0;
			this.directions[1] = -1;
		} else if (s.getType() == r.getType() && !(directions[0] == -1 && directions[1] == 0)) {
			this.directions[0] = 1;
			this.directions[1] = 0;
		} else if (s.getType() == d.getType() && !(directions[0] == 0 && directions[1] == -1)) {
			this.directions[0] = 0;
			this.directions[1] = 1;
		} else if (s.getType() == l.getType() && !(directions[0] == 1 && directions[1] == 0)) {
			this.directions[0] = -1;
			this.directions[1] = 0;
		} else {
			foundNextD = false;
		}
		return foundNextD;
	}

	public void decreaseHealth(int damage) {
		this.health -= damage;
		//Sees if health is below 0, and ensures an enemy can only be killed once
		if (this.health <= 0 && this.isAlive()) {
			this.die();
		}
	}
	
	//Different enemies give different bonuses
	protected abstract void die(); 
	
	//Alternate die method for when an enemy reaches the end of the maze
	private void endReached(){
		alive = false;
		Player.modifyLives(-1);
	}

	public void draw() {
		DrawQuadTex(texture, x, y, width, height);
		DrawQuadTex(healthBackground, x + 5, y - 16, width - 10, 8);
		DrawQuadTex(healthForeground, x + 5, y - 16, (width - 10) * (health / startHealth), 8);
		DrawQuadTex(healthBorder, x + 5, y - 16, width - 10, 8);
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

}
