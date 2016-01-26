package data;

import static data.TileGrid.CreateMap;
import static helpers.Artist.*;

import org.lwjgl.input.Mouse;

import UI.UI;
import helpers.StateManager;
import helpers.StateManager.GameState;

public class Game {

	private Player player;
	private WaveManager waveManager;
	private boolean mouseButton0;
	
	private UI gameUI;
	
	public Game(int[][] newMap){
		//Note: When loading maps from file, ensure there's only one start and one goal tile.
		CreateMap(newMap);	//Initializes the map (TileGrid Class)
		
		waveManager = new WaveManager();	//Deals with spawning enemies
		player = new Player(waveManager);	//Allows for player interactions
		player.setup();
		
		this.mouseButton0 = false;
		this.gameUI = new UI();
		gameUI.addButton("Menu", "menubutton", (WIDTH - 256) / 2, (int) (HEIGHT * .5f));
	}
	
	private void UpdateButtons(){
		if (Mouse.isButtonDown(0) && !mouseButton0){
			if (gameUI.isButtonClicked("Menu")){
				StateManager.setState(GameState.MAINMENU);
			}
		}
		mouseButton0 = Mouse.isButtonDown(0);
	}
	
	public void Update(){
		TileGrid.Draw();		//Draw the board
		player.update();
		if (player.showPauseMenu()){
			waveManager.pauseDraw();
			gameUI.draw();
			UpdateButtons();
		}
		else{
			waveManager.update();	//Enemy Actions (Don't do if game menu is up)
		}
	}
	

}
