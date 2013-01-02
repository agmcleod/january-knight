package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {
	
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
	
	public GameScreen(MyGame game) {
		this.game = game;
		levels = new Array<Level>();
	}

	@Override
	public void dispose() {
		batch.dispose();
		background.dispose();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		stateTime += Gdx.graphics.getDeltaTime();
		
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
	public void show() {
		batch = new SpriteBatch();
		background = new Texture(Gdx.files.internal("assets/bg.jpg"));
		float widthToUse = (float) (Gdx.graphics.getWidth() - 512) / 512f;
		trailingBackground = new TextureRegion(background, 0f, 0f, widthToUse, 1f);
		lightBackgroundTile = new TextureRegion(background, 0f, 480f, 32, 32);
		player = new Player(0, 0);
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		levels.add(new Level("levelone.tmx"));
	}

}
