package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.WorldManifold;

public class Player extends Entity implements ContactListener {
	
	private Texture playerTexture;
	private PolygonShape collisionBox;
	private BodyDef bodyDef;
	private Body body;
	private final float maxVelocityX = 2.6f;
	private final float maxVelocityY = 20.5f;
	private boolean moving = false;
	private boolean touchingOnFoot = false;
	private boolean touchingOnRight = false;
	private boolean touchingOnLeft = false;
	
	private int originalX;
	private int originalY;
	
	public Player(int x, int y, World world) {
		super();
		playerTexture = new Texture(Gdx.files.internal("assets/hero.png"));
		originalX = x;
		originalY = y;
		init(x, y, 128, 128, playerTexture, false, world);
		
		// main body
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x * GameScreen.WORLD_TO_BOX, y * GameScreen.WORLD_TO_BOX);
		body = getWorld().createBody(bodyDef);
		collisionBox = new PolygonShape();
		collisionBox.setAsBox(32 * GameScreen.WORLD_TO_BOX, 64 * GameScreen.WORLD_TO_BOX, new Vector2(64 * GameScreen.WORLD_TO_BOX, 64 * GameScreen.WORLD_TO_BOX), 0.0f);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = collisionBox;
		fixtureDef.density = 10f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0f;
		Fixture f = body.createFixture(fixtureDef);
		f.setUserData(this);
		collisionBox.dispose();
	    world.setContactListener(this);
		
		addFrame(0, 0, 128, 128);
	}
	
	@Override
	public void beginContact(Contact contact) {
		Object fixtureUserDataA = contact.getFixtureA().getUserData();
		Object fixtureUserDataB = contact.getFixtureB().getUserData();
		if(fixtureUserDataA instanceof Player || fixtureUserDataB instanceof Player) {
			WorldManifold worldManifold = contact.getWorldManifold();
			Vector2 normal = worldManifold.getNormal();
			if(normal.x == 0.0f && normal.y == 1.0f) {
				touchingOnFoot = true;
			}
			else if(normal.x == -1.0f) {
				touchingOnRight = true;
			}
			else if(normal.x == 1.0f) {
				touchingOnLeft = true;
			}
			System.out.println("begin: "+ normal.x + "," + normal.y);
		}
	}
	
	public void dispose() {
		playerTexture.dispose();
	}
	
	@Override
	public void endContact(Contact contact) {
		Object fixtureUserDataA = contact.getFixtureA().getUserData();
		Object fixtureUserDataB = contact.getFixtureB().getUserData();
		if(fixtureUserDataA instanceof Player || fixtureUserDataB instanceof Player) {
			//WorldManifold worldManifold = contact.getWorldManifold();
			//Vector2 normal = worldManifold.getNormal();
			touchingOnFoot = false;
			touchingOnRight = false;
			touchingOnLeft = false;
			//System.out.println("end: " + normal.x + "," + normal.y);
		}
	}
	
	public boolean isMoving() {
		return moving;
	}
	
	public void jump() {
		if(touchingOnFoot) {
			body.applyLinearImpulse(new Vector2(0f, maxVelocityY), body.getWorldCenter());
		}	
	}
	
	public void moveLeft() {
		if(touchingOnLeft) {
			stopMoving(0);
			stayHere();
		}
		else {
			body.applyLinearImpulse(new Vector2(-maxVelocityX, 0f), body.getWorldCenter());
		}
	}
	
	public void moveRight() {
		if(touchingOnRight) {
			stopMoving(0);
			stayHere();
			System.out.println("touching on right");
		}
		else {
			body.applyLinearImpulse(new Vector2(maxVelocityX, 0f), body.getWorldCenter());
		}
	}
	
	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		
	}
	
	public void reset() {
		setX(originalX);
		setY(originalY);
		body.setTransform(new Vector2(originalX * GameScreen.WORLD_TO_BOX, originalY * GameScreen.WORLD_TO_BOX), 0f);
		body.setLinearVelocity(new Vector2(0, 0));
		body.setAngularVelocity(0);
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	
	public void stayHere() {
		body.setTransform(body.getPosition(), 0f);
	}

	public void stopMoving() {
		Vector2 vel = body.getLinearVelocity();
		// the Math.abs here will always ensure that the X velocity maintains direction
		body.setLinearVelocity(new Vector2(2f * (Math.abs(vel.x)/vel.x), 0f));
	}
	
	public void stopMoving(float amount) {
		Vector2 vel = body.getLinearVelocity();
		// the Math.abs here will always ensure that the X velocity maintains direction
		body.setLinearVelocity(new Vector2(amount * (Math.abs(vel.x)/vel.x), 0f));
	}

	public void update() {
		setX((int) ((body.getPosition().x) * GameScreen.BOX_TO_WORLD));
		setY((int) ((body.getPosition().y) * GameScreen.BOX_TO_WORLD));
		stayHere();
		
		if((getY() + getHeight()) <= 0) {
			reset();
		}
	}
}
