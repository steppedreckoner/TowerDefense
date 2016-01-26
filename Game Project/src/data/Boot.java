package data;

import static helpers.Artist.BeginSession;

import java.util.Scanner;

import org.lwjgl.opengl.Display;

import helpers.Clock;
import helpers.StateManager;

public class Boot {
	
	public static Scanner SC;
	
	public Boot(){
		
		//Handle user input through console. Ensures the scanner is not prematurely closed.
		SC = new Scanner(System.in);
		//Call static method in Artist class to initialize OpenGL calls
		BeginSession();
		
		//Main Game Loop
		while(!Display.isCloseRequested()){
			Clock.Update();
			StateManager.Update();
			Display.update();
			Display.sync(60);
		}
		SC.close();
		Display.destroy();
	}
	
	public static void main(String[] args){
		new Boot();
	}

}
