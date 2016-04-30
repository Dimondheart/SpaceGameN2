package xyz.digitalcookies.spacegame1.spaceentity;

import xyz.digitalcookies.objective.scene.EntityUpdateEvent;

/** Represents a space object that is affiliated with a
 * faction.
 * @author Bryan Charles Bettis
 */
public abstract class Spacecraft extends SpaceObject
{
	@Override
	public void update(EntityUpdateEvent event)
	{
		super.update(event);
	}
	
	@Override
	public boolean applyHit(Hit hit)
	{
		return getHull().applyHit(hit);
	}
	
	/** Shortcut method for getting the body and casting it to a hull.
	 * @return the hull of this spacecraft
	 */
	public Hull getHull()
	{
		return (Hull) getBody();
	}
}
