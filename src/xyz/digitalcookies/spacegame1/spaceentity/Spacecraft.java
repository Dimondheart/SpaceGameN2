package xyz.digitalcookies.spacegame1.spaceentity;

import xyz.digitalcookies.objective.graphics.RenderEvent;
import xyz.digitalcookies.objective.scene.EntityUpdateEvent;
import xyz.digitalcookies.spacegame1.HullData;

/** Represents a space object that is affiliated with a
 * faction.
 * @author Bryan Charles Bettis
 */
public abstract class Spacecraft extends SpaceObject
{
	public Spacecraft(HullData hullData)
	{
		setBody(new Hull(hullData));
//		getBody().setRotation(35);
	}
	@Override
	public void update(EntityUpdateEvent event)
	{
		event.setProperty(SpacecraftModule.EVENT_PARENT, this);
		super.update(event);
		// Clip velocity to max
		if (getBody().getVelocity().getMagnitude() > 1600)
		{
			getBody().getVelocity().setMagnitude(1600);
		}
	}
	
	@Override
	public boolean applyHit(Hit hit)
	{
		return getHull().applyHit(hit);
	}
	
	@Override
	public void render(RenderEvent event)
	{
		super.render(event);
		getHull().render(event);
	}
	
	/** Shortcut method for getting the body and casting it to a hull.
	 * @return the hull of this spacecraft
	 */
	public Hull getHull()
	{
		return (Hull) getBody();
	}
}
