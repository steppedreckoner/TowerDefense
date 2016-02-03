package data;

import static data.TileGrid.GetTile;
import static helpers.Artist.HEIGHT;
import static helpers.Artist.TILE_SIZE;
import static helpers.Artist.WIDTH;
import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.DrawQuadTexAlpha;
import static helpers.Artist.QuickLoad;
import static helpers.Clock.Delta;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import helpers.Clock;
import helpers.StateManager;

public class Player {

	private WaveManager waveManager;
	
	private boolean showPauseMenu, showTowerMenu, mouseButton0, mouseButton1, mouseWait;
	
	private ArrayList<Tower> towerList;
	private CopyOnWriteArrayList<AOE> AOEList;
	private static TowerType CurrentTowerType;
	private static AOEType CurrentAOEType;
	private boolean placingTower, placingAOE;
	
	private static UI TowerUI;
	
	private static Texture ExpBarBackground = QuickLoad("playerexpbackground"),
			ExpBarForeground = QuickLoad("playerexpforeground"),
			ExpBarBorder = QuickLoad("playerexpborder");
	
	public static int Cash, Lives;
	private static int PlayerLevel, CurrentExp;
	
	public static final int STARTING_CASH = 200, STARTING_LIVES = 5;
	public static final int TOWER_LIST_ID = 703, ENEMY_LIST_ID = 333;
	
	private static final int TOWER_CANNONRED_UNLOCK = 2,
			TOWER_ICE_UNLOCK = 3,
			TOWER_ROCKET_UNLOCK = 4,
			AOE_FIRESTRIKE_UNLOCK = 3,
			AOE_TOWERBUFF_UNLOCK = 4,
			AOE_SLOW_UNLOCK = 5;
	private static final int[] EXP_LIST = {0, 100, 220, 370, 600, 920, 1430};
	public static final int MAX_LEVEL = EXP_LIST.length - 1;

