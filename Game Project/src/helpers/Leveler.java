package helpers;

import static data.TileGrid.CreateMap;
import static data.TileGrid.GetTile;
import static data.TileGrid.GetTilesHigh;
import static data.TileGrid.GetTilesWide;
import static data.TileGrid.SetTile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import data.Tile;
import data.TileType;

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
	
	public static int[][] GetMapArray(String mapName){
		int[][] newMap = new int[GetTilesHigh()][GetTilesWide()];	//Maps taken as input for Game(map) have these dimensions
		try {
			BufferedReader br = new BufferedReader(new FileReader(mapName));
			String data = br.readLine();
			br.close();
			int index = 0;
			for (int j = 0; j < GetTilesWide(); j++){	//Must be done in this order because of how TileGrid stores its map
				System.out.println();
				for (int i = 0; i < GetTilesHigh(); i++){
					System.out.print(data.substring(index, index + 1));
					newMap[i][j] = Integer.parseInt(data.substring(index, index + 1));
					index++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return newMap;
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



//package helpers;
//
//import static data.TileGrid.CreateMap;
//import static data.TileGrid.GetTile;
//import static data.TileGrid.GetTilesHigh;
//import static data.TileGrid.GetTilesWide;
//import static data.TileGrid.SetTile;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//
//import data.Tile;
//import data.TileType;
//
//public class Leveler {
//	
//	public static void SaveMap(String mapName){
////		System.out.println("Enter File Name: ");
////		Scanner sc = new Scanner(System.in);
////		String mapName = sc.next();
////		sc.close();
//		String mapData = "";
//		for (int i = 0; i < GetTilesHigh(); i++){
//			for (int j = 0; j < GetTilesWide(); j++){
//				mapData += getTileID(GetTile(j, i));
//			}
//		}
//		
//		
//		try {
//			File file = new File(mapName);
//			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
//			bw.write(mapData);
//			bw.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public static int[][] LoadMap(String mapName){
//		int[][] newMap = new int[GetTilesHigh()][GetTilesWide()];
////		CreateMap();
//		try{
//			BufferedReader reader = new BufferedReader(new FileReader(mapName));
//			String data = reader.readLine();
//			reader.close();
//			for (int i  = 0; i < GetTilesHigh(); i++){
//				for (int j = 0; j < GetTilesWide(); j++){
////					System.out.print(data.substring(i * GetTilesWide() + j, i * GetTilesWide() + j + 1));
//					newMap[i][j] = Integer.parseInt(data.substring(i * GetTilesWide() + j, i * GetTilesWide() + j + 1));
////					SetTile(i, j, getTileType((data.substring(i * GetTilesHigh() + j, i * GetTilesHigh() + j + 1))));
//				}
//			}
//		} catch (Exception e){
//			e.printStackTrace();
//		}
//		return newMap;
//	}
//	
//	public static void DrawMap(int[][] newMap){
//		for (int i  = 0; i < GetTilesHigh(); i++){
//			for (int j = 0; j < GetTilesWide(); j++){
//				SetTile(i, j, getTileType(Integer.toString(newMap[i][j])));
//			}
//		}
//	}
//	
//	public static TileType getTileType(String ID){
//		TileType type = TileType.NULL;
//		switch(ID){
//		case "0":
//			type = TileType.Grass;
//			break;
//		case "1":
//			type = TileType.Dirt;
//			break;
//		case "2": 
//			type = TileType.Water;
//			break;
//		case "3": 
//			type = TileType.Start;
//			break;
//		case "4": 
//			type = TileType.Goal;
//			break;
//		case "5": 
//			type = TileType.NULL;
//			break;
//		}
//		
//		return type;
//	}
//	
//	public static String getTileID(Tile t){
//		String ID = "E";
//		switch (t.getType()){
//		case Grass:
//			ID = "0";
//			break;
//		case Dirt:
//			ID = "1";
//			break;
//		case Water:
//			ID = "2";
//			break;
//		case Start:
//			ID = "3";
//			break;
//		case Goal:
//			ID = "4";
//			break;
//		case NULL:
//			ID = "5";
//			break;
//		}		
//		return ID;
//	}
//
//}