package game;

import java.util.Iterator;

import game.Entity.states;

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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameScreen implements Screen, InputProcessor {
	
	private MyGame game;
	private Texture background;
	private TextureRegion trailingBackground;
	private TextureRegion lightBackgroundTile;
	private SpriteBatch batch;
	private Player player;
	private Texture entityTexture;
	
	// camera properties
	private OrthographicCamera camera;
    private Vector2 offset;
    private Rectangle glViewport;
	
	private Array<Level> levels;
	private int currentLevel = 0;
	static final float TILE_SIZE = 32f;
	private WorldCollision worldCollision;
	static final float gravity = 15f;
	
	public GameScreen(MyGame game) {
		this.game = game;
	}
	
	public void checkIfEnemiesGotHurt() {
		if(player.getState() == states.ATTACKING) {
			Weapon sword = player.getWeapon();
			Array<Integer> toRemove = worldCollision.weaponTouchesEntities(sword, sword.getCurrentPosition().getAngle(), getCurrentLevel());
			getCurrentLevel().removeEntities(toRemove);
			if(getCurrentLevel().getEnemies().size == 0) {
				game.getEndScreen().setPlayerWon(true);
				gotoEndScreen();
			}
		}
	}
	
	public void checkIfPlayerGotHurt() {
		Array<MoveableEntity> enemies = getCurrentLevel().getEnemies();
		Iterator<MoveableEntity> it = enemies.iterator();
		boolean playerCollided = false;
		while(it.hasNext()) {
			MoveableEntity e = it.next();
			if(player.getWorldCollisionRectangle().overlaps(e.getWorldCollisionRectangle())) {
				if(!player.isColliding()) {
					if(player.takeDamage()) {
						game.getEndScreen().setPlayerWon(false);
						gotoEndScreen();
					}
				}
				
				playerCollided = true;
				
			}
		}
		
		player.setColliding(playerCollided);
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		background.dispose();
		entityTexture.dispose();
	}

	public Level getCurrentLevel() {
		return levels.get(currentLevel);
	}
	
	public Texture getEntityTexture() {
		return this.entityTexture;
	}
	
	public Vector2 getOffset() {
		return this.offset;
	}
	
	public void gotoEndScreen() {
		this.game.setScreen(game.getEndScreen());
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}

	public void initBackground() {
		background = new Texture(Gdx.files.internal("assets/bg.jpg"));
		float widthToUse = (float) (Gdx.graphics.getWidth() - 512) / 512f;
		trailingBackground = new TextureRegion(background, 0f, 0f, widthToUse, 1f);
		lightBackgroundTile = new TextureRegion(background, 0f, 480f, 32, 32);
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
	
	public void loadLevels() {
		levels.add(new Level("levelone.tmx", this));
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
	
	public void processInput() {
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
			player.stop();
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.X)) {
			player.attack();
		}
	}
	
	@Override
	public void render(float delta) {
		update();
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		// background is not big enough for full screen, so tile it across until it's filled
		batch.draw(background, offset.x, Gdx.graphics.getHeight() - 512, 512, 512);
		batch.draw(trailingBackground, offset.x + 512, Gdx.graphics.getHeight() - 512);
		int heightToCover = Gdx.graphics.getHeight() - 512;
		int widthToCover = Gdx.graphics.getWidth();
		for(int w = 0; w < widthToCover; w += 32) {
			for(int h = 0; h < heightToCover; h += 32) {
				batch.draw(lightBackgroundTile, w + offset.x, h, 32, 32);
			}
		}
		
		getCurrentLevel().renderEntities(batch, camera);
		player.render(batch);
		batch.end();
		levels.get(currentLevel).render(camera);
		//player.debug(camera);
		//getCurrentLevel().debug(camera);
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
		levels = new Array<Level>();
		initBackground();
		
		glViewport = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		
		// setup entities
		entityTexture = new Texture(Gdx.files.internal("assets/hero.png"));
		player = new Player(0, 300, entityTexture);
		
		loadLevels();
		
		offset = new Vector2(0, 0);
		worldCollision = new WorldCollision(player);
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
		worldCollision.checkIfEntityIsOnGround(getCurrentLevel(), player);
		worldCollision.checkIfPlayerTouchesBySideAndCantMove(getCurrentLevel());
		
		checkIfEnemiesGotHurt();
		processInput();
		
		player.update();
		// updates the enemies
		getCurrentLevel().update(worldCollision);
		
		checkIfPlayerGotHurt();
		
		if(player.reset()) {
			camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
		}
		updateOffset();
		
		if(player.getX() >= Gdx.graphics.getWidth() / 2) {
			camera.position.set(player.getX(), camera.position.y, 0);
		}
		else {
			camera.position.set(Gdx.graphics.getWidth() / 2, camera.position.y, 0);
		}
		
		camera.update();
		GL10 gl = Gdx.app.getGraphics().getGL10();
		camera.apply(gl);
	}
	
	public void updateOffset() {
		offset.x = player.getX() - (Gdx.graphics.getWidth() / 2);
		if(offset.x < 0) {
			offset.x = 0;
		}
	}
}
