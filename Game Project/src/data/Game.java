package data;

import static data.TileGrid.CreateMap;
import static helpers.StateManager.mouseButton0;

import org.lwjgl.input.Mouse;

import UI.UI;
import helpers.StateManager;
import helpers.StateManager.GameState;

public class Game {

	private Player player;
	private WaveManager waveManager;
	
	private UI gameUI;
	
	public Game(int[][] newMap){
		CreateMap(newMap);	//Initializes the map (TileGrid Class)
		waveManager = new WaveManager();	//Deals with spawning enemies
		player = new Player(waveManager);	//Allows for player interactions
		player.setup();
		
		this.gameUI = new UI();
		gameUI.addButton("Menu", "menubutton", 0, 0);
	}
	
	private void UpdateButtons(){
		if (Mouse.isButtonDown(0) && ! mouseButton0){
			if (gameUI.isButtonClicked("Menu")){
				StateManager.setState(GameState.MAINMENU);
			}
		}
		mouseButton0 = Mouse.isButtonDown(0);
	}
	
	public void Update(){
		TileGrid.Draw();		//Draw the board
		waveManager.update();	//Enemy Actions
		player.update();		//Player Actions
		gameUI.draw();
		UpdateButtons();
	}
	

}
