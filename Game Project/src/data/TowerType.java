package data;

import static helpers.Artist.QuickLoad;

import org.newdawn.slick.opengl.Texture;

public enum TowerType {
	
	CannonRed(new Texture[]{QuickLoad("cannonbase"), QuickLoad("cannongun")}, 5, 31) {
		
		private final float[] LevelListFireRate = {100f, .4f, .3f, .2f, .15f, .1f};	//list[i] is fire rate at level i.
		private final int[] LevelListRange = {0, 150, 175, 200, 225, 250};	//Same as above
		private final int[] LevelListUpgradeCost = {-50, -20, -30, -50, -75, -100};	//Upgrade cost at list[i] is cost to go from level i to i+1. First value is build cost.
		private final int[] LevelListUpgradeExp = {0, 1, 2, 3, 4, 500};	//Same arrangement as cost
		
		@Override
		public Tower makeTower(Tile startTile) {
			return new TowerCannonRed(startTile);
		}

		@Override
		public float[] getLevelListFireRate() {return LevelListFireRate;}

		@Override
		public int[] getLevelListRange() {return LevelListRange;}

		@Override
		public int[] getLevelListUpgradeCost() {return LevelListUpgradeCost;}

		@Override
		public int[] getLevelListUpgradeExp() {return LevelListUpgradeExp;}
		
	},
	CannonBlue(new Texture[]{QuickLoad("cannonbaseblue"), QuickLoad("cannongunblue")}, 5, 31) {
		
		private final float[] LevelListFireRate = {100f, 3f, 2.5f, 2f, 1.75f, 1.5f};	//list[i] is fire rate at level i.
		private final int[] LevelListRange = {0, 200, 225, 250, 300, 350};	//Same as above
		private final int[] LevelListUpgradeCost = {-40, -20, -30, -50, -75, -100};	//Upgrade cost at list[i] is cost to go from level i to i+1.
		private final int[] LevelListUpgradeExp = {0, 1, 2, 3, 4, 500};	//Same arrangement as cost
		
		@Override
		public Tower makeTower(Tile startTile) {
			return new TowerCannonBlue(startTile);
		}
		
		@Override
		public float[] getLevelListFireRate() {return LevelListFireRate;}

		@Override
		public int[] getLevelListRange() {return LevelListRange;}

		@Override
		public int[] getLevelListUpgradeCost() {return LevelListUpgradeCost;}

		@Override
		public int[] getLevelListUpgradeExp() {return LevelListUpgradeExp;}
		
	},
	IceTower(new Texture[]{QuickLoad("icetowerbase2"), QuickLoad("icetowergun")}, 5, 20) {
		
		private final float[] LevelListFireRate = {100f, 2f, 1.75f, 1.5f, 1.25f, 1f};	//list[i] is fire rate at level i.
		private final int[] LevelListRange = {0, 250, 275, 300, 325, 350};	//Same as above
		private final int[] LevelListUpgradeCost = {-100, -20, -30, -50, -75, -100};	//Upgrade cost at list[i] is cost to go from level i to i+1.
		private final int[] LevelListUpgradeExp = {0, 1, 2, 3, 4, 500};	//Same arrangement as cost
		
		@Override
		public Tower makeTower(Tile startTile) {
			return new TowerIce(startTile);
		}
		
		@Override
		public float[] getLevelListFireRate() {return LevelListFireRate;}

		@Override
		public int[] getLevelListRange() {return LevelListRange;}

		@Override
		public int[] getLevelListUpgradeCost() {return LevelListUpgradeCost;}

		@Override
		public int[] getLevelListUpgradeExp() {return LevelListUpgradeExp;}
		
	},
	RocketTower(new Texture[]{QuickLoad("rockettowerbase"), QuickLoad("rockettowerbarrel")}, 5, 28) {
		
		private final float[] LevelListFireRate = {100f, 2.6f, 2.3f, 2f, 1.7f, 1.4f};	//list[i] is fire rate at level i.
		private final int[] LevelListRange = {0, 400, 500, 600, 650, 700};	//Same as above
		private final int[] LevelListUpgradeCost = {-150, -20, -30, -50, -75, -100};	//Upgrade cost at list[i] is cost to go from level i to i+1.
		private final int[] LevelListUpgradeExp = {0, 1, 2, 3, 4, 500};	//Same arrangement as cost
		
		@Override
		public Tower makeTower(Tile startTile) {
			return new TowerRocketLauncher(startTile);
		}
		
		@Override
		public float[] getLevelListFireRate() {return LevelListFireRate;}

		@Override
		public int[] getLevelListRange() {return LevelListRange;}

		@Override
		public int[] getLevelListUpgradeCost() {return LevelListUpgradeCost;}

		@Override
		public int[] getLevelListUpgradeExp() {return LevelListUpgradeExp;}
		
	};
	
	Texture[] textures;
	int barrelLength, width, height;
	int maxLevel;
	
	TowerType(Texture[] textures, int maxLevel, int barrelLength){
		this.textures = textures;
		this.maxLevel = maxLevel;
		this.barrelLength = barrelLength;
		this.width = textures[0].getImageWidth();
		this.height = textures[0].getImageHeight();
	}
	
	public abstract Tower makeTower(Tile startTile);
	
	public abstract float[] getLevelListFireRate();
	public abstract int[] getLevelListRange();
	public abstract int[] getLevelListUpgradeCost();
	public abstract int[] getLevelListUpgradeExp();
	
}
