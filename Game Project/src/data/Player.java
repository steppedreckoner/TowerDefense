package data;

import static data.TileGrid.GetTile;
import static helpers.Artist.HEIGHT;
import static helpers.Artist.TILE_SIZE;
import static helpers.Artist.WIDTH;

import java.awt.Font;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.TrueTypeFont;

import UI.UI;
import helpers.Clock;
import helpers.StateManager;

public class Player {

	private WaveManager waveManager;
	private ArrayList<Tower> towerList;
	private boolean showPauseMenu, showTowerMenu, placingTower, mouseButton0, mouseButton1, mouseWait;
	private UI towerUI;
	
	private static TrueTypeFont GameFont;
	
	public static int Cash, Lives;
	private static TowerType CurrentTowerType;
	public static final int STARTING_CASH = 200, STARTING_LIVES = 5;

	public Player(WaveManager waveManager) {
		this.waveManager = waveManager;
		this.towerList = new ArrayList<Tower>();
		this.showPauseMenu = false;
		this.showTowerMenu = false;
		this.placingTower = false;
		this.mouseButton0 = false;
		this.mouseButton1 = false;
		//Keeps track of if there was an interactive click this update cycle. If so
		//other mouse actions are blocked until both mouse buttons are not clicked
		this.mouseWait = true;
		
		GameFont = new TrueTypeFont( new Font("Tahoma", Font.BOLD, 24), false);
		
		//Setup towerUI
		this.towerUI = new UI();
		towerUI.addButton("Towermenutitle", "towerselectbutton", (int) (WIDTH * .175f - 256), (int) (HEIGHT * .8f));	//Title bar, no functionality
		towerUI.addButton("Towercannonblue", "cannonbaseblue", (int) (WIDTH * .075f - 32), (int) (HEIGHT * .9f));
		towerUI.addButton("Towercannonred", "cannonbase", (int) (WIDTH * .175f - 32), (int) (HEIGHT * .9f));
		towerUI.addButton("Towerice", "icetowerbase2", (int) (WIDTH * .275f - 32), (int) (HEIGHT * .9f));
		
		CurrentTowerType = TowerType.CannonBlue;
		Cash = 0; // These are modified when setup() is called
		Lives = 0;
	}

	public void setup() {
		Cash = STARTING_CASH;
		Lives = STARTING_LIVES;
	}

	public static boolean ModifyCash(int amount) {
		if (Cash + amount >= 0) {
			Cash += amount;
			System.out.println(Cash);
			return true;
		}
		System.out.println("Insufficient Funds!");
		return false;
	}

	// Might want to add a life every x waves
	public static void ModifyLives(int amount) {
		Lives += amount;
		if (amount == -1) {
			System.out.println("Ouch! Lost 1 Life! " + Lives + " remaining!");
		}
		if (Lives <= 0) {
			StateManager.setState(StateManager.GameState.GAMEOVER);
		}
	}
	
	public static void DrawCash(){
		GameFont.drawString(WIDTH * .9f, HEIGHT * .9f, "Lives: " + Integer.toString(Lives));
	}
	
	public static void DrawLives(){
		GameFont.drawString(WIDTH * .9f, HEIGHT * .95f, "Cash: " + Integer.toString(Cash));
	}
	
	private void placeTower(Tower tower, Tile tile){
		if (!tile.hasTower()){
			towerList.add(tower);
			tile.setHasTower(true);
		}
	}
	
	public boolean showPauseMenu(){
		return showPauseMenu;
	}
	
	private void UpdateButtons(){
		if (Mouse.isButtonDown(0) && !mouseButton0){
			if (towerUI.isButtonClicked("Towercannonblue")){
				CurrentTowerType = TowerType.CannonBlue;
				placingTower = true;
				mouseWait = true;
			}
			if (towerUI.isButtonClicked("Towercannonred")){
				CurrentTowerType = TowerType.CannonRed;
				placingTower = true;
				mouseWait = true;
			}
			if (towerUI.isButtonClicked("Towerice")){
				CurrentTowerType = TowerType.IceTower;
				placingTower = true;
				mouseWait = true;
			}
		}
	}
	
