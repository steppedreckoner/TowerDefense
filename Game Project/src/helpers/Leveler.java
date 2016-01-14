package helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import data.Tile;
import data.TileGrid;
import data.TileType;

public class Leveler {
	
	public static void SaveMap(TileGrid grid, String mapName){
//		System.out.println("Enter File Name: ");
//		Scanner sc = new Scanner(System.in);
//		String mapName = sc.next();
//		sc.close();
		String mapData = "";
		for (int i = 0; i < grid.getTilesWide(); i++){
			for (int j = 0; j < grid.getTilesHigh(); j++){
				mapData += getTileID(grid.getTile(i, j));
			}
		}
		
		
		try {
			File file = new File(mapName);
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(mapData);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static TileGrid LoadMap(String mapName){
		TileGrid grid = new TileGrid();
		try{
			BufferedReader reader = new BufferedReader(new FileReader(mapName));
			String data = reader.readLine();
			reader.close();
			for (int i  = 0; i < grid.getTilesWide(); i++){
				for (int j = 0; j < grid.getTilesHigh(); j++){
					grid.setTile(i, j, getTileType((data.substring(i * grid.getTilesHigh() + j, i * grid.getTilesHigh() + j + 1))));
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		
		
		return grid;
	}
	
	public static TileType getTileType(String ID){
		TileType type = TileType.NULL;
		switch(ID){
		case "0":
			type = TileType.Grass;
			break;
		case "1":
			type = TileType.Dirt;
			break;
		case "2": 
			type = TileType.Water;
			break;
		case "3": 
			type = TileType.NULL;
			break;		
		}
		
		return type;
	}
	
	public static String getTileID(Tile t){
		String ID = "E";
		switch (t.getType()){
		case Grass:
			ID = "0";
			break;
		case Dirt:
			ID = "1";
			break;
		case Water:
			ID = "2";
			break;
		case NULL:
			ID = "3";
			break;
		}		
		return ID;
	}

}
