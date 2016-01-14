package data;

import java.util.ArrayList;

public class WaveManager {

	private float timeSinceLastWave, timeBetweenEnemies;
	private int waveNumber;
	private Wave currentWave;
	
	private boolean gameComplete = false;

	private ArrayList<EnemyType> enemyTypeList;
	private ArrayList<Integer> enemiesPerWaveList;
	private ArrayList<Float> timeBetweenEnemiesList;
	
	private EnemyType enemyType;

	public WaveManager() {
		// this.enemyType = enemyType;
		// this.enemiesPerWave = enemiesPerWave;
		// this.timeBetweenEnemies = timeBetweenEnemies;
		this.enemyType = EnemyType.EnemySpacePlane;
		this.timeSinceLastWave = 0;
		this.waveNumber = 0;

		this.enemyTypeList = new ArrayList<EnemyType>();
		this.enemiesPerWaveList = new ArrayList<Integer>();
		this.timeBetweenEnemiesList = new ArrayList<Float>();

		this.currentWave = null;

		for (int i = 1; i <= 4; i++) {
//			Enemy enemy = new Enemy(QuickLoad("UFO"), grid.getTile(5, 7), grid, TILE_SIZE, TILE_SIZE, 50, 50 + 25 * i);	//Makes a different enemy type for each wave
//			enemyTypeList.add(enemy);	
			enemiesPerWaveList.add(i + 1);
			timeBetweenEnemiesList.add((float) (3 - i * .5));
		}

		newWave(enemyType, enemiesPerWaveList.get(waveNumber),
				timeBetweenEnemiesList.get(waveNumber));
	}

	public void update() {
		if (!gameComplete) {
			if (!currentWave.isComplete()) {
				currentWave.update();
			} else if (waveNumber < enemiesPerWaveList.size()) {
				//Sets currentWave to to wave of specified type 
				newWave(enemyType, enemiesPerWaveList.get(waveNumber),
						timeBetweenEnemiesList.get(waveNumber));
			} else {
				System.out.println("Game Complete!");
				gameComplete = true;
			}
		}
	}

	private void newWave(EnemyType enemyType, int enemiesPerWave, float timeBetweenEnemies) {
		currentWave = new Wave(enemyType, 1, enemiesPerWave, timeBetweenEnemies);
		waveNumber++;
		System.out.println("Beginning Wave " + waveNumber);
	}
	
	public Wave getCurrentWave(){
		return currentWave;
	}
	
	public void setEnemyType(EnemyType type){
		this.enemyType = type;
	}
}
