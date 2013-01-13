package game;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLayer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Level {
	private TiledMap tiledMap;
	private TileAtlas tileAtlas;
	private TileMapRenderer tileMapRenderer;
	private World world;
	private int[] layerIndexes;
	private BodyDef collisionDef;
	
	public Level(String filename, World world) {
		tiledMap = TiledLoader.createMap(Gdx.files.internal("assets/" + filename));
		tileAtlas = new TileAtlas(tiledMap, Gdx.files.internal("assets"));
		tileMapRenderer = new TileMapRenderer(tiledMap, tileAtlas, 8, 8);
		initGround();
	}
	
	public void dispose() {
		tileMapRenderer.dispose();
		tileAtlas.dispose();
	}
	
	public void initGround() {
		ArrayList<TiledLayer> layers = tiledMap.layers;
		Iterator<TiledLayer> it = layers.iterator();
		int layerIndex = 0;
		Array<Integer> tempIndex = new Array<Integer>();
		while(it.hasNext()) {
			TiledLayer layer = it.next();
			if(layer.name.equals("collision")) {
				int[][] tiles = layer.tiles;
				for(int ty = 0; ty < tiles.length; ty++) {
					for(int tx = 0; tx < tiles.length; tx++) {
						System.out.println(tiledMap.getTileProperty(tiles[ty][tx], "type"));
					}
				}
			}
			else {
				tempIndex.add(layerIndex);
			}
			layerIndex++;
		}
		layerIndexes = new int[tempIndex.size];
		for(int i = 0; i < tempIndex.size; i++) {
			layerIndexes[i] = tempIndex.get(i);
		}
	}
	
	public void render(OrthographicCamera camera) {
		tileMapRenderer.render(camera, layerIndexes);
	}
}
