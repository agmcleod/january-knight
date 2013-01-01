package game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Entity {
	private int x;
	private int y;
	private int width;
	private int height;
	private Texture textureImage;
	// used for storing various animations for when a sprite needs to be animated
	private Array<Animation> animations;
	// used for still images to be controlled for various states.
	private Array<TextureRegion> frames;
	private boolean animated;
	private float animationSpeed = 0.02f;
	private int focusedAnimation = 0;
	
	public Entity() {
		
	}
	
	public Entity(int x, int y, int width, int height, Texture textureImage, boolean animated) {
		init(x, y, width, height, textureImage, animated);
	}
	
	public void addAnimation(int[][] coords) {
		TextureRegion[] frames = new TextureRegion[coords.length];
		for(int i = 0; i < coords.length; i++) {
			frames[i] = new TextureRegion(textureImage, coords[i][0] * 32, coords[i][1] * 32, 32, 32);
		}
		this.animations.add(new Animation(this.animationSpeed, frames));
	}

	public void addFrame(int x, int y, int width, int height) {
		TextureRegion region = new TextureRegion(this.textureImage, x, y, width, height);
		this.frames.add(region);
	}

	public String collisionRectangleString() {
		Rectangle r = getCollisionRectangle();
		return " x : " + r.x + " y: " + r.y + " width: " + r.width + " height: " + r.height; 
	}


	public Array<Animation> getAnimations() {
		return animations;
	}
	
	public Rectangle getCollisionRectangle() {
		return new Rectangle(this.x, this.y, this.width, this.height);
	}

	public Array<TextureRegion> getFrames() {
		return frames;
	}

	public int getHeight() {
		return height;
	}

	public Texture getTextureImage() {
		return textureImage;
	}

	public int getWidth() {
		return width;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void init(int x, int y, int width, int height, Texture textureImage, boolean animated) {
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		this.setTextureImage(textureImage);
		this.setAnimated(animated);
		if(this.animated) {
			animations = new Array<Animation>();
		}
		else {
			frames = new Array<TextureRegion>();
		}
	}

	public boolean isAnimated() {
		return animated;
	}
	
	public void render(float stateTime, SpriteBatch batch) {
		TextureRegion currentFrame;
		if(this.animated) {
			Animation currentAnimation = this.animations.get(focusedAnimation);
			currentFrame = currentAnimation.getKeyFrame(stateTime, true); 
		}
		else {
			currentFrame = frames.get(focusedAnimation);
		}
		batch.draw(currentFrame, x, y);
	}

	public void setAnimated(boolean animated) {
		this.animated = animated;
	}

	public void setAnimations(Array<Animation> animations) {
		this.animations = animations;
	}

	public void setFrames(Array<TextureRegion> frames) {
		this.frames = frames;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setTextureImage(Texture textureImage) {
		this.textureImage = textureImage;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
}
