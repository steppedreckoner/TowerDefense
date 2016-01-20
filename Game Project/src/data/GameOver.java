package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.QuickLoad;
import static helpers.Artist.WIDTH;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import helpers.StateManager;
import helpers.StateManager.GameState;

import static helpers.StateManager.mouseButton0;

public class GameOver {
	
	private UI gameOverUI;
	private Texture background;
	
	public GameOver(){
		this.gameOverUI = new UI();
		this.background = QuickLoad("gameover");
		
		gameOverUI.addButton("Reset", "resetbutton64", (WIDTH - 256) / 2, 500);
		gameOverUI.addButton("Menu", "menubutton", (WIDTH - 256) / 2, 600);
		gameOverUI.addButton("Quit", "quitbutton", (WIDTH - 256) / 2, 700);
	}
	
	private void UpdateButtons(){
		if (Mouse.isButtonDown(0) && !mouseButton0){
			if (gameOverUI.isButtonClicked("Reset")){
				StateManager.setState(GameState.GAME);
			}
			if (gameOverUI.isButtonClicked("Menu")){
				StateManager.setState(GameState.MAINMENU);
			}
			if (gameOverUI.isButtonClicked("Quit")){
				System.exit(0);
			}
		}
		mouseButton0 = Mouse.isButtonDown(0);
	}
	
	public void Update(){
		DrawQuadTex(background, 0, 0, 2048, 1024);
		gameOverUI.draw();
		this.UpdateButtons();
	}
	

}
