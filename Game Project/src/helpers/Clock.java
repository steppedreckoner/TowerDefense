package helpers;

import org.lwjgl.Sys;

public class Clock {

	private static boolean isPaused = false;
	public static long lastFrame, totalTime;
	public static float d = 0, multiplier = 1;
	
	public static long getTime(){
		return Sys.getTime()*1000 / Sys.getTimerResolution();
	}
	
	public static float getDelta(){
		long currentTime = getTime();
		int delta = (int) (currentTime - lastFrame);
		lastFrame = getTime();
//		System.out.println(delta * 0.01f);
		if (delta * 0.001f > 0.03f){
			return 0.03f;
		}
		return delta * 0.001f;
	}
	
	public static float Delta(){
		if (isPaused){
			return 0;
		}
		else{
			return d * multiplier;
		}
	}
	
	public static float TotalTime(){
		return totalTime;
	}
	
	public static float Multiplier(){
		return multiplier;
	}
	
	public static void Update(){
		d = getDelta();
		totalTime += d;
	}
	
	public static void ChangeMultiplier(float change){
		if (multiplier + change < -1 || multiplier + change > 7){
			
		}
		else{
			multiplier += change;
		}
	}
	
	public static void Pause(){
		isPaused = !isPaused;
	}
	
	public static boolean IsPaused(){
		return isPaused;
	}
}
