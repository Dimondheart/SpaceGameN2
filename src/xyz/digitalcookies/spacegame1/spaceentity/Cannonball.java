package xyz.digitalcookies.spacegame1.spaceentity;

import java.awt.Color;
import java.util.List;

import xyz.digitalcookies.objective.graphics.RenderEvent;
import xyz.digitalcookies.objective.scene.Entity;
import xyz.digitalcookies.objective.scene.EntityUpdateEvent;
import xyz.digitalcookies.spacegame1.scene.RegionCamera;
import xyz.digitalcookies.spacegame1.scene.RegionScene;

public class Cannonball extends SpaceObject
{
	private Spacecraft firedBy;
	
	public Cannonball(Spacecraft firedBy)
	{
		this.firedBy = firedBy;
	}
	
	@Override
	public boolean applyHit(Hit hit)
	{
		// Cannot be hit (for now)
		return false;
	}
	
	@Override
	public void update(EntityUpdateEvent event)
	{
		super.update(event);
		for (Entity e : (List<SpaceEntity>) event.getProperty(RegionScene.UEP_ENTITIES))
		{
			if (e == this)
			{
				continue;
			}
			else
			{
				break;
			}
		}
	}
	
	@Override
	public void render(RenderEvent event)
	{
		super.render(event);
		RegionCamera camera =
				(RegionCamera) event.getProperty(RegionScene.REP_CAMERA);
		event.getGC().setColor(Color.green);
		event.getGC().fillOval(
				(int) (-getBody().getRegion().getRadius()*camera.getScale()),
				(int) (-getBody().getRegion().getRadius()*camera.getScale()),
				(int) (getBody().getRegion().getRadius()*2*camera.getScale()),
				(int) (getBody().getRegion().getRadius()*2*camera.getScale())
				);
	}
}
