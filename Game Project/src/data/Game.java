package data;

import static data.TileGrid.*;

public class Game {

	private Player player;
	private WaveManager waveManager;
	
	//Temp Variables
	
	public Game(int[][] newMap){
		//Calls to TileGrid Class. Initializes the map
		CreateMap(newMap);
		SetStartTile(GetTile(5,7));
		waveManager = new WaveManager();
		player = new Player(waveManager);
		player.setup();
		
	}
	
	public void Update(){
		TileGrid.Draw();		//Draw the board
		waveManager.update();	//Enemy Actions
		player.update();		//Player Actions
	}
	

}
