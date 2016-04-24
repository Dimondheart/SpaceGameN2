package xyz.digitalcookies.spacegame1.scene;

import xyz.digitalcookies.objective.scene.EntityUpdateEvent;
import xyz.digitalcookies.spacegame1.PlaneVector;

/** A module that can be attached to a sentient space object to allow
 * it to have an effect (usually harmful) to a space object in its
 * surroundings.
 * @author Bryan Charles Bettis
 */
public abstract class Weapon extends SentientObjectModule
{
	private boolean isActive;
	
	public Weapon()
	{
		setActive(false);
	}
	
	@Override
	public void update(EntityUpdateEvent event)
	{
		super.update(event);
	}
	
	public boolean isActive()
	{
		return isActive;
	}
	
	public void setActive(boolean active)
	{
		isActive = active;
	}
}
