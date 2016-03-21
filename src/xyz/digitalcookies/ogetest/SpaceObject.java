package xyz.digitalcookies.ogetest;

import xyz.digitalcookies.objective.scene.Entity;
import xyz.digitalcookies.objective.scene.EntityUpdateEvent;

public abstract class SpaceObject implements Entity
{
	@Override
	public boolean utilizesBody()
	{
		return true;
	}
	
	public abstract SpaceBody getBody();
	
	@Override
	public void update(EntityUpdateEvent event)
	{
		if (getBody() != null)
		{
			getBody().update();
		}
	}
}
