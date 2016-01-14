package data;

import static helpers.Clock.Delta;

import java.util.concurrent.CopyOnWriteArrayList;

public class Wave {

	private float timeSinceLastSpawn, spawnTime;
	private EnemyType enemyType;
	private CopyOnWriteArrayList<Enemy> enemyList;
	private int level, enemiesPerWave, enemiesSpawned;
	private boolean waveComplete;
	private TileGrid grid;

	public Wave(TileGrid grid, EnemyType enemyType, int level, int enemiesPerWave, float spawnTime) {
		this.grid = grid;
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

	public void spawn(EnemyType type, int level) {
		enemyList.add(type.makeEnemy(level, grid.getStartTile(), grid));
		enemiesSpawned++;
	}

	public boolean isComplete() {
		return waveComplete;
	}
	
	public CopyOnWriteArrayList<Enemy> getEnemyList(){
		return enemyList;
	}
}
