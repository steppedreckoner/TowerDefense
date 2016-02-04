package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.DrawQuadTexRotate;
import static helpers.Artist.WIDTH;
import static helpers.Artist.HEIGHT;
import static helpers.Artist.TILE_SIZE;
import static helpers.Clock.Delta;

import java.awt.Font;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import helpers.Artist;

public abstract class Tower implements Entity {

	protected float x, y, barrelX, barrelY;
	protected int barrelLength;
	protected float timeSinceLastShot;
	protected float rateOfFire;
	protected int range;
	private float angle;
	private int width, height;
	private int cost;
	protected Enemy target;
	private Texture[] textures;
	private Tile startTile;
	protected CopyOnWriteArrayList<Projectile> projectiles;
	protected boolean hasTarget, isBuffed;
	protected TowerType type;
	protected int level, maxLevel, shotsFired, totalKills;
	protected String towerName;
	
	private UI towerUI;
	private boolean showTowerUI;
	private int menuX, menuY, drawOffset;
	private static TrueTypeFont TowerFont, TowerTitleFont;
	private static boolean MouseButton0, MouseWait;
	private static Tower MenuTower;
	private static final int MENU_WIDTH = 160, MENU_HEIGHT = 192;
	
	private static int TotalTowersPlaced, CurrentTowers;

	public Tower(TowerType type, Tile startTile) {
		this.textures = type.textures;
		this.range = type.getLevelListRange()[1];
		this.rateOfFire = type.getLevelListFireRate()[1];
		this.startTile = startTile;
		this.x = this.startTile.getX();
		this.y = this.startTile.getY();
		this.barrelLength = type.barrelLength;
		this.barrelX = getCenterX();
		this.barrelY = getCenterY() + barrelLength;
		this.width = startTile.getWidth();
		this.height = startTile.getHeight();
		this.hasTarget = false;
		this.isBuffed = false;
		this.timeSinceLastShot = this.rateOfFire;
		this.projectiles = new CopyOnWriteArrayList<Projectile>();
		this.angle = 0f;
		this.cost = type.getLevelListUpgradeCost()[0];
		this.type = type;
		this.level = 1;
		this.maxLevel = type.maxLevel;
		this.shotsFired = 0;
		this.totalKills = 0;
		
		towerUI = new UI();
		setMenuParameters();
		towerUI.addButton("Background", "towermenubackground", menuX, menuY);	//Not a functional button
		towerUI.addButton("Exit", "exitbutton16", menuX + 140, menuY + 3);
		towerUI.addButton("Upgrade", "towermenuupgradebutton", menuX + (MENU_WIDTH / 2) - 64, menuY + 118);
		towerUI.addButton("Delete", "towermenudeletebutton", menuX + (MENU_WIDTH / 2) - 64, menuY + 154);
		this.showTowerUI = false;
		
		
		TotalTowersPlaced++;
		CurrentTowers++;
		towerName = "Tower " + Integer.toString(TotalTowersPlaced);
		drawOffset = 3 * (towerName.length() - 6);	//3 pixels for each digit in towerName
		Player.TowerList.add(this);
	}
	
	public static void Setup() {
		TowerTitleFont = new TrueTypeFont( new Font("Tahoma", Font.BOLD, 14), false);
		TowerFont = new TrueTypeFont( new Font("Tahoma", Font.BOLD, 12), false);
		MouseButton0 = true;
		MouseWait = true;
		TotalTowersPlaced = 0;
		CurrentTowers = 0;
	}
	
	public void delete() {
		Player.ModifyCash((int) (-cost * .8f));	//negative cost to add money
		Player.TowerList.remove(this);
		startTile.setHasTower(false);	//There might be a way to abuse this, but it should work for now.
		CurrentTowers--;	
	}
	
	public void levelUp() {
		if (level < maxLevel){
			if (Player.ModifyCash(type.getLevelListUpgradeCost()[level])) {
				level++;
				rateOfFire = type.getLevelListFireRate()[level];
				range = type.getLevelListRange()[level];
			}	
		}
	}
	
	private void setMenuParameters() {
		menuX = (int) (x - (MENU_WIDTH / 2) + TILE_SIZE / 2);
		menuY = (int) (y - MENU_HEIGHT - 8);
		if (menuX < 0) {
			menuX = 0;
		}
		if (menuX > WIDTH - MENU_WIDTH) {
			menuX = WIDTH - MENU_WIDTH;
		}
		if (menuY < 0) {
			menuY = 0;
		}
		if (menuY > HEIGHT - MENU_HEIGHT) {
			menuY = HEIGHT - MENU_HEIGHT;
		}
	}
	
