package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class GameScreen implements Screen, InputProcessor {
	
	private MyGame game;
	private Texture background;
	private TextureRegion trailingBackground;
	private TextureRegion lightBackgroundTile;
	private SpriteBatch batch;
	private Player player;
	private float stateTime = 0;
	private OrthographicCamera camera;
	private Array<Level> levels;
	private int currentLevel = 0;
	private World world;
	static final float WORLD_TO_BOX = 0.01f;
	static final float BOX_TO_WORLD = 100f;
	static final float TILE_SIZE = 32f;
	private Box2DDebugRenderer debugRenderer;
	
	public GameScreen(MyGame game) {
		this.game = game;
		levels = new Array<Level>();
	}

	@Override
	public void dispose() {
		batch.dispose();
		background.dispose();
		player.dispose();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean keyDown(int keyCode) {
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void initBackground() {
		background = new Texture(Gdx.files.internal("assets/bg.jpg"));
		float widthToUse = (float) (Gdx.graphics.getWidth() - 512) / 512f;
		trailingBackground = new TextureRegion(background, 0f, 0f, widthToUse, 1f);
		lightBackgroundTile = new TextureRegion(background, 0f, 480f, 32, 32);
	}
	
	public void loadLevels() {
		levels.add(new Level("levelone.tmx", world));
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void render(float delta) {
		update();
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(background, 0, Gdx.graphics.getHeight() - 512, 512, 512);
		batch.draw(trailingBackground, 512, Gdx.graphics.getHeight() - 512);
		int heightToCover = Gdx.graphics.getHeight() - 512;
		int widthToCover = Gdx.graphics.getWidth();
		for(int w = 0; w < widthToCover; w += 32) {
			for(int h = 0; h < heightToCover; h += 32) {
				batch.draw(lightBackgroundTile, w, h, 32, 32);
			}
		}
		player.render(stateTime, batch);
		batch.end();
		levels.get(currentLevel).render(camera);
		
		// physics updates
		
		Matrix4 cameraCopy = camera.combined.cpy();
		debugRenderer.render(world, cameraCopy.scl(BOX_TO_WORLD));
		
		world.step(1/60f, 6, 2);
		
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		batch = new SpriteBatch();
		initBackground();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false);

		
		world = new World(new Vector2(0, -10), true);
		debugRenderer = new Box2DDebugRenderer();
		loadLevels();
		player = new Player(0, 300, world);
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	public void update() {
		stateTime += Gdx.graphics.getDeltaTime();
		camera.update();
		GL10 gl = Gdx.app.getGraphics().getGL10();
		camera.apply(gl);
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) 
				|| Gdx.input.isKeyPressed(Input.Keys.RIGHT)
				|| Gdx.input.isKeyPressed(Input.Keys.UP)) {
			if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				player.moveLeft();
				player.setMoving(true);
			}
			else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				player.moveRight();
				player.setMoving(true);
			}
			
			if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
				player.jump();
			}
		}
		else if(player.isMoving()) {
			player.setMoving(false);
			player.stopMoving();
		}
		
		player.update();
	}
}
