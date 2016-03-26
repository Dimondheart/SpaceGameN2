package xyz.digitalcookies.ogetest;

import xyz.digitalcookies.objective.scene.EntityUpdateEvent;
import xyz.digitalcookies.objective.graphics.RenderEvent;

/** A unit that can be controlled directly by the player (or an AI.)
 * A spacecraft that is pilotable.
 * @author Bryan Charles Bettis
 */
public class Spaceship extends SentientSpaceObject
{
	@Override
	public void update(EntityUpdateEvent event)
	{
		super.update(event);
	}

	@Override
	public void render(RenderEvent event)
	{
//		// Render acceleration vector
//		event.getContext().setColor(Color.red);
//		int ax = (int) (x + getBody().getAccel().getCompX());
//		int ay = (int) (y - getBody().getAccel().getCompY());
//		event.getContext().drawLine(x, y, ax, ay);
//		// Render velocity vector
//		event.getContext().setColor(Color.green);
//		int vx = (int) (x + getBody().getVelocity().getCompX());
//		int vy = (int) (y - getBody().getVelocity().getCompY());
//		event.getContext().drawLine(x, y, vx, vy);
//		// Render direction facing
//		event.getContext().setColor(Color.white);
//		int rx = (int) (x + Math.cos(getBody().getRotation()*Math.PI/180.0)*10);
//		int ry = (int) (y - Math.sin(getBody().getRotation()*Math.PI/180.0)*10);
//		event.getContext().drawLine(x, y, rx, ry);
	}
}
