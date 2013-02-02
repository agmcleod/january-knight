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
	private boolean jumpInitiated = false;
	private boolean falling;
	private final float jumpSpeed = 450f;

	private int originalX;
	private int originalY;

	public Player(int x, int y) {
		super();
		playerTexture = new Texture(Gdx.files.internal("assets/hero.png"));
		originalX = x;
		originalY = y;
		maxVelocity = new Vector2(10f, 7.5f);
		velocity = new Vector2(0f, 0f);
		init(x, y, 44, 128, playerTexture, false);

		addFrame(44, 0, 39, 128);
	}

	public void dispose() {
		playerTexture.dispose();
	}
	
	public boolean isFalling() {
		return falling;
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
		if (touchingOnFoot) {
			velocity.y += jumpSpeed * Gdx.graphics.getDeltaTime();
			jumpInitiated = true;
		}
	}

	public void moveLeft() {
		if (!touchingOnLeft) {
			velocity.x -= maxVelocity.x * Gdx.graphics.getDeltaTime();
			if (velocity.x < -maxVelocity.x) {
				velocity.x = -maxVelocity.x;
			}
		}
	}

	public void moveRight() {
		if (!touchingOnRight) {
			velocity.x += maxVelocity.x * Gdx.graphics.getDeltaTime();
			if (velocity.x > maxVelocity.x) {
				velocity.x = maxVelocity.x;
			}
		}
	}

	public boolean reset() {
		if(getTopY() < 0) {
			setX(originalX);
			setY(originalY);
			return true;
		}
		else {
			return false;
		}
	}
	
	public void setFalling(boolean falling) {
		this.falling = falling;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public void setTouchingOnFoot(boolean touchingOnFoot) {
		this.touchingOnFoot = touchingOnFoot;
	}

	public void setTouchingOnLeft(boolean touchingOnLeft) {
		this.touchingOnLeft = touchingOnLeft;
		if(this.touchingOnLeft) {
			this.velocity.x = 0;
		}
	}

	public void setTouchingOnRight(boolean touchingOnRight) {
		this.touchingOnRight = touchingOnRight;
		if(this.touchingOnRight) {
			this.velocity.x = 0;
		}
	}

	public void stop() {
		this.velocity.x = 0;
	}

	public void update() {
		setX((int) (getX() + velocity.x));
		if(isTouchingOnFoot() && !jumpInitiated) {
			velocity.y = 0;
			this.falling = false;
		}
		else {
			velocity.y -= GameScreen.gravity * Gdx.graphics.getDeltaTime();
			// set falling
			if(velocity.y < 0) {
				this.falling = true;
			}
			else if(!isTouchingOnFoot()) {
				this.falling = false;
			}
			if(velocity.y > maxVelocity.y) {
				velocity.y = maxVelocity.y;
			}
			if(jumpInitiated) {
				jumpInitiated = false;
			}
		}
		if(velocity.y > 0)
			System.out.println(velocity.y);
		setY((int) (getY() + velocity.y));
	}
}
