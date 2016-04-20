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
	public static final String EVENT_OTHER_ENTITIES = "otherEntities";
	
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
		event.getContext().setColor(Color.blue);
		Circle tbc = getBody().getRegion();
		event.getContext().fillOval(
				(int) (tbc.getX()-tbc.getRadius()),
				(int) (-tbc.getY()-tbc.getRadius()),
				(int) (tbc.getRadius()*2),
				(int) (tbc.getRadius()*2)
				);
		event.getContext().setColor(Color.green);
		event.getContext().drawLine(
				(int) (tbc.getX()),
				(int) (-tbc.getY()),
				(int) (tbc.getX()+getBody().getVelocity().getX()),
				(int) (-(tbc.getY()+getBody().getVelocity().getY()))
				);
//		event.getContext().setColor(Color.white);
//		PlaneVector dv = getBody().getDirection().clone();
//		dv.setMagnitude(15);
//		event.getContext().drawLine(
//				(int) (tbc.getX()),
//				(int) (-tbc.getY()),
//				(int) (tbc.getX()+dv.getX()),
//				(int) (-(tbc.getY()+dv.getY()))
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
