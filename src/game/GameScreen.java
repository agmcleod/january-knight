package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameScreen implements Screen {
	
	private MyGame game;
	private Texture background;
	private Texture playerTexture;
	private TextureRegion trailingBackground;
	private TextureRegion lightBackgroundTile;
	private SpriteBatch batch;
	
	public GameScreen(MyGame game) {
		this.game = game;
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
	public void render(float arg0) {
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
		batch.draw(playerTexture, 0, 0, 64, 128);
		batch.end();
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
		playerTexture = new Texture(Gdx.files.internal("assets/player.png"));
	}

}