	//Updates towers. If paused, it only draws the towers (prevents towers from disappearing on pause).
	public void updateTowers(boolean isPaused){
		if (isPaused){
			for (Tower t : towerList){
				t.pauseUpdate();
			}
		}
		else{
			for (Tower t : towerList) {
				t.RefreshEnemies(waveManager.getCurrentWave().getEnemyList());
				t.update();
			}
		}
	}
	
	public void update() {
		if (showTowerMenu){
			towerUI.draw();
			UpdateButtons();
		}
		updateTowers(showPauseMenu);
		DrawCash();
		DrawLives();
		if (placingTower){
			Tower.PlacementDraw(CurrentTowerType, Mouse.getX() - 32, (int) Math.floor(HEIGHT - Mouse.getY() - 1) - 32);
		}

		// Handle mouse input
		
		if (Mouse.isButtonDown(0) && placingTower && !mouseButton0 && !mouseWait){
			Tile tile = GetTile((int) Math.floor(Mouse.getX() / TILE_SIZE), (int) Math.floor((HEIGHT - Mouse.getY() - 1) / TILE_SIZE));
			if (tile.canBuild()) {
				if (ModifyCash(CurrentTowerType.getCost())) {
					placeTower(CurrentTowerType.makeTower(tile, waveManager.getCurrentWave().getEnemyList()), tile);
				}
				placingTower = false;	//If player doesn't have enough cash, cancel tower placement
			}
		}
		//Cancel tower placement by pressing right mouse
		if (Mouse.isButtonDown(1) && placingTower && !mouseButton1 && !mouseWait){
			placingTower = false;
		}
		mouseButton0 = Mouse.isButtonDown(0);
		mouseButton1 = Mouse.isButtonDown(1);
		
		// Handle keyboard input
		while (Keyboard.next()) {
			//Pause and show pause menu
			if (Keyboard.getEventKey() == Keyboard.KEY_P && Keyboard.getEventKeyState()) {
				showPauseMenu = !showPauseMenu;
			}
			//Speed up and slow down time with arrow keys
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
				Clock.ChangeMultiplier(0.2f);
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()) {
				Clock.ChangeMultiplier(-0.2f);
			}
			//Select and place towers
			//Open tower menu
			if (Keyboard.getEventKey() == Keyboard.KEY_T && Keyboard.getEventKeyState()) {
				showTowerMenu = !showTowerMenu;
			}
			//Hotkeys for tower selection
			if (Keyboard.getEventKey() == Keyboard.KEY_1 && Keyboard.getEventKeyState()) {
				CurrentTowerType = TowerType.CannonBlue;
				placingTower = true;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_2 && Keyboard.getEventKeyState()) {
				CurrentTowerType = TowerType.CannonRed;
				placingTower = true;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_3 && Keyboard.getEventKeyState()) {
				CurrentTowerType = TowerType.IceTower;
				placingTower = true;
			}
			//Place towers with mouse click, but only when tower is selected
			
			if (Keyboard.getEventKey() == Keyboard.KEY_A && Keyboard.getEventKeyState() && placingTower) {
				// Get tile at mouse coordinates. Will try to place tower there.
				Tile tile = GetTile((int) Math.floor(Mouse.getX() / TILE_SIZE), (int) Math.floor((HEIGHT - Mouse.getY() - 1) / TILE_SIZE));
				if (tile.canBuild()) {
					if (ModifyCash(CurrentTowerType.getCost())) {
						placeTower(CurrentTowerType.makeTower(tile, waveManager.getCurrentWave().getEnemyList()), tile);
					}
					placingTower = false;
				}
			}
			// Tower deletion
			if (Keyboard.getEventKey() == Keyboard.KEY_D && Keyboard.getEventKeyState()) {
				Tile tile = GetTile((int) Math.floor(Mouse.getX() / TILE_SIZE),
						(int) Math.floor((HEIGHT - Mouse.getY() - 1) / TILE_SIZE));
				Tower tower = null;
				for (Tower t : towerList) {
					if (tile == t.getStartTile()) {
						tower = t;
						break;
					}
				}
				if (tower != null) {
					ModifyCash((int) (-tower.getCost() * .8f));
					towerList.remove(tower);
					tower.getStartTile().setHasTower(false);	//There might be a way to abuse this, but it should work for now.
				}
			}
		}
		//Keep waiting until both buttons are not down. May be changed
		//to have one for each button.
		mouseWait = (Mouse.isButtonDown(0) || Mouse.isButtonDown(1));	
	}

}