	public void showMenu() {
		if (!MouseWait) {
			MenuTower = this;
			showTowerUI = true;
		}
	}
	
	public void closeMenu() {
		MenuTower = null;
		showTowerUI = false;
		MouseWait = true;
	}
	
	public boolean isShowMenu() {
		return showTowerUI;
	}
	
	private void drawUI() {
		towerUI.draw();
	}
	
	private void updateButtons() {
		if (Mouse.isButtonDown(0) && !MouseButton0){
			if (towerUI.isButtonClicked("Exit")){
				this.closeMenu();
				MouseWait = true;
			}
			if (towerUI.isButtonClicked("Upgrade")){
				this.levelUp();
				MouseWait = true;
			}
			if (towerUI.isButtonClicked("Delete")){
				this.closeMenu();
				this.delete();
				MouseWait = true;
			}
		}
		if (towerUI.isButtonClicked("Upgrade")) {
			System.out.println("Print upgrade features");
		}
	}
	
	public void updateUI() {
		drawUI();
		TowerTitleFont.drawString(menuX + (MENU_WIDTH / 2) - 28 - drawOffset, menuY + 2, towerName);
		TowerFont.drawString(menuX + 6, menuY + 22, "Level: " + level);
		TowerFont.drawString(menuX + 6, menuY + 42, "Range: " + range);
		TowerFont.drawString(menuX + 6, menuY + 58, "RoF: " + rateOfFire + " sec/shot");
		TowerFont.drawString(menuX + 6, menuY + 78, "Shots: " + shotsFired);
		TowerFont.drawString(menuX + 6, menuY + 94, "Kills: " + totalKills);
		
		updateButtons();
	}

	public void update() {
		target = acquireTarget();
		timeSinceLastShot += Delta();
		if (this.hasTarget) {
			angle = calculateAngle();
			if (timeSinceLastShot > rateOfFire && this.isInRange(target)) {
				shoot(target);
				timeSinceLastShot = 0;
				shotsFired++;
			}
		}
		for (Projectile p : projectiles) {
			if (p.isAlive()){
				p.update();
			}
			else{
				if (p.killedEnemy()) {
					totalKills++;
				}
				projectiles.remove(p);
			}
		}
		this.draw();
	}
	
	//Used to illustrate towers that are about to be placed
	public static void PlacementDraw(TowerType type, int x, int y){
		for (int i = 0; i < type.textures.length; i++){
			DrawQuadTex(type.textures[i], x - (type.width / 2), y - (type.height / 2), type.width, type.height);
		}
	}

	public void draw() {
		for (int i = 0; i < textures.length; i++) {
			if (i == 0) {
				DrawQuadTexRotate(textures[i], x, y, width, height, 0);
			} 
			else {
				DrawQuadTexRotate(textures[i], x, y, width, height, angle);
			}
		}
	}

	protected abstract Enemy acquireTarget();
	
	public abstract void shoot(Enemy target);

	protected boolean isInRange(Enemy e) {
		float totalDist = findDistance(e);
		if (totalDist < this.range) {
			return true;
		}
		return false;
	}

	protected float findDistance(Enemy e) {
		float xDist = e.getCenterX() - x;
		float yDist = e.getCenterY() - y;
		return (float) Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
	}

	protected float calculateAngle() {
		double angleTemp = Math.atan2(target.getY() - y, target.getX() - x);
		barrelX = (float) (getCenterX() + barrelLength * Math.cos(angleTemp));	//angleTemp still in radians
		barrelY = (float) (getCenterY() + barrelLength * Math.sin(angleTemp));	//Do this before rotating texture to fit with coordinates
		return (float) Math.toDegrees(angleTemp) - 90;
	}
	
	public void rateOfFireMultiplier(float mult){
		rateOfFire = rateOfFire / mult;
		buff();
	}
	
	public void resetRateOfFire(){
		rateOfFire = type.getLevelListFireRate()[level];
	}
	
	public boolean isBuffed(){
		return isBuffed;
	}
	
	public void buff(){
		isBuffed = true;
	}
	
	public void debuff(){
		resetRateOfFire();
		isBuffed = false;
	}
	
	public static void SetMouseButton0() {
		MouseButton0 = Mouse.isButtonDown(0);
	}
	
	public static void UnlockMouse() {
		MouseWait = false;
	}
	
	public static Tower GetMenuTower() {
		return MenuTower;
	}

	public int getCost(){
		return cost;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public float getCenterX(){
		return x + Artist.TILE_SIZE / 2;
	}
	
	public float getCenterY(){
		return y + Artist.TILE_SIZE / 2;
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
	
	public Enemy getTarget(){
		return target;
	}
	
	public Tile getStartTile(){
		return startTile;
	}

}
