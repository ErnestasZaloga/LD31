package com.us.ld31.utils.tiles;

import com.badlogic.gdx.math.MathUtils;
import com.us.ld31.LD31;

public class WorldGenerator {
	
	private static char grass = '.';
	private static char rock = 'O';
	private static char road = '=';
//	private static char grass = '.';
//	private static char grass = '.';
//	private static char grass = '.';
	
	public static WorldMap generateWorld(LD31 app, WorldMap worldMap) {
		float tileWH = worldMap.getTileSize();
		TileFactory tileFactory = new TileFactory(app, tileWH);
		char[][] map = new char[worldMap.getTilesX()][worldMap.getTilesY()];
		//Fill map with grass
		for(int x=0; x<map.length; x++) {
			for(int y=0; y<map[0].length; y++) {
				map[x][y] = grass;
			}
		}
		//Scatter some rocks around
		for(int i=0; i<40; i++) {
			int randX = MathUtils.random(0, map.length-1);
			int randY = MathUtils.random(0, map[0].length-1);
			map[randX][randY] = rock;
		}
		//TODO scatter some trees
		//...
		
		//Add roads
		int cityX = (int) Math.ceil(worldMap.getTilesX()/2);
		int cityY = (int) Math.ceil(worldMap.getTilesY()/2);
		drawRoad(map, cityX, cityY);
		
		//Convert chars to actual actors
		for(int x=0; x<map.length; x++) {
			for(int y=0; y<map[0].length; y++) {
				if(map[x][y] == grass) {
					worldMap.addActor(tileFactory.createGrassTile(x*tileWH, y*tileWH));
				} else if(map[x][y] == rock) {
					worldMap.addActor(tileFactory.createRockTile(x*tileWH, y*tileWH));
				} else if(map[x][y] == road) {
					worldMap.addActor(tileFactory.createRoadTile(x*tileWH, y*tileWH));
				}
			}
		}
		
		return worldMap;
	}
	
	private static void drawRoad(char[][] map, int cityX, int cityY) {
		map[cityX][cityY] = road;
		//left
		for(int i=cityX; i>=0; i--) {
			map[i][cityY-1] = '=';
			map[i][cityY] = '=';
			map[i][cityY+1] = '=';
		}
		//right
		for(int i=cityX; i<map.length; i++) {
			map[i][cityY-1] = '=';
			map[i][cityY] = '=';
			map[i][cityY+1] = '=';
		}
		//up
		for(int i=cityY; i<map[0].length; i++) {
			map[cityX-1][i] = '=';
			map[cityX][i] = '=';
			map[cityX+1][i] = '=';
		}
		//down
		for(int i=cityY; i>=0; i--) {
			map[cityX-1][i] = '=';
			map[cityX][i] = '=';
			map[cityX+1][i] = '=';
		}
		//center
		int radius = 8;
		for(int x=0; x<map.length; x++) {
			for(int y=0; y<map[0].length; y++) {
				int dX = cityX - x;
				int dY = cityY - y;
				if(Math.sqrt(dX*dX+dY*dY) <= radius) {
					map[x][y] = '=';
				}
			}
		}
	}
	
}
