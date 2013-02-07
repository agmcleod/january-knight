package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class MoveableEntity extends Entity {
	private Vector2 maxVelocity;
	private Vector2 velocity;
	private boolean moving = false;
	private boolean touchingOnFoot = false;
	private boolean touchingOnRight = false;
	private boolean touchingOnLeft = false;
	private boolean jumpInitiated = false;
	private boolean falling;
	private float jumpSpeed = 450f;
	
	public MoveableEntity(int x, int y, Texture texture, boolean animated) {
		init(x, y, texture, animated);
	}

	public float getJumpSpeed() {
		return jumpSpeed;
	}

	public Vector2 getMaxVelocity() {
		return maxVelocity;
	}
	
	public Vector2 getVelocity() {
		return velocity;
	}
	
	public boolean isFalling() {
		return falling;
	}

	public boolean isJumpInitiated() {
		return jumpInitiated;
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

	public void setFalling(boolean falling) {
		this.falling = falling;
	}
	
	public void setJumpInitiated(boolean jumpInitiated) {
		this.jumpInitiated = jumpInitiated;
	}

	public void setJumpSpeed(float jumpSpeed) {
		this.jumpSpeed = jumpSpeed;
	}

	public void setMaxVelocity(Vector2 maxVelocity) {
		this.maxVelocity = maxVelocity;
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

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
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
		
		setY((int) (getY() + velocity.y));
	}
}
