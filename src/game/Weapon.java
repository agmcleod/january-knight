package game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class Weapon {
	private Rectangle box;
	private float angle = 0;
	private ShapeRenderer renderer;
	
	public Weapon(Rectangle box, float angle) {
		this.box = box;
		this.angle = angle;
		renderer = new ShapeRenderer();
	}
	
	public void debug(OrthographicCamera camera) {
		renderer.begin(ShapeType.Rectangle);
		renderer.setProjectionMatrix(camera.combined);
		renderer.setColor(Color.WHITE);
		renderer.rect(box.x, box.y, box.width, box.height);
		renderer.rotate(box.x, box.y, 0, angle);
		renderer.end();
	}
}
