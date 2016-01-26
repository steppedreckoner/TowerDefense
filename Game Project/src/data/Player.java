package data;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import helpers.Clock;
import helpers.StateManager;

import static helpers.Artist.*;
import static data.TileGrid.*;

import java.util.ArrayList;

public class Player {

	private WaveManager waveManager;
	private ArrayList<Tower> towerList;
	private boolean showPauseMenu;
	private boolean showTowerMenu;
	public static int Cash, Lives;
	private static TowerType CurrentTowerType;
	public static final int STARTING_CASH = 150, STARTING_LIVES = 10;

	public Player(WaveManager waveManager) {
		this.waveManager = waveManager;
		this.towerList = new ArrayList<Tower>();
		this.showPauseMenu = false;
		this.showTowerMenu = false;
		CurrentTowerType = TowerType.CannonBlue;
		Cash = 0; // These are modified when setup() is called
		Lives = 0;
	}

	public void setup() {
		Cash = STARTING_CASH;
		Lives = STARTING_LIVES;
	}

	public static boolean modifyCash(int amount) {
		if (Cash + amount >= 0) {
			Cash += amount;
			System.out.println(Cash);
			return true;
		}
		System.out.println("Insufficient Funds!");
		return false;
	}

	// Might want to add a life every x waves
	public static void modifyLives(int amount) {
		Lives += amount;
		if (amount == -1) {
			System.out.println("Ouch! Lost 1 Life! " + Lives + " remaining!");
		}
		if (Lives <= 0) {
			StateManager.setState(StateManager.GameState.GAMEOVER);
		}
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

	public void update() {
		for (Tower t : towerList) {
			t.RefreshEnemies(waveManager.getCurrentWave().getEnemyList());
			t.update();
		}

		// Handle mouse input
		if (Mouse.isButtonDown(0)) {
			// SetTile();
		}
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
			if (Keyboard.getEventKey() == Keyboard.KEY_1 && Keyboard.getEventKeyState()) {
				CurrentTowerType = TowerType.CannonBlue;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_2 && Keyboard.getEventKeyState()) {
				CurrentTowerType = TowerType.CannonRed;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_3 && Keyboard.getEventKeyState()) {
				CurrentTowerType = TowerType.IceTower;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_T && Keyboard.getEventKeyState()) {
				// Get tile at mouse coordinates. Will try to place tower there.
				Tile tile = GetTile((int) Math.floor(Mouse.getX() / TILE_SIZE), (int) Math.floor((HEIGHT - Mouse.getY() - 1) / TILE_SIZE));
				if (tile.canBuild()) {
					if (modifyCash(CurrentTowerType.getCost())) {
						placeTower(CurrentTowerType.makeTower(tile, waveManager.getCurrentWave().getEnemyList()), tile);
					}
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
					modifyCash((int) (-tower.getCost() * .8f));
					towerList.remove(tower);
					tower.getStartTile().setHasTower(false);	//There might be a way to abuse this, but it should work for now.
				}
			}
		}
	}

}
