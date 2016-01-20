package data;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import helpers.Clock;
import helpers.StateManager;

import static helpers.Artist.*;
import static data.TileGrid.*;

import java.util.ArrayList;

public class Player {

	private TileType[] types;
	private WaveManager waveManager;
	private ArrayList<Tower> towerList;
	public static int Cash, Lives;
	public static final int STARTING_CASH = 150, STARTING_LIVES = 10;

	public Player(WaveManager waveManager) {
		this.types = new TileType[3];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
		this.waveManager = waveManager;
		this.towerList = new ArrayList<Tower>();
		Cash = 0;
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
	
	//Might want to add a life every x waves
	public static void modifyLives(int amount) {
		Lives += amount;
		if (amount == -1){
			System.out.println("Ouch! Lost 1 Life! " + Lives + " remaining!");
		}
		if (Lives <= 0){
			StateManager.setState(StateManager.GameState.GAMEOVER);
		}
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
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
				Clock.ChangeMultiplier(0.2f);
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()) {
				Clock.ChangeMultiplier(-0.2f);
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_T && Keyboard.getEventKeyState()) {
				if (modifyCash(-40)) {
					// Get tile at mouse coordinates and place tower there
					Tile tile = GetTile((int) Math.floor(Mouse.getX() / TILE_SIZE),(int) Math.floor((HEIGHT - Mouse.getY() - 1) / TILE_SIZE));
					towerList.add(new TowerCannonBlue(tile, waveManager.getCurrentWave().getEnemyList()));
				}
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_R && Keyboard.getEventKeyState()) {
				if (modifyCash(-40)) {
					// Get tile at mouse coordinates and place tower there
					Tile tile = GetTile((int) Math.floor(Mouse.getX() / TILE_SIZE),(int) Math.floor((HEIGHT - Mouse.getY() - 1) / TILE_SIZE));
					towerList.add(new TowerCannonRed(tile, waveManager.getCurrentWave().getEnemyList()));
				}
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_I && Keyboard.getEventKeyState()) {
				if (modifyCash(-110)) {
					// Get tile at mouse coordinates and place tower there
					Tile tile = GetTile((int) Math.floor(Mouse.getX() / TILE_SIZE),(int) Math.floor((HEIGHT - Mouse.getY() - 1) / TILE_SIZE));
					towerList.add(new TowerIce(tile, waveManager.getCurrentWave().getEnemyList()));
				}
			}
			//Basic tower deletion
			//TO DO: Change tower cost to be part of the TowerType so cost can be refunded on deletion
			if (Keyboard.getEventKey() == Keyboard.KEY_D && Keyboard.getEventKeyState()){
				Tile tile = GetTile((int) Math.floor(Mouse.getX() / TILE_SIZE),(int) Math.floor((HEIGHT - Mouse.getY() - 1) / TILE_SIZE));
				Tower tower = null;
				for (Tower t : towerList){
					if (tile == t.getStartTile()){
						tower = t;
						break;
					}
				}
				if (tower != null){
					towerList.remove(tower);
				}
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_1 && Keyboard.getEventKeyState()) {
				waveManager.setEnemyType(EnemyType.EnemyUFO);
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_2 && Keyboard.getEventKeyState()) {
				waveManager.setEnemyType(EnemyType.EnemySpacePlane);
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_3 && Keyboard.getEventKeyState()) {
				waveManager.setEnemyType(EnemyType.EnemyNuke);
			}
		}
	}

}
