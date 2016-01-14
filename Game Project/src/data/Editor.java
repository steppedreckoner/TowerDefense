package data;

import static helpers.Artist.HEIGHT;
import static helpers.Artist.TILE_SIZE;
import static helpers.Leveler.LoadMap;
import static helpers.Leveler.SaveMap;
import static data.TileGrid.*;

import java.util.Scanner;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Editor {

	private int index;
	private TileType[] types;

	public Editor() {
		CreateMap();
		this.index = 0;		
		this.types = new TileType[3];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
	}

	public void Update() {
		TileGrid.Draw();
		
		// Handle mouse input
		if (Mouse.isButtonDown(0)) {
			setTile();
		}
		// Handle keyboard input
		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
				MoveIndex();
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_S && Keyboard.getEventKeyState()) {
				System.out.println("Enter File Name: ");
				Scanner sc = new Scanner(System.in);
				String mapName = sc.next();
				sc.close();
				SaveMap(mapName);
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_L && Keyboard.getEventKeyState()) {				
				LoadMap("mapTest");
			}
		}
	}

	private void setTile() {
		TileGrid.SetTile((int) Math.floor(Mouse.getX() / TILE_SIZE),
				(int) Math.floor((HEIGHT - Mouse.getY() - 1) / TILE_SIZE), types[index]);

	}
	
	//Changes selected TileType
	private void MoveIndex(){
		index++;
		if (index > types.length - 1){
			index = 0;
		}
	}
}
