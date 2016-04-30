package xyz.digitalcookies.spacegame1.spaceentity;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import xyz.digitalcookies.objective.graphics.GraphicsManager;
import xyz.digitalcookies.objective.graphics.ImageDrawer;
import xyz.digitalcookies.objective.graphics.RenderEvent;
import xyz.digitalcookies.objective.scene.EntityUpdateEvent;
import xyz.digitalcookies.spacegame1.scene.RegionCamera;
import xyz.digitalcookies.spacegame1.scene.RegionScene;

/** Any space entity that has a body that moves around and interacts
 * with other space entities.
 * @author Bryan Charles Bettis
 */
public abstract class SpaceObject extends SpaceEntity
{
	private SpaceBody body;
	private boolean isDestroyed;
	
	public SpaceObject()
	{
		body = new SpaceBody();
		isDestroyed = false;
	}
	
	@Override
	public final boolean utilizesBody()
	{
		return true;
	}
	
	@Override
	public void update(EntityUpdateEvent event)
	{
		getBody().update(event);
	}
	
	@Override
	public void render(RenderEvent event)
	{
		RegionCamera camera =
				(RegionCamera) event.getProperty(RegionScene.REP_CAMERA);
		int x =
				camera.getX(getBody().getPos().getX());
		int y = 
				camera.getY(-getBody().getPos().getY());
		event.getContext().translate(
				x,
				y
				);
//		Circle tbc = getBody().getRegion();
//		event.getContext().setColor(Color.yellow);
//		event.getContext().fillOval(
//				(int) (-tbc.getRadius()*camera.getScale()),
//				(int) (-tbc.getRadius()*camera.getScale()),
//				(int) (tbc.getRadius()*2*camera.getScale()),
//				(int) (tbc.getRadius()*2*camera.getScale())
//				);
		AffineTransform orig = event.getContext().getTransform();
		event.getContext().rotate(-getBody().getDirection().getDirectionRad(), 0, 0);
		BufferedImage img =
				GraphicsManager.getResManager().getRes("noImage.png");
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
//		PlaneVector mv = getBody().getVelocity().clone();
//		mv.setMagnitude(mv.getMagnitude()*1.5);
//		event.getContext().setColor(Color.green);
//		event.getContext().drawLine(
//				(int) (0),
//				(int) (0),
//				(int) (mv.getX()),
//				(int) (-(mv.getY()))
//				);
//		event.getContext().setColor(Color.white);
//		PlaneVector dv = getBody().getDirection().clone();
//		dv.setMagnitude(15);
//		event.getContext().drawLine(
//				(int) (0),
//				(int) (0),
//				(int) (dv.getX()),
//				(int) (-(dv.getY()))
//				);
	}
	
	public abstract boolean applyHit(Hit hit);
	
	public SpaceBody getBody()
	{
		return body;
	}
}
