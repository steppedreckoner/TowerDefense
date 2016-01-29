package data;

import static helpers.Artist.HEIGHT;
import static helpers.Artist.WIDTH;

import java.util.ArrayList;

public class WaveManager {

//	private float timeSinceLastWave, timeBetweenEnemies;
	private int waveNumber;
	private Wave currentWave;
	
	private boolean gameComplete;

	private ArrayList<EnemyType> enemyTypeList;
	private ArrayList<Integer> enemiesPerWaveList;
	private ArrayList<Float> timeBetweenEnemiesList;
	
	private EnemyType enemyType;

	public WaveManager() {
		// this.enemyType = enemyType;
		// this.enemiesPerWave = enemiesPerWave;
		// this.timeBetweenEnemies = timeBetweenEnemies;
		this.enemyType = EnemyType.EnemyUFO;
//		this.timeSinceLastWave = 0;
		this.waveNumber = 0;
		this.gameComplete = false;

		this.enemyTypeList = new ArrayList<EnemyType>();
		enemyTypeList.add(EnemyType.EnemyUFO);
		enemyTypeList.add(EnemyType.EnemySpacePlane);
		enemyTypeList.add(EnemyType.EnemyNuke);
		enemyTypeList.add(EnemyType.EnemyUFO);
		enemyTypeList.add(EnemyType.EnemyUFO);
		this.enemiesPerWaveList = new ArrayList<Integer>();
		this.timeBetweenEnemiesList = new ArrayList<Float>();

		this.currentWave = null;

		for (int i = 1; i <= 10; i++) {
//			Enemy enemy = new Enemy(QuickLoad("UFO"), grid.getTile(5, 7), grid, TILE_SIZE, TILE_SIZE, 50, 50 + 25 * i);	//Makes a different enemy type for each wave
//			enemyTypeList.add(enemy);	
			enemiesPerWaveList.add(i + 1);
			timeBetweenEnemiesList.add((float) (3 - i * .15));
		}
		enemiesPerWaveList.add(100);
		enemiesPerWaveList.add(1000);
		timeBetweenEnemiesList.add(1f);
		timeBetweenEnemiesList.add(.5f);

		newWave(enemyType, enemiesPerWaveList.get(waveNumber),
				timeBetweenEnemiesList.get(waveNumber), 1);
	}

	public void drawWaveNumber(){
		Game.GameFont.drawString(WIDTH * .9f, HEIGHT * .89f, "Wave: " + Integer.toString(waveNumber));
	}

	public void update() {
		if (!gameComplete) {
			if (!currentWave.isComplete()) {
				currentWave.update();
			} else if (waveNumber < enemiesPerWaveList.size()) {
				//Sets currentWave to to wave of specified type 
//				newWave(EnemyType.EnemyUFO, enemiesPerWaveList.get(waveNumber),	//Gets random enemy type
//						timeBetweenEnemiesList.get(waveNumber), 2);
				newWave(enemiesPerWaveList.get(waveNumber), timeBetweenEnemiesList.get(waveNumber));
			} else {
				System.out.println("Game Complete!");
				gameComplete = true;
			}
		}
	}

	private void newWave(EnemyType enemyType, int enemiesPerWave, float timeBetweenEnemies, int level) {
		currentWave = new Wave(enemyType, level, enemiesPerWave, timeBetweenEnemies);
		waveNumber++;
		System.out.println("Beginning Wave " + waveNumber + ": " + enemyType + " - Level " + level);
	}
	
	private void newWave(int enemiesPerWave, float timeBetweenEnemies){
//		int level = 1;
//		if (waveNumber + 1 > 2){	//Add one because waveNumber isn't updated until after this evaluation
//			level = 2;
//		}
		int level = Math.floorDiv(waveNumber, 2) + 1;
		newWave(EnemyType.EnemyUFO, enemiesPerWave, timeBetweenEnemies, level);
	}
	
	public Wave getCurrentWave(){
		return currentWave;
	}
	
	public void setEnemyType(EnemyType type){
		this.enemyType = type;
	}
}
