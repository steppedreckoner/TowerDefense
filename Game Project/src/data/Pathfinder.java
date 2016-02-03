package data;

import static data.TileGrid.GetGoalTile;
import static data.TileGrid.GetStartTile;
import static data.TileGrid.GetTile;

import java.util.ArrayList;

import helpers.Clock;

public class Pathfinder {

	public ArrayList<Tile> pathList;

	private int currentCP, totalCP;

	public static final String GROUND = "ground", AIR = "air";

	public Pathfinder(boolean canFly) {
		if (canFly) {
			pathList = pathfindAir();
		} else {
			pathList = pathfindGround();
		}
		totalCP = pathList.size();
		currentCP = 1;
	}

	public int[] determineDirection() {
		int[] directions = new int[2];
		if (currentCP < totalCP && currentCP > 0) {
			directions[0] = pathList.get(currentCP).getXPlace() - pathList.get(currentCP - 1).getXPlace();
			directions[1] = pathList.get(currentCP).getYPlace() - pathList.get(currentCP - 1).getYPlace();
		} else if (currentCP >= totalCP) {
			directions[0] = -10;
			directions[1] = -10;
		}
		return directions;
	}

	public void checkCP(float x, float y) {
		Tile t = pathList.get(currentCP);
		if (x > t.getX() - (3 * Clock.GetMultiplier()) && x < t.getX() + (3 * Clock.GetMultiplier()) 
				&& y > t.getY() - (3 * Clock.GetMultiplier()) && y < t.getY() + (3 * Clock.GetMultiplier())) {
			currentCP++;
			x = t.getX();
			y = t.getY();
		}
	}

	public ArrayList<Tile> getPathList() {
		return pathList;
	}

	private ArrayList<Tile> pathfindGround() {
		ArrayList<Tile> pathList = new ArrayList<Tile>();
		ArrayList<TileDist> counterPathList = new ArrayList<TileDist>();
		Tile endTile = GetGoalTile();
		Tile startTile = GetStartTile();
		counterPathList.add(new TileDist(endTile, 0));
		boolean keepGoing = true;
		int index = 0;
		int distance;
		int totalDistance = -1; // Initialize to -1 to use later. Will check
								// that value has been changed.

		// Find the distance of tiles from the finish until the start tile is
		// reached
		while (keepGoing) {
			TileDist current = counterPathList.get(index);
			index++;
			distance = current.getDistance() + 1;
			// Tile[] neighbors = current.getTile().getNeighbors();
			Tile[] neighbors = new Tile[4];
			neighbors[0] = GetTile(current.getXPlace(), current.getYPlace() - 1);
			neighbors[1] = GetTile(current.getXPlace() + 1, current.getYPlace());
			neighbors[2] = GetTile(current.getXPlace(), current.getYPlace() + 1);
			neighbors[3] = GetTile(current.getXPlace() - 1, current.getYPlace());
			for (Tile t : neighbors) {
				if (t.canGround()) {
					boolean isShortest = true;
					for (TileDist d : counterPathList) {
						// Check if that tile is has already been added with
						// shorter distance
						if (t.getXPlace() == d.getXPlace() && t.getYPlace() == d.getYPlace()
								&& distance >= d.getDistance()) {
							isShortest = false;
						}
					}
					if (isShortest) {
						TileDist newTileDist = new TileDist(t, distance);
						counterPathList.add(newTileDist);
					}
				}
				// Check to see if startTile has been reached
				if (t.equals(startTile)) {
					totalDistance = distance;
					keepGoing = false;
				}
			}
		}

		// Find the tiles that make up a shortest path.
		if (totalDistance > 0) { // There exists a path between the start and
									// end
			pathList.add(startTile);
			TileDist current = new TileDist(startTile, totalDistance);
			for (int dist = totalDistance - 1; dist >= 0; dist--) {
				// TileDist[] neighbors = current.getNeighbors();
				TileDist[] neighbors = new TileDist[4];
				neighbors[0] = TileDist.GetTileDist(current.getXPlace(), current.getYPlace() - 1);
				neighbors[1] = TileDist.GetTileDist(current.getXPlace() + 1, current.getYPlace());
				neighbors[2] = TileDist.GetTileDist(current.getXPlace(), current.getYPlace() + 1);
				neighbors[3] = TileDist.GetTileDist(current.getXPlace() - 1, current.getYPlace());
				for (TileDist td : neighbors) {
					if (td != null) {
						if (td.getDistance() == dist) {
							current = td;
						}
					}
				}
				pathList.add(current.getTile());
			}
		}

		return pathList;
	}
	
	private ArrayList<Tile> pathfindAir() {
		ArrayList<Tile> pathList = new ArrayList<Tile>();
		ArrayList<TileDist> counterPathList = new ArrayList<TileDist>();
		Tile endTile = GetGoalTile();
		Tile startTile = GetStartTile();
		counterPathList.add(new TileDist(endTile, 0));
		boolean keepGoing = true;
		int index = 0;
		int distance;
		int totalDistance = -1; // Initialize to -1 to use later. Will check
								// that value has been changed.

		// Find the distance of tiles from the finish until the start tile is
		// reached
		while (keepGoing) {
			TileDist current = counterPathList.get(index);
			index++;
			distance = current.getDistance() + 1;
			// Tile[] neighbors = current.getTile().getNeighbors();
			Tile[] neighbors = new Tile[4];
			neighbors[0] = GetTile(current.getXPlace(), current.getYPlace() - 1);
			neighbors[1] = GetTile(current.getXPlace() + 1, current.getYPlace());
			neighbors[2] = GetTile(current.getXPlace(), current.getYPlace() + 1);
			neighbors[3] = GetTile(current.getXPlace() - 1, current.getYPlace());
			for (Tile t : neighbors) {
				if (t.canFly()) {
					boolean isShortest = true;
					for (TileDist d : counterPathList) {
						// Check if that tile is has already been added with
						// shorter distance
						if (t.getXPlace() == d.getXPlace() && t.getYPlace() == d.getYPlace()
								&& distance >= d.getDistance()) {
							isShortest = false;
						}
					}
					if (isShortest) {
						TileDist newTileDist = new TileDist(t, distance);
						counterPathList.add(newTileDist);
					}
				}
				// Check to see if startTile has been reached
				if (t.equals(startTile)) {
					totalDistance = distance;
					keepGoing = false;
				}
			}
		}

		// Find the tiles that make up a shortest path.
		if (totalDistance > 0) { // There exists a path between the start and
									// end
			pathList.add(startTile);
			TileDist current = new TileDist(startTile, totalDistance);
			for (int dist = totalDistance - 1; dist >= 0; dist--) {
				// TileDist[] neighbors = current.getNeighbors();
				TileDist[] neighbors = new TileDist[4];
				neighbors[0] = TileDist.GetTileDist(current.getXPlace(), current.getYPlace() - 1);
				neighbors[1] = TileDist.GetTileDist(current.getXPlace() + 1, current.getYPlace());
				neighbors[2] = TileDist.GetTileDist(current.getXPlace(), current.getYPlace() + 1);
				neighbors[3] = TileDist.GetTileDist(current.getXPlace() - 1, current.getYPlace());
				for (TileDist td : neighbors) {
					if (td != null) {
						if (td.getDistance() == dist) {
							current = td;
						}
					}
				}
				pathList.add(current.getTile());
			}
		}

		return pathList;
	}

}
