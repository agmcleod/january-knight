package game;

import java.util.Iterator;

import com.badlogic.gdx.math.Rectangle;

public class WorldCollision {
	private Player player;
	public WorldCollision(Player player) {
		this.player = player;
	}
	
	public void checkIfEntityIsOnGround(Level level, MoveableEntity entity) {
		boolean result = false;
		Iterator<Rectangle> it = level.getCollisionTiles().iterator();
		Rectangle resultingGround = null;
		while(it.hasNext()) {
			Rectangle r = it.next();
			if(r.overlaps(entity.getWorldCollisionRectangle())) {
				result = true;
				resultingGround = r;
			}
		}
		entity.setTouchingOnFoot(result);
		if(entity.isFalling() && result && resultingGround != null) {
			entity.setY((int) (resultingGround.y + resultingGround.height));
		}
	}
	
	public void checkIfPlayerTouchesBySide(Level level) {
		Iterator<Rectangle> it = level.getCollisionTiles().iterator();
		boolean noTouching = true;
		while(it.hasNext()) {
			Rectangle r = it.next();
			if((player.getY() >= r.y && player.getY() < (r.y + r.height)) 
					|| (player.getTopY() >= r.y && player.getTopY() <= (r.y + r.height))) {
				// touches on right of player
				if(entityTouchesRightOfRect(player, r) && !entityTouchesLeftOfRect(player, r)) {
					player.setTouchingOnRight(true);
					player.setTouchingOnLeft(false);
					noTouching = false;
					break;
				}
				// touches on the left
				else if(!entityTouchesRightOfRect(player, r) && entityTouchesLeftOfRect(player, r)) {
					player.setTouchingOnLeft(true);
					player.setTouchingOnRight(false);
					noTouching = false;
					break;
				}
			}
		}
		if(noTouching) {
			player.setTouchingOnRight(false);
			player.setTouchingOnLeft(false);
		}
	}
	
	public boolean entityTouchesLeftOfRect(Entity entity, Rectangle rect) {
		boolean result = (entity.getCollisionX() >= rect.x && entity.getCollisionX() <= (rect.x + rect.width));
		return result;
	}
	
	public boolean entityTouchesRightOfRect(Entity entity, Rectangle rect) {
		boolean result = (entity.getCollisionRightX() >= rect.x && entity.getCollisionRightX() <= (rect.x + rect.width));
		return result;
	}
}
