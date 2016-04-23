package xyz.digitalcookies.spacegame1.scene;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import xyz.digitalcookies.objective.graphics.GraphicsManager;
import xyz.digitalcookies.objective.graphics.ImageDrawer;
import xyz.digitalcookies.objective.graphics.RenderEvent;

/** Cannot be directly controlled by a player. A spacecraft or artificial
 * structure with minimal or no means of direct propulsion.
 * @author Bryan Charles Bettis
 */
public class SpaceStation extends SentientSpaceObject
{
	public SpaceStation()
	{
		this(0, 0);
	}
	
	public SpaceStation(double x, double y)
	{
		getBody().getRegion().setRadius(800);
		getBody().getRegion().getPosition().setVectorComp(x, y);
	}
	
	@Override
	public void render(RenderEvent event)
	{
		super.render(event);
		RegionCamera camera =
				(RegionCamera) event.getProperty(GalaxyRegionScene.EVENT_CAMERA);
		AffineTransform orig = event.getContext().getTransform();
		event.getContext().rotate(-getBody().getDirection().getDirectionRad(), 0, 0);
		BufferedImage img =
				GraphicsManager.getResManager().getRes("starbase-tex.png");
		double w = getBody().getRegion().getRadius()*2*camera.getScale();
		double h = img.getHeight()*w/img.getWidth();
		ImageDrawer.drawGraphic(
				event.getContext(),
				img,
				-(int)(w/2),
				-(int)(h/2),
				(int) w,
				(int) h
				);
		event.getContext().setTransform(orig);
	}
}
