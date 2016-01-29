package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.HEIGHT;
import static helpers.Artist.QuickLoad;

import java.awt.Font;

import static helpers.Artist.*;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import helpers.StateManager;
import helpers.StateManager.GameState;

public class MainMenu {

	private Texture background;
	private UI menuUI;
	private boolean mouseButton0;
	private TrueTypeFont imageCreditFont;
	
	public MainMenu(){
		this.mouseButton0 = false;
		this.background = QuickLoad("Ion_cannon_firing");
		this.menuUI = new UI();
		this.menuUI.addButton("Play", "playbutton", WIDTH / 2 - 128, (int) (HEIGHT * .6f));
		this.menuUI.addButton("Editor", "editorbutton", WIDTH / 2 - 128, (int) (HEIGHT * .7f));
		this.menuUI.addButton("Quit", "quitbutton", WIDTH / 2 - 128, (int) (HEIGHT * .8f));
		
		imageCreditFont = new TrueTypeFont( new Font("Arial", Font.PLAIN, 18), false);
	}
	
	private void UpdateButtons(){
		if (Mouse.isButtonDown(0) && !mouseButton0){
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
		imageCreditFont.drawString(WIDTH - 150, HEIGHT - 100, "Image Credit", Color.gray);
		menuUI.draw();
		UpdateButtons();
	}
}
