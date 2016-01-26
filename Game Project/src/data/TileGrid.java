package data;

import static helpers.Artist.*;

public class TileGrid {

	private static Tile[][] Map;
	private static int TilesWide = 25;
	private static int TilesHigh = 15;
	
	private static Tile StartTile; // Tile that enemies will spawn on. Currently
									// unique for each map.
	private static Tile GoalTile; // Tile that all enemies try to get to. Lives
									// are lost when an enemy makes it there.
									//These may be later changed to lists to 
									//allow for multiple spawn points.

	public static void CreateMap() {
		TilesWide = 25;
		TilesHigh = 15;
		Map = new Tile[25][15];
		for (int i = 0; i < Map.length; i++) {
			for (int j = 0; j < Map[i].length; j++) {
				Map[i][j] = new Tile(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Grass);
			}
		}
	}
	
	public static void CreateMap(int[][] newMap){
		TilesWide = newMap[0].length;
		TilesHigh = newMap.length;
		Map = new Tile[TilesWide][TilesHigh];
		for (int i = 0; i < Map.length; i++) {
			for (int j = 0; j < Map[i].length; j++) {
				switch (newMap[j][i]) {
				case 0:
					Map[i][j] = new Tile(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Grass);
					break;
				case 1:
					Map[i][j] = new Tile(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Dirt);
					break;
				case 2:
					Map[i][j] = new Tile(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Water);
					break;
				case 3:
					Map[i][j] = new Tile(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Start);
					StartTile = Map[i][j];
					break;
				case 4:
					Map[i][j] = new Tile(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Goal);
					GoalTile = Map[i][j];
					break;
				case 5:
					Map[i][j] = new Tile(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.NULL);
					break;
				}
			}
		}
	}

	public static void SetTile(int xCoord, int yCoord, TileType type) {
		Map[xCoord][yCoord] = new Tile(xCoord * TILE_SIZE, yCoord * TILE_SIZE, TILE_SIZE, TILE_SIZE, type);
	}

	public static Tile GetTile(int xPlace, int yPlace) {
		if (xPlace < TilesWide && yPlace < TilesHigh && xPlace >= 0 && yPlace >= 0) {
			return Map[xPlace][yPlace];
		} else {
			return new Tile(0, 0, 0, 0, TileType.NULL);
		}
	}

	public static void Draw() {
		for (int i = 0; i < Map.length; i++) {
			for (int j = 0; j < Map[i].length; j++) {
				Map[i][j].draw();
			}
		}
	}

	public static Tile GetStartTile() {
		return StartTile;
	}
	
	public static Tile GetGoalTile(){
		return GoalTile;
	}
	
	public static int GetTilesHigh(){
		return TilesHigh;
	}
	
	public static int GetTilesWide(){
		return TilesWide;
	}

}



//package data;
//
//import static helpers.Artist.*;
//
//public class TileGrid {
//
//	private static Tile[][] Map;
//	private static int TilesWide = 25;
//	private static int TilesHigh = 15;
//	
//	private static Tile StartTile; // Tile that enemies will spawn on. Currently
//									// unique for each map.
//	private static Tile GoalTile; // Tile that all enemies try to get to. Lives
//									// are lost when an enemy makes it there.
//									//These may be later changed to lists to 
//									//allow for multiple spawn points.
//
//	public static void CreateMap() {
//		TilesWide = 25;
//		TilesHigh = 15;
//		Map = new Tile[TilesHigh][TilesWide];
//		for (int i = 0; i < TilesHigh; i++) {
//			for (int j = 0; j < TilesWide; j++) {
//				Map[i][j] = new Tile(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Grass);
//			}
//		}
//	}
//	
//	public static void CreateMap(int[][] newMap){
//		TilesWide = newMap[0].length;
//		TilesHigh = newMap.length;
//		Map = new Tile[TilesHigh][TilesWide];
//		for (int i = 0; i < Map.length; i++) {
//			for (int j = 0; j < Map[i].length; j++) {
//				switch (newMap[i][j]) {
//				case 0:
//					Map[i][j] = new Tile(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Grass);
//					break;
//				case 1:
//					Map[i][j] = new Tile(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Dirt);
//					break;
//				case 2:
//					Map[i][j] = new Tile(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Water);
//					break;
//				case 3:
//					Map[i][j] = new Tile(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Start);
//					StartTile = Map[i][j];
//					break;
//				case 4:
//					Map[i][j] = new Tile(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Goal);
//					GoalTile = Map[i][j];
//					break;
//				case 5:
//					Map[i][j] = new Tile(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.NULL);
//					break;
//				}
//			}
//		}
//	}
//
//	public static void SetTile(int xCoord, int yCoord, TileType type) {
//		Map[yCoord][xCoord] = new Tile(xCoord * TILE_SIZE, yCoord * TILE_SIZE, TILE_SIZE, TILE_SIZE, type);
//	}
//
//	public static Tile GetTile(int xPlace, int yPlace) {
//		if (xPlace < TilesWide && yPlace < TilesHigh && xPlace >= 0 && yPlace >= 0) {
//			return Map[yPlace][xPlace];
//		} else {
//			return new Tile(0, 0, 0, 0, TileType.NULL);
//		}
//	}
//
//	public static void Draw() {
//		for (int i = 0; i < Map.length; i++) {
//			for (int j = 0; j < Map[i].length; j++) {
//				Map[i][j].draw();
//			}
//		}
//	}
//
//	public static Tile GetStartTile() {
//		return StartTile;
//	}
//	
//	public static Tile GetGoalTile(){
//		return GoalTile;
//	}
//	
//	public static int GetTilesHigh(){
//		return TilesHigh;
//	}
//	
//	public static int GetTilesWide(){
//		return TilesWide;
//	}
//
//}