	public Player(WaveManager waveManager) {
		PlayerLevel = 1;
		CurrentExp = 0;
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
		TowerUI = new UI();
		TowerUI.addButton("Towermenutitle", "towerselectbutton", TILE_SIZE, TILE_SIZE * 12);	//Title bar, no functionality
		TowerUI.addButton("Towercannonblue", "cannonbaseblue", TILE_SIZE * 1, TILE_SIZE * 13);
		
		
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

	public static void Setup() {
		Cash = STARTING_CASH;
		Lives = STARTING_LIVES;
		PlayerLevel = 1;
		CurrentExp = 0;
		UnlockTowers();
	}
	
	//To be used with persistent player profiles. Parameters should be read in from a file.
	//May be modified to take a file as input
	public static void Setup(int newCash, int newLives, int newPlayerLevel, int newCurrentExp){
		Cash = newCash;
		Lives = newLives;
		PlayerLevel = newPlayerLevel;
		CurrentExp = newCurrentExp;
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
	
	public static void ModifyExp(int amount){
		CurrentExp += amount;
		System.out.println("Gained " + amount + " experience!");
		if (PlayerLevel < MAX_LEVEL){
			if (CurrentExp >= EXP_LIST[PlayerLevel]){
				PlayerLevel++;
				Lives ++;
				UnlockTowers();
				System.out.println("Advanced Player to Level " + PlayerLevel + "!");
			}
		}
	}
	
	private static void UnlockTowers(){
		if (PlayerLevel >=  TOWER_CANNONRED_UNLOCK){
			TowerUI.addButton("Towercannonred", "cannonbase", TILE_SIZE * 2, TILE_SIZE * 13);
		}
		if (PlayerLevel >=  TOWER_ICE_UNLOCK){
			TowerUI.addButton("Towerice", "icetowerbase2", TILE_SIZE * 3, TILE_SIZE * 13);
		}
		if (PlayerLevel >=  TOWER_ROCKET_UNLOCK){
			TowerUI.addButton("Towerrocket", "rockettowerbase", TILE_SIZE * 4, TILE_SIZE * 13);
		}
	}
	
	public static void DrawCash(){
		Game.GameFont.drawString(WIDTH * .88f - 4, HEIGHT * .93f, "Cash: $" + Integer.toString(Cash), Color.green);
	}
	
	public static void DrawLives(){
		Game.GameFont.drawString(WIDTH * .88f - 4, HEIGHT * .9f, "Lives: " + Integer.toString(Lives), Color.red);
	}
	
	public static void DrawLevel() {
		Game.GameFont.drawString(WIDTH * .88f - 4, HEIGHT - 31, "Player Level: " + PlayerLevel, Color.white);
	}
	
	public static void DrawExp(){
		int denominator;
		int numerator;
		if (PlayerLevel < MAX_LEVEL){
			denominator = EXP_LIST[PlayerLevel] - EXP_LIST[PlayerLevel - 1];
			numerator = CurrentExp - EXP_LIST[PlayerLevel - 1];	//Works when PlayerLeve = 1 because EXP_LIST[0] = 0
		} else {
			denominator = EXP_LIST[MAX_LEVEL];
			numerator = EXP_LIST[MAX_LEVEL];
		}
		DrawQuadTexAlpha(ExpBarBackground, 0, HEIGHT - 32, ExpBarBackground.getImageWidth(), ExpBarBackground.getImageHeight(), .5f);
		DrawQuadTexAlpha(ExpBarForeground, 0, HEIGHT - 32, ExpBarForeground.getImageWidth() * numerator / denominator, ExpBarForeground.getImageHeight(), .5f);
		DrawQuadTex(ExpBarBorder, 0, HEIGHT - 32, ExpBarBorder.getImageWidth(), ExpBarBorder.getImageHeight());
		
		Game.GameFont.drawString(WIDTH / 2, HEIGHT - 31, numerator + "/" + denominator, Color.white);
	}
	
	private void placeTower(Tower tower, Tile tile){
		if (!tile.hasTower()){
			towerList.add(tower);
			tile.setHasTower(true);
		}
	}
	
	public boolean isShowPauseMenu(){
		return showPauseMenu;
	}
	
	public void closePauseMenu(){
		showPauseMenu = false;
	}
	
	private void UpdateButtons(){
		if (Mouse.isButtonDown(0) && !mouseButton0){
			if (TowerUI.isButtonClicked("Towercannonblue")){
				CurrentTowerType = TowerType.CannonBlue;
				placingTower = true;
				placingAOE = false;
				mouseWait = true;
			}
			if (TowerUI.isButtonClicked("Towercannonred") && PlayerLevel >= TOWER_CANNONRED_UNLOCK){
				CurrentTowerType = TowerType.CannonRed;
				placingTower = true;
				placingAOE = false;
				mouseWait = true;
			}
			if (TowerUI.isButtonClicked("Towerice") && PlayerLevel >= TOWER_ICE_UNLOCK){
				CurrentTowerType = TowerType.IceTower;
				placingTower = true;
				placingAOE = false;
				mouseWait = true;
			}
			if (TowerUI.isButtonClicked("Towerrocket") && PlayerLevel >= TOWER_ROCKET_UNLOCK){
				CurrentTowerType = TowerType.RocketTower;
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
	
	private void placeAOE(AOE a, int listID){
		AOEList.add(a);
		if (listID == ENEMY_LIST_ID){
			a.setEnemyList(waveManager.getCurrentWave().getEnemyList());
		}
		else if (listID == TOWER_LIST_ID){
			a.setTowerList(towerList);
		}
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
			TowerUI.draw();
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
					//Use placeAOE method to ensure AOEs aren't placed without fetching the appropriate list
					placeAOE(CurrentAOEType.makeAOE(Mouse.getX(), (int) Math.floor(HEIGHT - Mouse.getY() - 1)), CurrentAOEType.getListID());
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
			if (Keyboard.getEventKey() == Keyboard.KEY_UP && Keyboard.getEventKeyState()) {
				Clock.ResetMultiplier();
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
			if (Keyboard.getEventKey() == Keyboard.KEY_2 && Keyboard.getEventKeyState() && PlayerLevel >= TOWER_CANNONRED_UNLOCK) {
				CurrentTowerType = TowerType.CannonRed;
				placingTower = true;
				placingAOE = false;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_3 && Keyboard.getEventKeyState() && PlayerLevel >= TOWER_ICE_UNLOCK) {
				CurrentTowerType = TowerType.IceTower;
				placingTower = true;
				placingAOE = false;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_4 && Keyboard.getEventKeyState() && PlayerLevel >= TOWER_ROCKET_UNLOCK) {
				CurrentTowerType = TowerType.RocketTower;
				placingTower = true;
				placingAOE = false;
			}
			
			//Select and begin placing AOE
			
			if (Keyboard.getEventKey() == Keyboard.KEY_Q && Keyboard.getEventKeyState() && PlayerLevel >= AOE_FIRESTRIKE_UNLOCK) {
				CurrentAOEType = AOEType.FireStrike;
				placingAOE = true;
				placingTower = false;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_W && Keyboard.getEventKeyState() && PlayerLevel >= AOE_TOWERBUFF_UNLOCK) {
				CurrentAOEType = AOEType.TowerBuff;
				placingAOE = true;
				placingTower = false;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_E && Keyboard.getEventKeyState() && PlayerLevel >= AOE_SLOW_UNLOCK) {
				CurrentAOEType = AOEType.Slow;
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
