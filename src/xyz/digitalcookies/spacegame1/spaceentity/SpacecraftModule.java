package xyz.digitalcookies.spacegame1.spaceentity;

import xyz.digitalcookies.objective.graphics.RenderEvent;
import xyz.digitalcookies.objective.graphics.Renderer;
import xyz.digitalcookies.objective.scene.Entity;
import xyz.digitalcookies.objective.scene.EntityUpdateEvent;

/** A component of a sentient object that is controlled or managed
 * by the sentient object containing/owning it.
 * @author Bryan Charles Bettis
 */
public abstract class SpacecraftModule implements Renderer, Entity
{
	public static final String EVENT_PARENT = "parent";
	@Override
	public void render(RenderEvent event)
	{
	}
	
	@Override
	public boolean utilizesBody()
	{
		return false;
	}
	
	@Override
	public void update(EntityUpdateEvent event)
	{
	}
	
	public void applyHit(Hit hit)
	{
	}
}
