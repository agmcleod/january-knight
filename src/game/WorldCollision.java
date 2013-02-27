package game;

import java.util.Iterator;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

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
	
	public void checkIfPlayerTouchesBySideAndCantMove(Level level) {
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
	
	public void weaponTouchesEntities(Weapon weapon, float angle, Level currentLevel) {
		Vector2 axis1 = new Vector2(), axis2 = new Vector2(), axis3 = new Vector2(), axis4 = new Vector2();
		Rectangle box = weapon.getBox();
		float a = (angle) * (MathUtils.PI/180);
		
		Vector2 upperRight = new Vector2(box.x + box.width, box.y + box.height);
		Vector2 upperLeft = new Vector2(box.x, box.y + box.height);
		Vector2 lowerRight = new Vector2(box.x + box.width, box.y);
		Vector2 lowerLeft = new Vector2(box.x, box.y);
		
		// rotate the weapon box by its angle
		upperRight.x = MathUtils.cos(a) * (upperRight.x - lowerLeft.x) - MathUtils.sin(a) * (upperRight.y - lowerLeft.y) + lowerLeft.x;
		upperRight.y = MathUtils.sin(a) * (upperRight.x - lowerLeft.x) + MathUtils.cos(a) * (upperRight.y - lowerLeft.y) + lowerLeft.y;
		
		upperLeft.x = MathUtils.cos(a) * (upperLeft.x - lowerLeft.x) - MathUtils.sin(a) * (upperLeft.y - lowerLeft.y) + lowerLeft.x;
		upperLeft.y = MathUtils.sin(a) * (upperLeft.x - lowerLeft.x) + MathUtils.cos(a) * (upperLeft.y - lowerLeft.y) + lowerLeft.y;
		
		lowerRight.x = MathUtils.cos(a) * (lowerRight.x - lowerLeft.x) - MathUtils.sin(a) * (lowerRight.y - lowerLeft.y) + lowerLeft.x;
		lowerRight.y = MathUtils.sin(a) * (lowerRight.x - lowerLeft.x) + MathUtils.cos(a) * (lowerRight.y - lowerLeft.y) + lowerLeft.y;
		
		Iterator<MoveableEntity> it = currentLevel.getEnemies().iterator();
		int index = 0;
		Array<Integer> toRemove = new Array<Integer>();
		while(it.hasNext()) {
			MoveableEntity entity = it.next();
			boolean collides = false;
			collides = (entity.getWorldCollisionRectangle().contains(box.x, box.y) 
				|| entity.getWorldCollisionRectangle().contains(lowerRight.x, lowerRight.y)
				|| entity.getWorldCollisionRectangle().contains(upperLeft.x, upperLeft.y)
				|| entity.getWorldCollisionRectangle().contains(upperRight.x, upperRight.y));
			if(collides && !weapon.hasHit()) {
				if(entity.takeDamage()) {
					toRemove.add(index);
				}
				weapon.setHit(true);
			}
			index++;
		}
		currentLevel.removeEntities(toRemove);
	}
}
