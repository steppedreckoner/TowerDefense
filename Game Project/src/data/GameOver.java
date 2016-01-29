package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.QuickLoad;
import static helpers.Artist.WIDTH;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import helpers.StateManager;
import helpers.StateManager.GameState;

public class GameOver {
	
	private UI gameOverUI;
	private Texture background;
	private boolean mouseButton0;
	
	public GameOver(){
		this.mouseButton0 = false;
		this.background = QuickLoad("gameover");
		this.gameOverUI = new UI();
		this.gameOverUI.addButton("Reset", "resetbutton", (WIDTH - 256) / 2, 500);
		this.gameOverUI.addButton("Menu", "menubutton", (WIDTH - 256) / 2, 600);
		this.gameOverUI.addButton("Quit", "quitbutton", (WIDTH - 256) / 2, 700);
		
		System.out.println("GameOver");
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
