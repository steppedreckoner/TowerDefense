package data;

import static helpers.Clock.Delta;

import java.util.concurrent.CopyOnWriteArrayList;

public class Wave {

	private float timeSinceLastSpawn, spawnTime;
	private EnemyType enemyType;
	private CopyOnWriteArrayList<Enemy> enemyList;
	private int level, enemiesPerWave, enemiesSpawned;
	private boolean waveComplete;

	public Wave(EnemyType enemyType, int level, int enemiesPerWave, float spawnTime) {
		TileDist.ResetDistMap();	//Patch to keep travel information from previous waves from altering new wave's path
		this.enemyType = enemyType;
		this.level = level;
		this.enemiesPerWave = enemiesPerWave;
		this.enemiesSpawned = 0;
		this.spawnTime = spawnTime;
		this.waveComplete = false;
		this.timeSinceLastSpawn = 0;
		this.enemyList = new CopyOnWriteArrayList<Enemy>();

		spawn(enemyType, level);
	}

	public void update() {
		boolean allEnemiesDead = true;
		if (enemiesSpawned < enemiesPerWave) {
			allEnemiesDead = false;
			timeSinceLastSpawn += Delta();
			if (timeSinceLastSpawn > spawnTime) {
				spawn(enemyType, level);
				timeSinceLastSpawn = 0;
			}
		}
		for (Enemy e : enemyList) {
			if (e.isAlive()) {
				allEnemiesDead = false;
				e.update();
				e.draw();
			}
			else{
				enemyList.remove(e);
			}
		}
		if (allEnemiesDead){
			waveComplete = true;
		}
	}
	
	//Might want to change this so that start tile can be set by spawn method
	public void spawn(EnemyType type, int level) {
		enemyList.add(type.makeEnemy(level));
		enemiesSpawned++;
	}

	public boolean isComplete() {
		return waveComplete;
	}
	
	public CopyOnWriteArrayList<Enemy> getEnemyList(){
		return enemyList;
	}
}
