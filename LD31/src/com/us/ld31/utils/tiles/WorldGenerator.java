package com.us.ld31.utils.tiles;

import com.badlogic.gdx.math.MathUtils;
import com.us.ld31.LD31;

public class WorldGenerator {
	
	private static char grass = '.';
	private static char rock = 'O';
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
		
		//Convert chars to actual actors
		for(int x=0; x<map.length; x++) {
			for(int y=0; y<map[0].length; y++) {
				if(map[x][y] == grass) {
					worldMap.addActor(tileFactory.createGrassTile(x*tileWH, y*tileWH));
				} else if(map[x][y] == rock) {
					worldMap.addActor(tileFactory.createRockTile(x*tileWH, y*tileWH));
				}
			}
		}
		
		return worldMap;
	}
	
}
