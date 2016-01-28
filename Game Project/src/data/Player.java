package data;

import static data.TileGrid.GetTile;
import static helpers.Artist.HEIGHT;
import static helpers.Artist.TILE_SIZE;
import static helpers.Artist.WIDTH;
import static helpers.Clock.Delta;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

import UI.UI;
import helpers.Clock;
import helpers.StateManager;

public class Player {

	private WaveManager waveManager;
	
	private boolean showPauseMenu, showTowerMenu, mouseButton0, mouseButton1, mouseWait;
	private UI towerUI;
	
	private ArrayList<Tower> towerList;
	private CopyOnWriteArrayList<AOE> AOEList;
	private static TowerType CurrentTowerType;
	private static AOEType CurrentAOEType;
	private boolean placingTower, placingAOE;
	
	public static int Cash, Lives;
	public static final int STARTING_CASH = 200, STARTING_LIVES = 5;

	public Player(WaveManager waveManager) {
		this.waveManager = waveManager;
		this.showPauseMenu = false;
		this.showTowerMenu = false;
		this.placingTower = false;
		this.mouseButton0 = false;
		this.mouseButton1 = false;
		//Keeps track of if there was an interactive click this update cycle. If so
		//other mouse actions are blocked until both mouse buttons are not clicked
		this.mouseWait = true;
		
		//Setup towerUI
		this.towerUI = new UI();
		towerUI.addButton("Towermenutitle", "towerselectbutton", (int) (WIDTH * .175f - 256), (int) (HEIGHT * .8f));	//Title bar, no functionality
		towerUI.addButton("Towercannonblue", "cannonbaseblue", (int) (WIDTH * .075f - 32), (int) (HEIGHT * .9f));
		towerUI.addButton("Towercannonred", "cannonbase", (int) (WIDTH * .175f - 32), (int) (HEIGHT * .9f));
		towerUI.addButton("Towerice", "icetowerbase2", (int) (WIDTH * .275f - 32), (int) (HEIGHT * .9f));
		
		this.towerList = new ArrayList<Tower>();
		this.AOEList = new CopyOnWriteArrayList<AOE>();
		CurrentTowerType = TowerType.CannonBlue;
		CurrentAOEType = AOEType.NULL;
		for (AOEType a : AOEType.values()){
			a.resetCooldown();	//All AOEs are initially available
			a.incrementCooldown(a.cooldown);
		}
		
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
		Game.GameFont.drawString(WIDTH * .9f, HEIGHT * .92f, "Cash: $" + Integer.toString(Cash), Color.green);
	}
	
	public static void DrawLives(){
		Game.GameFont.drawString(WIDTH * .9f, HEIGHT * .95f, "Lives: " + Integer.toString(Lives), Color.red);
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
				placingAOE = false;
				mouseWait = true;
			}
			if (towerUI.isButtonClicked("Towercannonred")){
				CurrentTowerType = TowerType.CannonRed;
				placingTower = true;
				placingAOE = false;
				mouseWait = true;
			}
			if (towerUI.isButtonClicked("Towerice")){
				CurrentTowerType = TowerType.IceTower;
				placingTower = true;
				placingAOE = false;
				mouseWait = true;
			}
		}
	}
	
	public void updateTowers(){
		for (Tower t : towerList) {
			t.refreshEnemies(waveManager.getCurrentWave().getEnemyList());
			t.update();
		}
	}
	
	private void placeAOE(AOE a){
		AOEList.add(a);
	}
	
	public void updateAOE(){
		for (AOE a : AOEList){
			if (a.isAlive()){
				a.refreshEnemies(waveManager.getCurrentWave().getEnemyList());
				a.update();
			}
			else{
				AOEList.remove(a);
			}
		}
	}
	
	public void update() {
		for (AOEType a : AOEType.values()){
			a.incrementCooldown(Delta());
		}
		if (showTowerMenu){
			towerUI.draw();
			UpdateButtons();
		}
		updateTowers();
		updateAOE();
		if (placingTower){
			Tower.PlacementDraw(CurrentTowerType, Mouse.getX() - 32, (int) Math.floor(HEIGHT - Mouse.getY() - 1) - 32);
		}
		if (placingAOE){
			CurrentAOEType.PlacementDraw(Mouse.getX(), (int) Math.floor(HEIGHT - Mouse.getY() - 1));
		}

		// Handle mouse input
		
		if (Mouse.isButtonDown(0) && !mouseButton0 && !mouseWait){
			if (placingTower){
				Tile tile = GetTile((int) Math.floor(Mouse.getX() / TILE_SIZE), (int) Math.floor((HEIGHT - Mouse.getY() - 1) / TILE_SIZE));
				if (tile.canBuild() && !tile.hasTower()) {
					if (ModifyCash(CurrentTowerType.getCost())) {
						placeTower(CurrentTowerType.makeTower(tile, waveManager.getCurrentWave().getEnemyList()), tile);
					}
					placingTower = false;	//If player doesn't have enough cash, cancel tower placement
				}
			}
			if (placingAOE && CurrentAOEType.cooldownComplete()){
				placeAOE(CurrentAOEType.makeAOE(Mouse.getX(), (int) Math.floor(HEIGHT - Mouse.getY() - 1), waveManager.getCurrentWave().getEnemyList()));
				CurrentAOEType.resetCooldown();
				placingAOE = false;
			}
		}
		//Cancel tower or AOE placement by pressing right mouse
		if (Mouse.isButtonDown(1) && (placingTower || placingAOE) && !mouseButton1 && !mouseWait){
			placingTower = false;
			placingAOE = false;
		}
		mouseButton0 = Mouse.isButtonDown(0);
		mouseButton1 = Mouse.isButtonDown(1);
		
		// Handle keyboard input
		
		while (Keyboard.next()) {
			//Toggle Pause and toggle pause menu
			if (Keyboard.getEventKey() == Keyboard.KEY_P && Keyboard.getEventKeyState()) {
				showPauseMenu = !showPauseMenu;
				Clock.Pause();
			}
			//Speed up and slow down time with arrow keys
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
				Clock.ChangeMultiplier(0.2f);
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()) {
				Clock.ChangeMultiplier(-0.2f);
			}
			
			//Select and and begin placing towers
			
			//Open tower menu
			if (Keyboard.getEventKey() == Keyboard.KEY_T && Keyboard.getEventKeyState()) {
				showTowerMenu = !showTowerMenu;
			}
			//Hotkeys for tower selection
			if (Keyboard.getEventKey() == Keyboard.KEY_1 && Keyboard.getEventKeyState()) {
				CurrentTowerType = TowerType.CannonBlue;
				placingTower = true;
				placingAOE = false;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_2 && Keyboard.getEventKeyState()) {
				CurrentTowerType = TowerType.CannonRed;
				placingTower = true;
				placingAOE = false;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_3 && Keyboard.getEventKeyState()) {
				CurrentTowerType = TowerType.IceTower;
				placingTower = true;
				placingAOE = false;
			}
			
			//Select and begin placing AOE
			
			if (Keyboard.getEventKey() == Keyboard.KEY_Q && Keyboard.getEventKeyState()) {
				CurrentAOEType = AOEType.FireStrike;
				placingAOE = true;
				placingTower = false;
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
