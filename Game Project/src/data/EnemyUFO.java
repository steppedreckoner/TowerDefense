package data;

public class EnemyUFO extends Enemy {

	public EnemyUFO(int level, Tile startTile, TileGrid grid) {
		super(EnemyType.EnemyUFO, level, startTile, grid);
	}
	
	@Override
	public void die() {
		alive = false;
		Player.modifyCash(20);
	}

}
