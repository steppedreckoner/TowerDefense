package data;

public class Game {

	private TileGrid grid;
	private Player player;
	private WaveManager waveManager;
	
	//Temp Variables
	private Tile startTile;
	
	public Game(int[][] map){
		grid = new TileGrid(map);
		startTile = grid.getTile(5, 7);
		grid.setStartTile(startTile);
		waveManager = new WaveManager(grid);
		player = new Player(grid, waveManager);
		player.setup();
		
	}
	
	public void Update(){
		grid.draw();			//Draw the board
		waveManager.update();	//Enemy Actions
		player.update();		//Player Actions
	}
	

}
