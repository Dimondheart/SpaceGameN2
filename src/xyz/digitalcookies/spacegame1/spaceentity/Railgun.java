package xyz.digitalcookies.spacegame1.spaceentity;

import java.util.List;

import xyz.digitalcookies.objective.scene.EntityUpdateEvent;
import xyz.digitalcookies.spacegame1.scene.RegionScene;

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
				(double) event.getProperty(RegionScene.UEP_ELAPSED);
		if (isActive() && sinceLastFire > 0.8)
		{
			Spacecraft parent =
					(Spacecraft)
					event.getProperty(SpacecraftModule.EVENT_PARENT);
			Cannonball ball = new Cannonball(parent);
			ball.getBody().getRegion().setRadius(16);
			ball.getBody().getRegion().getPosition().addVector(
					parent.getBody().getRegion().getPosition()
					);
			ball.getBody().getVelocity().setVector(
					1500,
					parent.getBody().getDirection().getDirectionDeg()
					);
			((List<SpaceEntity>) event.getProperty(RegionScene.UEP_ADD_SPACE_ENTITY)).add(ball);
			sinceLastFire = 0;
		}
	}
}
