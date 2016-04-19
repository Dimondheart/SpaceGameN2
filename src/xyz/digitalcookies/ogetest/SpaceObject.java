package xyz.digitalcookies.ogetest;

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
	}
}
