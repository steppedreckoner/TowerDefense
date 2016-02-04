package data;

import static helpers.Artist.*;

import java.awt.Font;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import helpers.StateManager;
import helpers.StateManager.GameState;

public class HowTo {
	
	private static Texture background;
	private static UI HowToUI;
	private static TrueTypeFont HowToFont;
	private static boolean IsSetup = false, MouseButton0;
	
	public static void Setup() {
		background = QuickLoad("howtobackground");
		HowToFont = new TrueTypeFont( new Font("Tahoma", Font.PLAIN, 16), false);
		MouseButton0 = Mouse.isButtonDown(0);
		
		HowToUI = new UI();
		HowToUI.addButton("Play", "playbutton", WIDTH / 2 - 128, (int) (HEIGHT * .73f));
		HowToUI.addButton("Menu", "menubutton", WIDTH / 2 - 128, (int) (HEIGHT * .82f));
		
		IsSetup = true;
	}
	
	public static boolean IsSetup() {
		return IsSetup;
	}
	
	public static void Update() {
		DrawQuadTex(background, 0, (HEIGHT - background.getImageHeight()) / 2, 2048, 1024);
		
		HowToFont.drawString(10,  40, "PLACING TOWERS:");
		HowToFont.drawString(10, 60 + 0 * 18, "Towers can be selected with either the Tower Menu (T) or with their hotkeys (1-4).");
		HowToFont.drawString(10, 60 + 1 * 18, "Place towers by left clicking on an unoccupied grass tile. You can cancel the");
		HowToFont.drawString(10, 60 + 2 * 18, "current placement by right clicking. If you don't have enough money, you will");
		HowToFont.drawString(10, 60 + 3 * 18, "not be able to place the selected tower.");
		HowToFont.drawString(10, 60 + 4 * 18, "");
		HowToFont.drawString(10, 60 + 5 * 18, "A tower can be upgraded by hovering over it and pressing 'U'. Similarly, a tower can");
		HowToFont.drawString(10, 60 + 6 * 18, "be deleted by hovering over it and pressing 'D', granting an 80% refund of its base cost.");
		
		HowToFont.drawString(10, 200, "USING AOE:");
		HowToFont.drawString(10, 220 + 0 * 18, "AOE stands for 'Area of Effect'. These abilities affect all applicable objects");
		HowToFont.drawString(10, 220 + 1 * 18, "within their range. Once unlocked they can be accessed via their hotkeys (Q,W,E).");
		HowToFont.drawString(10, 220 + 2 * 18, "Each AOE has its own cooldown timer, and is unusable until its cooldown is complete.");
		HowToFont.drawString(10, 220 + 3 * 18, "FIRE STRIKE (Q): Does damage over time to all enemies in its range");
		HowToFont.drawString(10, 220 + 4 * 18, "TOWER BUFF (W): Doubles firing rate of all affected towers");
		HowToFont.drawString(10, 220 + 5 * 18, "SLOW (E): Slows all affected enemies");
		
		HowToUI.draw();
		UpdateButtons();
	}
	
	private static void UpdateButtons(){
		if (Mouse.isButtonDown(0) && !MouseButton0){
			if (HowToUI.isButtonClicked("Play")){
				StateManager.setState(GameState.GAME);
			}
			if (HowToUI.isButtonClicked("Menu")){
				StateManager.setState(GameState.MAINMENU);
			}
		}
		MouseButton0 = Mouse.isButtonDown(0);
	}

}
