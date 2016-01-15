package data;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import helpers.Artist;
import helpers.StateManager;
import helpers.StateManager.GameState;

import static helpers.Artist.*;

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
		if (Mouse.isButtonDown(0)){
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
	}
	
	public void Update(){
		DrawQuadTex(background, (WIDTH - 1600) / 2, (HEIGHT - 891) / 2, 2048, 1024);
		menuUI.draw();
		UpdateButtons();
	}
}
