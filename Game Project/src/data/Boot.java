package data;

import static helpers.Artist.BeginSession;

import org.lwjgl.opengl.Display;

import helpers.Clock;
import helpers.StateManager;

public class Boot {
	
	public Boot(){
		
		//Call static method in Artist class to initialize OpenGL calls
		BeginSession();
		
		//Main Game Loop
		while(!Display.isCloseRequested()){
			Clock.Update();
			StateManager.Update();
			Display.update();
			Display.sync(60);
		}
		Display.destroy();
	}
	
	public static void main(String[] args){
		new Boot();
	}

}
