package helpers;

import data.Editor;
import data.Game;
import data.GameOver;
import data.MainMenu;

public class StateManager {

	public static enum GameState {
		MAINMENU, GAME, EDITOR, GAMEOVER
	}
	
	public static GameState gameState = GameState.MAINMENU;
	public static MainMenu MainMenu;
	public static Game Game;
	public static Editor Editor;
	public static GameOver GameOver;
	
	public static boolean mouseButton0;
	
	static int[][] map = {
			{1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{1, 1, 1, 0, 0, 1, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0},
			{0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 1, 1, 2, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 4, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 2, 0, 0, 1, 0, 0, 0, 1, 3, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 0, 2, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
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
				Game = new Game(map);
				System.out.println("New Game");
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
		case GAMEOVER:
			if (GameOver == null){
				Game = null;
				GameOver = new GameOver();
				System.out.println("New GameOver");
			}
			GameOver.Update();
			break;
		}
	}
	
	public static void setState(GameState newState){
		gameState = newState;
	}
	
}
