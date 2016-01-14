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

import static data.TileGrid.*;

public class Leveler {
	
	public static void SaveMap(String mapName){
//		System.out.println("Enter File Name: ");
//		Scanner sc = new Scanner(System.in);
//		String mapName = sc.next();
//		sc.close();
		String mapData = "";
		for (int i = 0; i < GetTilesWide(); i++){
			for (int j = 0; j < GetTilesHigh(); j++){
				mapData += getTileID(GetTile(i, j));
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
	
	public static void LoadMap(String mapName){
		CreateMap();
		try{
			BufferedReader reader = new BufferedReader(new FileReader(mapName));
			String data = reader.readLine();
			reader.close();
			for (int i  = 0; i < GetTilesWide(); i++){
				for (int j = 0; j < GetTilesHigh(); j++){
					SetTile(i, j, getTileType((data.substring(i * GetTilesHigh() + j, i * GetTilesHigh() + j + 1))));
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
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
			type = TileType.Start;
			break;
		case "4": 
			type = TileType.Goal;
			break;
		case "5": 
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
		case Start:
			ID = "3";
			break;
		case Goal:
			ID = "4";
			break;
		case NULL:
			ID = "5";
			break;
		}		
		return ID;
	}

}
