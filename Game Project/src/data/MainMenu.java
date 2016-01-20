package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.HEIGHT;
import static helpers.Artist.QuickLoad;
import static helpers.Artist.*;
import static helpers.StateManager.mouseButton0;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import helpers.StateManager;
import helpers.StateManager.GameState;

public class MainMenu {

	private Texture background;
	private UI menuUI;
	
	public MainMenu(){
		background = QuickLoad("Ion_cannon_firing");
		menuUI = new UI();
		menuUI.addButton("Play", "playbutton", WIDTH / 2 - 128, (int) (HEIGHT * .6f));
		menuUI.addButton("Editor", "editorbutton", WIDTH / 2 - 128, (int) (HEIGHT * .7f));
		menuUI.addButton("Quit", "quitbutton", WIDTH / 2 - 128, (int) (HEIGHT * .8f));
	}
	
	private void UpdateButtons(){
		if (Mouse.isButtonDown(0) && ! mouseButton0){
			if (menuUI.isButtonClicked("Play")){
				StateManager.setState(GameState.GAME);
			}
			if (menuUI.isButtonClicked("Editor")){
				StateManager.setState(GameState.EDITOR);
			}
			if (menuUI.isButtonClicked("Quit")){
				System.exit(0);
			}
		}
		mouseButton0 = Mouse.isButtonDown(0);
	}
	
	public void Update(){
		ClearDisplay();
		DrawQuadTex(background, (WIDTH - background.getImageWidth()) / 2, (HEIGHT - background.getImageHeight()) / 2, 2048, 1024);	//background tex is 1600 x 891
		menuUI.draw();
		UpdateButtons();
	}
}
