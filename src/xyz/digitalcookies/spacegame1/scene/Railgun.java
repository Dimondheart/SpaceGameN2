package xyz.digitalcookies.spacegame1.scene;

import java.util.List;

import xyz.digitalcookies.objective.scene.EntityUpdateEvent;

public class Railgun extends Weapon
{
	private double sinceLastFire;
	
	public Railgun()
	{
		sinceLastFire = 0;
	}
	
	@Override
	public void update(EntityUpdateEvent event)
	{
		super.update(event);
		sinceLastFire +=
				(double) event.getProperty(GalaxyRegionScene.UPDATE_ELAPSED);
		if (isActive() && sinceLastFire > 0.8)
		{
			SentientSpaceObject parent =
					(SentientSpaceObject)
					event.getProperty(SentientObjectModule.EVENT_PARENT);
			Cannonball ball = new Cannonball(parent);
			ball.getBody().getRegion().setRadius(16);
			ball.getBody().getRegion().getPosition().addVector(
					parent.getBody().getRegion().getPosition()
					);
			ball.getBody().getVelocity().setVector(
					1500,
					parent.getBody().getDirection().getDirectionDeg()
					);
			((List<SpaceObject>) event.getProperty(GalaxyRegionScene.EVENT_NEW_OBJECTS)).add(ball);
			sinceLastFire = 0;
		}
	}
}
