package data;

public class EnemySpacePlane extends Enemy {

	public EnemySpacePlane(int level, Tile startTile, TileGrid grid) {
		super(EnemyType.EnemySpacePlane, level, startTile, grid);
	}
	
	@Override
	public void die() {
		alive = false;
		Player.modifyCash(15);
	}

}