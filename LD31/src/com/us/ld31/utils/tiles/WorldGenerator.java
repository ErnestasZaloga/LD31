package com.us.ld31.utils.tiles;

import com.us.ld31.LD31;

public class WorldGenerator {
	
	public static WorldMap generateWorld(LD31 app, WorldMap worldMap) {
		float tileWH = worldMap.getTileSize();
		System.out.println(tileWH);
		TileFactory tileFactory = new TileFactory(app, tileWH);
		char[][] map = new char[worldMap.getTilesX()][worldMap.getTilesY()];
		//Fill map with grass
		for(int x=0; x<map.length; x++) {
			for(int y=0; y<map[0].length; y++) {
				map[x][y] = '.';
			}
		}
		
		//Convert chars to actual actors
		for(int x=0; x<map.length; x++) {
			for(int y=0; y<map[0].length; y++) {
				if(map[x][y] == '.') {
					worldMap.addActor(tileFactory.createGrassTile(x*tileWH, y*tileWH));
				}
			}
		}
		
		return worldMap;
	}
	
}
