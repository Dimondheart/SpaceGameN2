package xyz.digitalcookies.spacegame1.spaceentity;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

import xyz.digitalcookies.objective.graphics.GraphicsManager;
import xyz.digitalcookies.objective.scene.EntityUpdateEvent;
import xyz.digitalcookies.spacegame1.HullData.WeaponLinkProperty;
import xyz.digitalcookies.spacegame1.scene.RegionScene;

public class Railgun extends Weapon
{
	private double sinceLastFire;
	
	public Railgun(HashMap<WeaponLinkProperty,Object> link)
	{
		super(link);
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
			BufferedImage img = GraphicsManager.getResManager().getRes(
					"hulls/" + parent.getHull().getHullData().getResPath()+"/base.png"
					);
			Cannonball ball = new Cannonball(parent);
			ball.getBody().getRegion().setRadius(6);
			double ws = (parent.getBody().getRegion().getRadius())/img.getWidth();
			double hs = img.getHeight()*ws/img.getWidth();
			double x = -parent.getBody().getRegion().getRadius();
			double y = hs*parent.getBody().getRegion().getRadius();
			x += (double) getLink().get(WeaponLinkProperty.X);
			y += -(double) getLink().get(WeaponLinkProperty.Y);
			ball.getBody().getPos().addVector(x, y*hs);
			ball.getBody().getPos().rotateDegrees(parent.getBody().getDirection().getDirectionDeg());
			ball.getBody().getPos().addVector(parent.getBody().getPos());
//			ball.getBody().getVelocity().setVector(
//					1000,
//					parent.getBody().getDirection().getDirectionDeg()
//					);
			ball.getBody().getVelocity().addVector(parent.getBody().getVelocity());
			((List<SpaceEntity>) event.getProperty(RegionScene.UEP_ADD_SPACE_ENTITY)).add(ball);
			sinceLastFire = 0;
		}
	}
}
