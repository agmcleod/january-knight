package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Player extends Entity {
	
	private Texture playerTexture;
	private Vector2 maxVelocity;
	private Vector2 velocity;
	private boolean moving = false;
	private boolean touchingOnFoot = false;
	private boolean touchingOnRight = false;
	private boolean touchingOnLeft = false;
	
	private int originalX;
	private int originalY;
	
	public Player(int x, int y) {
		super();
		playerTexture = new Texture(Gdx.files.internal("assets/hero.png"));
		originalX = x;
		originalY = y;
		maxVelocity = new Vector2(10f, 10f);
		velocity = new Vector2(0f, 0f);
		init(x, y, 128, 128, playerTexture, false);
		
		addFrame(0, 0, 128, 128);
	}
	
	public void dispose() {
		playerTexture.dispose();
	}
	
	public boolean isMoving() {
		return moving;
	}
	
	public boolean isTouchingOnFoot() {
		return touchingOnFoot;
	}
	
	public boolean isTouchingOnLeft() {
		return touchingOnLeft;
	}
	
	public boolean isTouchingOnRight() {
		return touchingOnRight;
	}
	
	public void jump() {
		if(touchingOnFoot) {
			
		}	
	}
	
	public void moveLeft() {
		if(!touchingOnLeft) {
			float a = 5f;
			velocity.x += 0.5 * a * Gdx.graphics.getDeltaTime();
			if(velocity.x < -maxVelocity.x){
				velocity.x = -maxVelocity.x;
			}
		}
	}
	
	public void moveRight() {
		if(!touchingOnRight) {
			float a = 5f;
			velocity.x += 0.5 * a * Gdx.graphics.getDeltaTime();
			if(velocity.x > maxVelocity.x){
				velocity.x = maxVelocity.x;
			}
		}
	}
	
	public void reset() {
		setX(originalX);
		setY(originalY);
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
		stop();
	}
	
	public void setTouchingOnFoot(boolean touchingOnFoot) {
		this.touchingOnFoot = touchingOnFoot;
	}
	
	public void setTouchingOnLeft(boolean touchingOnLeft) {
		this.touchingOnLeft = touchingOnLeft;
	}
	
	public void setTouchingOnRight(boolean touchingOnRight) {
		this.touchingOnRight = touchingOnRight;
	}
	
	public void stop() {
		this.velocity.x = 0;
		System.out.println("stop");
	}
	
	public void update() {
		 setX((int) (getX() + velocity.x));
	}
}
