package data;

import static data.TileGrid.CreateMap;
import static helpers.Artist.*;
import static helpers.Clock.*;

import java.awt.Font;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.TrueTypeFont;

import UI.UI;
import helpers.Clock;
import helpers.StateManager;
import helpers.StateManager.GameState;

public class Game {

	private Player player;
	private WaveManager waveManager;
	private boolean mouseButton0;
	
	public static TrueTypeFont GameFont;
	
	private UI gameUI;
	
	public Game(int[][] newMap, boolean newGame){
		//Note: When loading maps from file, ensure there's only one start and one goal tile.
		CreateMap(newMap);	//Initializes the map (TileGrid Class)
		
		WaveManager.Setup();
		waveManager = new WaveManager();	//Deals with spawning enemies
		player = new Player();				//Allows for player interactions
		if (newGame){
			Player.Setup();
		}
		else {
			
		}
		this.mouseButton0 = false;
		this.gameUI = new UI();
		gameUI.addButton("Continue", "continuebutton", (WIDTH - 256) / 2, (int) (HEIGHT * .55f));
		gameUI.addButton("HowTo", "howtobutton", (WIDTH - 256) / 2, (int) (HEIGHT * .64f));
		gameUI.addButton("Menu", "menubutton", (WIDTH - 256) / 2, (int) (HEIGHT * .73f));
		
		GameFont = new TrueTypeFont( new Font("Tahoma", Font.BOLD, 24), false);
		
		//Ensures the game begins unpaused (could happen if game is paused and exited and another game is started)
		if (IsPaused()){
			Pause();
		}
	}
	
	private void UpdateButtons(){
		if (Mouse.isButtonDown(0) && !mouseButton0){
			if (gameUI.isButtonClicked("Continue") && !mouseButton0){
				player.closePauseMenu();
				Clock.Unpause();
			}
			if (gameUI.isButtonClicked("HowTo") && !mouseButton0){
				StateManager.setState(GameState.HOWTO);
			}
			if (gameUI.isButtonClicked("Menu")){
				StateManager.setState(GameState.MAINMENU);
			}
		}
		mouseButton0 = Mouse.isButtonDown(0);
	}
	
	private void drawGameInfo(){
		Player.DrawCash();
		waveManager.drawWaveNumber();
		Player.DrawLives();
		Player.DrawExp();
		Player.DrawLevel();
	}
	
	public void Update(){
		TileGrid.Draw();		//Draw the board	
		waveManager.update();	//Enemy Actions
		player.update();		//Player Actions
		drawGameInfo();
		if (player.isShowPauseMenu()){
			gameUI.draw();
			UpdateButtons();
		}
		
	}
	

}
