package xyz.digitalcookies.spacegame1.scene;

import java.util.List;

import xyz.digitalcookies.objective.scene.Entity;
import xyz.digitalcookies.objective.scene.EntityUpdateEvent;

public class Cannonball extends AmbientSpaceObject
{
	private SentientSpaceObject firedBy;
	
	public Cannonball(SentientSpaceObject firedBy)
	{
		this.firedBy = firedBy;
	}

	@Override
	public String getMainImage()
	{
		return "cannonball.png";
	}
	
	@Override
	public void update(EntityUpdateEvent event)
	{
		super.update(event);
		for (Entity e : (List<Entity>) event.getProperty(SpaceObject.EVENT_ENTITIES))
		{
			if (e == this || e == firedBy)
			{
				continue;
			}
			else
			{
				SpaceObject o = (SpaceObject) e;
				if (o.getBody().getRegion().intersects(getBody().getRegion()))
				{
					if (o instanceof Asteroid)
					{
						((Asteroid) o).hit();
					}
					setDestroyed(true);
					break;
				}
			}
		}
	}
}
