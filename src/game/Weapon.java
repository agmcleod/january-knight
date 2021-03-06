package game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Weapon {
	private Rectangle box;
	private ShapeRenderer renderer;
	private Array<Position> positions;
	private int currentPosition = 0;
	private boolean hit = false;
	
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
		Position position = getCurrentPosition();
		
		renderer.setProjectionMatrix(camera.combined);
		
		renderer.begin(ShapeType.Rectangle);
		renderer.setColor(Color.WHITE);
		renderer.identity();
		renderer.translate(box.x, box.y, 0);
		renderer.rotate(0, 0, 1, position.getAngle());
		renderer.rect(0, 0, box.width, box.height);
		renderer.end();
	}
	
	public Rectangle getBox() {
		return this.box;
	}
	
	public Position getCurrentPosition() {
		return positions.get(currentPosition);
	}
	
	public boolean hasHit() {
		return hit;
	}
	
	public void setCurrentPosition(int pos) {
		this.currentPosition = pos;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
	}

	public void update(int x, int y) {
		box.x = x + getCurrentPosition().getX();
		box.y = y + getCurrentPosition().getY();
	}
}
