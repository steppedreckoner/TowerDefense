package data;

import static data.TileGrid.*;

public class TileDist extends Tile {
	
	private int distance;
	private Tile tile;
	private static TileDist[][] DistMap = new TileDist[GetTilesWide()][GetTilesHigh()];

	public TileDist(Tile tile, int distance) {
		super(tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight(), tile.getType());
		this.distance = distance;
		this.tile = tile;
		SetTileDist(this);
	}

	public int getDistance(){
		return distance;
	}
	
	@Override
	public TileDist[] getNeighbors(){
		TileDist[] neighbors = new TileDist[4];
		int xPlace = this.getXPlace();
		int yPlace = this.getYPlace();
		neighbors[0] = DistMap[xPlace][yPlace - 1];
		neighbors[1] = DistMap[xPlace + 1][yPlace];
		neighbors[2] = DistMap[xPlace][yPlace + 1];
		neighbors[3] = DistMap[xPlace - 1][yPlace];
		return neighbors;
	}
	
	public Tile getTile(){
		return this.tile;
	}
	
	public static TileDist GetTileDist(int xPlace, int yPlace){
		if (xPlace >= 0 && yPlace >= 0 && xPlace < GetTilesWide() && yPlace < GetTilesHigh()){
			return DistMap[xPlace][yPlace];
		} else {
			return new TileDist(new Tile(0, 0, 0, 0, TileType.NULL), Integer.MAX_VALUE);
		}
	}	
	
	public static void SetTileDist(TileDist newTile){
		DistMap[newTile.getXPlace()][newTile.getYPlace()] = newTile;
	}
	
}
