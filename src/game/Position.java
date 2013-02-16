package game;

import com.badlogic.gdx.math.Vector2;

public class Position {
	private int x;
	private int y;
	private float angle;
	
	
	public Position(int x, int y, float angle) {
		this.angle = angle;
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public float getAngle() {
		return angle;
	}
}
