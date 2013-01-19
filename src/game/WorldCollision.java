package game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;

public class WorldCollision implements ContactListener {
	private World world;
	private Player player;
	public WorldCollision(World world, Player player) {
		this.world = world;
		this.player = player;
		world.setContactListener(this);
	}

	@Override
	public void beginContact(Contact contact) {
		Object fixtureUserDataA = contact.getFixtureA().getUserData();
		Object fixtureUserDataB = contact.getFixtureB().getUserData();
		if(fixtureUserDataA instanceof Player || fixtureUserDataB instanceof Player) {
			WorldManifold worldManifold = contact.getWorldManifold();
			Vector2 normal = worldManifold.getNormal();
			if(normal.x == 0.0f && normal.y == 1.0f) {
				player.setTouchingOnFoot(true);
			}
			else if(normal.x == -1.0f) {
				player.setTouchingOnRight(true);
			}
			else if(normal.x == 1.0f) {
				player.setTouchingOnLeft(true);
			}
			System.out.println("begin: "+ normal.x + "," + normal.y);
		}

	}

	@Override
	public void endContact(Contact contact) {
		Object fixtureUserDataA = contact.getFixtureA().getUserData();
		Object fixtureUserDataB = contact.getFixtureB().getUserData();
		if(fixtureUserDataA instanceof Player || fixtureUserDataB instanceof Player) {
			player.setTouchingOnFoot(false);
			player.setTouchingOnLeft(false);
			player.setTouchingOnRight(false);
		}
	}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact contact, Manifold manifold) {
		/* Object fixtureUserDataA = contact.getFixtureA().getUserData();
		Object fixtureUserDataB = contact.getFixtureB().getUserData();
		if(fixtureUserDataA instanceof Player || fixtureUserDataB instanceof Player) {
			WorldManifold worldManifold = contact.getWorldManifold();
			Vector2 normal = worldManifold.getNormal();
			if(normal.x == -1.0f || normal.x == 1.0f) {
				player.stayHere();
			}
			else if(normal.x == 0.0f && normal.y == 1.0f) {
				player.setY((int) worldManifold.getPoints()[0].y);
			}
		} */
	}

}
