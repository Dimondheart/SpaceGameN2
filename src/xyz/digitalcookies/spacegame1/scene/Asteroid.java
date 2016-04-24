package xyz.digitalcookies.spacegame1.scene;

import java.util.Random;
import java.util.List;

import xyz.digitalcookies.objective.graphics.RenderEvent;
import xyz.digitalcookies.objective.scene.EntityUpdateEvent;

public class Asteroid extends AmbientSpaceObject
{
	private int hits = 0;
	private Random rand = new Random();
	
	public Asteroid(int resources, double x, double y)
	{
		getBody().getRegion().setRadius(resources);
		getBody().setMass(resources);
		getBody().getRegion().getPosition().setVectorComp(x, y);
		getBody().setRotation(10);
	}
	
	@Override
	public String getMainImage()
	{
		return "asteroid1.png";
	}
	
	@Override
	public void update(EntityUpdateEvent event)
	{
		for (; hits > 0; --hits)
		{
			if (getBody().getMass() <= 35)
			{
				setDestroyed(true);
				break;
			}
			getBody().setMass(getBody().getMass()/2);
			getBody().getRegion().setRadius(getBody().getMass());
			Asteroid breakoff = new Asteroid(
					(int) getBody().getMass(),
					getBody().getRegion().getX(),
					getBody().getRegion().getY()
					);
			breakoff.getBody().getVelocity().setVector(rand.nextInt(90)+10, rand.nextInt(360));
			getBody().getVelocity().addVector(
					-breakoff.getBody().getVelocity().getX(),
					-breakoff.getBody().getVelocity().getY()
					);
			(
				(List<SpaceObject>)
				event.getProperty(GalaxyRegionScene.EVENT_NEW_OBJECTS)
			)
			.add(breakoff);
		}
	}
	
	public void hit()
	{
		hits += 1;
	}
}
