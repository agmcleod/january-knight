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

public class Player extends Entity implements ContactListener {
	
	private Texture playerTexture;
	private PolygonShape collisionBox;
	private BodyDef bodyDef;
	private Body body;
	private final float maxVelocityX = 3f;
	private final float maxVelocityY = 3.5f;
	private boolean moving = false;
	private boolean touchingOnFoot = false;
	
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
		
		body.createFixture(fixtureDef);
		
		// foot sensor
		collisionBox.setAsBox(0.3f, 0.3f, new Vector2(0f,-2f), 0);
	    fixtureDef.isSensor = true;
	    Fixture footSensorFixture = body.createFixture(fixtureDef);
	    footSensorFixture.setUserData(this);
	    collisionBox.dispose();
	    world.setContactListener(this);
		
		addFrame(0, 0, 128, 128);
	}
	
	@Override
	public void beginContact(Contact contact) {
		Object fixtureUserDataA = contact.getFixtureA().getUserData();
		Object fixtureUserDataB = contact.getFixtureB().getUserData();
		if(fixtureUserDataA instanceof Player || fixtureUserDataB instanceof Player) {
			touchingOnFoot = true;
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
			touchingOnFoot = false;
		}
	}
	
	public boolean isMoving() {
		return moving;
	}
	
	public void jump() {
		if(!touchingOnFoot) {
			body.applyLinearImpulse(new Vector2(0f, maxVelocityY), body.getWorldCenter());
		}	
	}
	
	public void moveLeft() {
		body.applyLinearImpulse(new Vector2(-maxVelocityX, 0f), body.getWorldCenter());
	}
	
	public void moveRight() {
		body.applyLinearImpulse(new Vector2(maxVelocityX, 0f), body.getWorldCenter());
	}
	
	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		// TODO Auto-generated method stub
		
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

	public void stopMoving() {
		Vector2 vel = body.getLinearVelocity();
		body.setLinearVelocity(new Vector2(2f * (Math.abs(vel.x)/vel.x), 0f));
	}

	public void update() {
		setX((int) ((body.getPosition().x) * GameScreen.BOX_TO_WORLD));
		setY((int) ((body.getPosition().y) * GameScreen.BOX_TO_WORLD));
		
		if((getY() + getHeight()) <= 0) {
			reset();
		}
	}
}
