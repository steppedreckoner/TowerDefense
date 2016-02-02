package helpers;

import static helpers.Leveler.GetMapArray;

import java.io.File;

import data.Editor;
import data.Game;
import data.GameOver;
import data.HowTo;
import data.MainMenu;

public class StateManager {

	public static enum GameState {
		MAINMENU, GAME, EDITOR, GAMEOVER, HOWTO
	}
	
	public static GameState gameState = GameState.MAINMENU;
	public static MainMenu MainMenu;
	public static Game Game;
	public static Editor Editor;
	public static GameOver GameOver;
	public static File mapFile;
	
	static int[][] defaultMap = {
			{3, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{1, 1, 1, 0, 0, 1, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0},
			{0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 1, 1, 2, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 4, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 2, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 0, 2, 0, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	};
	
	public static void Update(){
		switch(gameState){
		case MAINMENU:
			Game = null;
			if (MainMenu == null){
				MainMenu = new MainMenu();
			}
			MainMenu.Update();
			break;
		case GAME:
			if (Game == null){
				GameOver = null;
				//Attempt to set mapFile to user input and check if it worked
				if ((mapFile = FileChooser.ChooseFile(FileChooser.MAP_FILE)) != null){
					int[][] gameMap = GetMapArray(mapFile);
					Game = new Game(gameMap, true);
					System.out.println("New Game");
				}
				else{
					//If loading a file was not successful, set gameState 
					//back to MAINMENU and break to avoid updating a null game.
					System.out.println("Invalid File Type");
					gameState = GameState.MAINMENU;
					break;
					//Uncomment this section and comment out the above to have 
					//default map loaded in case of file selection failure.
//					Game = new Game(defaultMap);
//					System.out.println("File selection unsuccessful. Beginning with default map");
//					System.out.println("New Game");
				}
			}
			Game.Update();
			break;
		case EDITOR:
			if (Editor == null){
				Editor = new Editor();
				System.out.println("New Editor");
			}
			Editor.Update();
			break;
		case HOWTO:
			if (!HowTo.IsSetup()) {
				HowTo.Setup();
			}
			HowTo.Update();
			break;
		case GAMEOVER:
			if (GameOver == null){
				Game = null;
				GameOver = new GameOver();
			}
			GameOver.Update();
			break;
		}
	}
	
	public static void setState(GameState newState){
		gameState = newState;
	}
	
}
