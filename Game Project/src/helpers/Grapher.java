package helpers;

import static data.TileGrid.*;

import data.Tile;
import data.TileType;

/*At each tile, there are four possible moves (diagonals are excluded for now).
 * For each tile in TileGrid.Map, this will store four values corresponding to
 * the possibility of movement. '1' represents allowed, '0' represents blocked.
 * The four values in order represent up, right, down, left (N, E, S, W).
*/
public class Grapher {

	private static int[][][] LandGraph, AirGraph;
	
	public static void CreateLandGraph(){
		//Every entry is a zero here, which is good b/c we default to no connectedness
		LandGraph = new int[GetTilesWide()][GetTilesHigh()][4];
		for (int i = 0; i < GetTilesWide(); i++){
			for (int j = 0; j < GetTilesWide(); j++){
				TileType currentType = GetTile(i,j).getType();
				if (currentType.canGround()){
					//Test Up
					TileType testType = GetTile(i,j - 1).getType();
					if (testType.canGround()){
						LandGraph[i][j][0] = 1;
					}
					//Test Right
					testType = GetTile(i + 1,j).getType();
					if (testType.canGround()){
						LandGraph[i][j][1] = 1;
					}
					//Test Down
					testType = GetTile(i,j + 1).getType();
					if (testType.canGround()){
						LandGraph[i][j][1] = 1;
					}
					//Test Left
					testType = GetTile(i - 1,j).getType();
					if (testType.canGround()){
						LandGraph[i][j][1] = 1;
					}
				}
			}
		}
	}
	
	public static void CreateAirGraph(){
		//Also all zeroes here
		AirGraph = new int[GetTilesWide()][GetTilesHigh()][4];
		for (int i = 0; i < GetTilesWide(); i++){
			for (int j = 0; j < GetTilesWide(); j++){
				TileType currentType = GetTile(i,j).getType();
				if (currentType.canFly()){
					//Test Up
					TileType testType = GetTile(i,j - 1).getType();
					if (testType.canFly()){
						AirGraph[i][j][0] = 1;
					}
					//Test Right
					testType = GetTile(i + 1,j).getType();
					if (testType.canFly()){
						AirGraph[i][j][1] = 1;
					}
					//Test Down
					testType = GetTile(i,j + 1).getType();
					if (testType.canFly()){
						AirGraph[i][j][1] = 1;
					}
					//Test Left
					testType = GetTile(i - 1,j).getType();
					if (testType.canFly()){
						AirGraph[i][j][1] = 1;
					}
				}
			}
		}
	}
	
	public static boolean CanGround(Tile tile, int direction){
		return LandGraph[tile.getXPlace()][tile.getYPlace()][direction] == 1;
	}
	
	public static boolean CanFly(Tile tile, int direction){
		return AirGraph[tile.getXPlace()][tile.getYPlace()][direction] == 1;
	}
	
}
