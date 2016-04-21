package xyz.digitalcookies.spacegame1;

import java.awt.Color;

import xyz.digitalcookies.objective.graphics.RenderEvent;
import xyz.digitalcookies.objective.scene.Entity;
import xyz.digitalcookies.objective.scene.EntityUpdateEvent;

/** Any independent entity existing within 1-2 galaxy regions
 * at a time. "Independent" means that its operation is not dependent
 * on a "parent entity," such as a ship.
 * @author Bryan Charles Bettis
 */
public abstract class SpaceObject implements Entity
{
	public static final String EVENT_ELAPSED = "elapsed";
	public static final String EVENT_ENTITIES = "entities";
	public static final String EVENT_PLAYER_CTRL = "playerCtrl";
	
	private SpaceBody body;
	private int lastX;
	
	public SpaceObject()
	{
		body = new SpaceBody();
	}
	
	@Override
	public final boolean utilizesBody()
	{
		return true;
	}
	
	@Override
	public void update(EntityUpdateEvent event)
	{
	}
	
	@Override
	public void render(RenderEvent event)
	{
		Circle tbc = getBody().getRegion();
		event.getContext().translate(
				(int) getBody().getRegion().getX(),
				(int) -getBody().getRegion().getY()
				);
//		event.getContext().setColor(Color.yellow);
//		event.getContext().fillOval(
//				(int) (-tbc.getRadius()),
//				(int) (-tbc.getRadius()),
//				(int) (tbc.getRadius()*2),
//				(int) (tbc.getRadius()*2)
//				);
		PlaneVector mv = getBody().getVelocity().clone();
		mv.setMagnitude(mv.getMagnitude()*1.5);
		event.getContext().setColor(Color.green);
		event.getContext().drawLine(
				(int) (0),
				(int) (0),
				(int) (mv.getX()),
				(int) (-(mv.getY()))
				);
//		event.getContext().setColor(Color.white);
//		PlaneVector dv = getBody().getDirection().clone();
//		dv.setMagnitude(15);
//		event.getContext().drawLine(
//				(int) (0),
//				(int) (0),
//				(int) (dv.getX()),
//				(int) (-(dv.getY()))
//				);
	}
	
	public SpaceBody getBody()
	{
		return body;
	}
	
	public void updatePhysics(double elapsed)
	{
		getBody().update(elapsed);
	}
}