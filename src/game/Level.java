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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
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
		this.world = world;
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
		float halfTile = GameScreen.TILE_SIZE / 2;
		float boxTileWidth = halfTile * GameScreen.WORLD_TO_BOX;
		while(it.hasNext()) {
			TiledLayer layer = it.next();
			if(layer.name.equals("collision")) {
				int[][] tiles = layer.tiles;
				for(int ty = 0; ty < tiles.length; ty++) {
					
					for(int tx = 0; tx < tiles[ty].length; tx++) {
						String type = tiledMap.getTileProperty(tiles[ty][tx], "type");
						if(type != null && type.equals("solid")) {
							BodyDef solidDef = new BodyDef();
							float x = tx * GameScreen.TILE_SIZE;
							// subtracting the y by the highest possible value, 
							// as the coordinates order needs to be reversed for OpenGL coords 
							float y = Math.abs(ty - (tiles.length-1)) * GameScreen.TILE_SIZE;
							Vector2 center = new Vector2((x + halfTile) * GameScreen.WORLD_TO_BOX, (y + halfTile) * GameScreen.WORLD_TO_BOX);
							solidDef.position.set(center);
							Body solidBody = world.createBody(solidDef);
							PolygonShape shape = new PolygonShape();
							shape.setAsBox(boxTileWidth, boxTileWidth);
							solidBody.createFixture(shape, 0f);
							shape.dispose();
						}
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
