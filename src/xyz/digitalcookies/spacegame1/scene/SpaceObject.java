package xyz.digitalcookies.spacegame1.scene;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import xyz.digitalcookies.objective.graphics.GraphicsManager;
import xyz.digitalcookies.objective.graphics.ImageDrawer;
import xyz.digitalcookies.objective.graphics.RenderEvent;
import xyz.digitalcookies.objective.scene.Entity;
import xyz.digitalcookies.objective.scene.EntityUpdateEvent;
import xyz.digitalcookies.spacegame1.Circle;

/** Any independent entity existing within a galaxy region.
 * "Independent" means that its operation is not dependent
 * on a "parent entity," such as a ship.
 * @author Bryan Charles Bettis
 */
public abstract class SpaceObject implements Entity
{
	public static final String EVENT_ELAPSED = "elapsed";
	public static final String EVENT_ENTITIES = "entities";
	public static final String EVENT_PLAYER_CTRL = "playerCtrl";
	
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
	}
	
	@Override
	public void render(RenderEvent event)
	{
		RegionCamera camera =
				(RegionCamera) event.getProperty(GalaxyRegionScene.EVENT_CAMERA);
		int x =
				camera.getX(getBody().getRegion().getX());
		int y = 
				camera.getY(-getBody().getRegion().getY());
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
				GraphicsManager.getResManager().getRes(getMainImage());
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
	
	public abstract String getMainImage();
	
	public boolean isDestroyed()
	{
		return isDestroyed;
	}
	
	public SpaceBody getBody()
	{
		return body;
	}
	
	public void updatePhysics(double elapsed)
	{
		getBody().update(elapsed);
	}
	
	protected void setDestroyed(boolean destroyed)
	{
		isDestroyed = destroyed;
	}
}
