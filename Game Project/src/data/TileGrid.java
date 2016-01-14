package data;

import static helpers.Artist.*;

public class TileGrid {
	
	public Tile[][] map;
	private int tilesWide;
	private int tilesHigh;
	private Tile startTile;	//Tile that enemies will spawn on. Currently unique for each map.
	
	public TileGrid(){
		this.tilesWide = 25;
		this.tilesHigh = 15;
		map = new Tile[25][15];
		for (int i = 0; i < map.length; i++){
			for (int j = 0; j < map[i].length; j++){
				map[i][j] = new Tile(i*TILE_SIZE, j*TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Grass);
			}
		}
	}
	
	public TileGrid(int[][] newMap){
		this.startTile = startTile;
		this.tilesWide = newMap[0].length;
		this.tilesHigh = newMap.length;
		map = new Tile[tilesWide][tilesHigh];
		for (int i = 0; i < map.length; i++){
			for (int j = 0; j < map[i].length; j++){
				switch (newMap[j][i]){
				case 0:
					map[i][j] = new Tile(i*TILE_SIZE, j*TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Grass);
					break;
				
				case 1:
					map[i][j] = new Tile(i*TILE_SIZE, j*TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Dirt);
					break;
					
				case 2:
					map[i][j] = new Tile(i*TILE_SIZE, j*TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Water);
					break;
				}
			}
		}
	}
	
	public void setTile(int xCoord, int yCoord, TileType type){
		map[xCoord][yCoord] = new Tile(xCoord*TILE_SIZE, yCoord*TILE_SIZE, TILE_SIZE, TILE_SIZE, type);
	}
	
	public Tile getTile(int xPlace, int yPlace){
		if (xPlace < tilesWide && yPlace < tilesHigh && xPlace >= 0 && yPlace >= 0){
			return map[xPlace][yPlace];
		}
		else {
			return new Tile(0, 0, 0, 0, TileType.NULL);
		}
	}
	
	public void draw(){
		for (int i = 0; i < map.length; i++){
			for (int j = 0; j < map[i].length; j++){
				map[i][j].draw();
			}
		}
	}

	public int getTilesWide() {
		return tilesWide;
	}

	public void setTilesWide(int tilesWide) {
		this.tilesWide = tilesWide;
	}

	public int getTilesHigh() {
		return tilesHigh;
	}

	public void setTilesHigh(int tilesHigh) {
		this.tilesHigh = tilesHigh;
	}
	
	public void setStartTile(Tile startTile){
		this.startTile = startTile;
	}
	
	public Tile getStartTile(){
		return startTile;
	}

}
