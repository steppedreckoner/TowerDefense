package data;

import static data.TileGrid.*;

public class Game {

	private Player player;
	private WaveManager waveManager;
	
	public Game(int[][] newMap){
		CreateMap(newMap);	//Initializes the map (TileGrid Class)
		waveManager = new WaveManager();	//Deals with spawning enemies
		player = new Player(waveManager);	//Allows for player interactions
		player.setup();
		
	}
	
	public void Update(){
		TileGrid.Draw();		//Draw the board
		waveManager.update();	//Enemy Actions
		player.update();		//Player Actions
	}
	

}
