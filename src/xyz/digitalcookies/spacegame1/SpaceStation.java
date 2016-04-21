package xyz.digitalcookies.spacegame1;

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
		AffineTransform orig = event.getContext().getTransform();
		event.getContext().rotate(-getBody().getDirection().getDirectionRad(), 0, 0);
		BufferedImage img =
				GraphicsManager.getResManager().getRes("starbase-tex.png");
		event.getContext().scale(
				getBody().getRegion().getRadius()
					*2
					/img.getWidth(),
				getBody().getRegion().getRadius()
					*2
					/img.getWidth()
				);
		ImageDrawer.drawGraphic(
				event.getContext(),
				img,
				-img.getWidth()/2,
				-img.getHeight()/2
				);
		event.getContext().setTransform(orig);
	}
}
