package data;

public class EnemyNuke extends Enemy{

	public EnemyNuke(int level, Tile startTile, TileGrid grid) {
		super(EnemyType.EnemyNuke, level, startTile, grid);
	}

	@Override
	protected void die() {
		alive = false;
		
	}

}
