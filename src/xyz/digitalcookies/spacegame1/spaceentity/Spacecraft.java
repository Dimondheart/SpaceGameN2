package xyz.digitalcookies.spacegame1.spaceentity;

import java.awt.image.BufferedImage;

import xyz.digitalcookies.objective.graphics.GraphicsManager;
import xyz.digitalcookies.objective.graphics.ImageDrawer;
import xyz.digitalcookies.objective.graphics.RenderEvent;
import xyz.digitalcookies.objective.scene.EntityUpdateEvent;
import xyz.digitalcookies.spacegame1.HullData;
import xyz.digitalcookies.spacegame1.scene.RegionCamera;
import xyz.digitalcookies.spacegame1.scene.RegionScene;

/** Represents a space object that is affiliated with a
 * faction.
 * @author Bryan Charles Bettis
 */
public abstract class Spacecraft extends SpaceObject
{
	public Spacecraft(HullData hullData)
	{
		setBody(new Hull(hullData));
//		getBody().setRotation(35);
	}
	@Override
	public void update(EntityUpdateEvent event)
	{
		super.update(event);
	}
	
	@Override
	public boolean applyHit(Hit hit)
	{
		return getHull().applyHit(hit);
	}
	
	@Override
	public void render(RenderEvent event)
	{
		super.render(event);
		RegionCamera camera =
				(RegionCamera) event.getProperty(RegionScene.REP_CAMERA);
		BufferedImage img =
				GraphicsManager.getResManager().getRes("hulls/" + getHull().getHullData().getResPath()+"/base.png");
		double w = getBody().getRegion().getRadius()*2.0*camera.getScale();
		double h = img.getHeight()*w/img.getWidth();
		ImageDrawer.drawGraphic(
				event.getContext(),
				img,
				-(int)(w/2),
				-(int)(h/2),
				(int) w,
				(int) h
				);
	}
	
	/** Shortcut method for getting the body and casting it to a hull.
	 * @return the hull of this spacecraft
	 */
	public Hull getHull()
	{
		return (Hull) getBody();
	}
}
