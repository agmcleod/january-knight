package game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Weapon {
	private Rectangle box;
	private ShapeRenderer renderer;
	private Array<Position> positions;
	private int currentPosition = 0;
	
	public Weapon(Rectangle box, float angle) {
		this.box = box;
		renderer = new ShapeRenderer();
		positions = new Array<Position>();
		positions.add(new Position(0, 0, angle));
	}
	
	public void addPosition(int x, int y, float angle) {
		positions.add(new Position(x, y, angle));
	}
	
	public void debug(OrthographicCamera camera) {
		Position position = positions.get(currentPosition);
		
		renderer.setProjectionMatrix(camera.combined);
		
		renderer.begin(ShapeType.Rectangle);
		renderer.setColor(Color.WHITE);
		renderer.identity();
		renderer.translate(box.x + position.getX(), box.y + position.getY(), 0);
		renderer.rotate(0, 0, 1, position.getAngle());
		renderer.rect(0, 0, box.width, box.height);
		
		renderer.end();
	}
	
	public void setCurrentPosition(int pos) {
		this.currentPosition = pos;
	}
	
	public void update(int x, int y) {
		box.x = x;
		box.y = y;
	}
}
