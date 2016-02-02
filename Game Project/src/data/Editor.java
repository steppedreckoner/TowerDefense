package data;

import static data.TileGrid.CreateMap;
import static helpers.Artist.HEIGHT;
import static helpers.Artist.TILE_SIZE;
import static helpers.Artist.WIDTH;
import static helpers.Leveler.LoadMap;
import static helpers.Clock.*;

import java.awt.Font;
import java.io.File;
import java.nio.file.Path;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.TrueTypeFont;

import UI.UI;
import helpers.FileChooser;
import helpers.Leveler;
import helpers.StateManager;
import helpers.StateManager.GameState;

public class Editor {

	private boolean showMenu;
	private TileType currentType;
	
	private boolean mouseButton0, recentlySaved, recentlyLoaded;
	private UI editorUI;
	private float saveTime, loadTime;
	
	private String savePath, loadPath;
	
	private static TrueTypeFont EditorFont;
	
	private static final float RECENT_TIME = 5f;

	public Editor() {
		CreateMap();
		this.currentType = TileType.Grass;
		this.showMenu = false;
		this.mouseButton0 = false;
		this.editorUI = new UI();
		this.editorUI.addButton("Save", "savebutton", (WIDTH - 256) / 2, (int) (HEIGHT * .4f));
		this.editorUI.addButton("Load", "loadbutton", (WIDTH - 256) / 2, (int) (HEIGHT * .5f));
		this.editorUI.addButton("Menu", "menubutton", (WIDTH - 256) / 2, (int) (HEIGHT * .6f));
		this.recentlySaved = false;
		this.recentlyLoaded = false;
		this.savePath = "";
		this.loadPath = "";
		this.saveTime = 0f;
		this.loadTime = 0f;
		
		EditorFont = new TrueTypeFont( new Font("Tahoma", Font.BOLD, 24), false);
	}

	public void Update() {
		TileGrid.Draw();
		if (showMenu){
			showMenu();
		}
		//Display saved/loaded filenames for RECENT_TIME after saving/loading
		if (recentlySaved && (saveTime < RECENT_TIME)){
			EditorFont.drawString(16f, 8f, "Saving File: " + savePath);
			saveTime += Delta();
		}
		if (recentlyLoaded && (loadTime < RECENT_TIME)){
			EditorFont.drawString(16f, 36f, "Loading File: " + loadPath);
			loadTime += Delta();
		}
		// Handle mouse input
		//Only change tiles if menu isn't up
		if (Mouse.isButtonDown(0) && !showMenu) {
			setTile();
		}
		// Handle keyboard input
		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_1 && Keyboard.getEventKeyState()) {
				currentType = TileType.Grass;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_2 && Keyboard.getEventKeyState()) {
				currentType = TileType.Dirt;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_3 && Keyboard.getEventKeyState()) {
				currentType = TileType.Water;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_4 && Keyboard.getEventKeyState()) {
				currentType = TileType.Start;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_5 && Keyboard.getEventKeyState()) {
				currentType = TileType.Goal;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_M && Keyboard.getEventKeyState()) {
				showMenu = !showMenu;
			}
		}
	}
	
	private void showMenu(){
		editorUI.draw();
		UpdateButtons();
	}
	
	private void UpdateButtons(){
		if (Mouse.isButtonDown(0) && !mouseButton0){
			if (editorUI.isButtonClicked("Save")){
				Path path = FileChooser.SaveFile(Leveler.GetMapString(), FileChooser.MAP_FILE);
				recentlySaved = true;
				saveTime = 0f;
				savePath = path.getFileName().toString();
			}
			if (editorUI.isButtonClicked("Load")){
				File mapFile = FileChooser.ChooseFile(FileChooser.MAP_FILE);
				if (mapFile != null){
					LoadMap(mapFile);
					recentlyLoaded = true;
					loadTime = 0f;
					loadPath = mapFile.getName();
				}
				else{
					loadPath = "Invalid File";
					System.out.println("Invalid File");
				}
			}
			if (editorUI.isButtonClicked("Menu")){
				StateManager.setState(GameState.MAINMENU);
			}
		}
		mouseButton0 = Mouse.isButtonDown(0);
	}

	private void setTile() {
		TileGrid.SetTile((int) Math.floor(Mouse.getX() / TILE_SIZE),
				(int) Math.floor((HEIGHT - Mouse.getY() - 1) / TILE_SIZE), currentType);

	}
	
}
