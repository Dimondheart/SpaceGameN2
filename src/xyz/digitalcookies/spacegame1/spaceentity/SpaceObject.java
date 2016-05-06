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
	private IntelligentSteering steering;
	
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
		try
		{
			getSteering().applySteering(this);
		}
		catch (NullPointerException e)
		{
			// No steering set
		}
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
		event.getGC().translate(
				x,
				y
				);
		event.getGC().rotate(
				-getBody().getDirection().getDirectionRad(),
				0,
				0
				);
//		Circle tbc = getBody().getRegion();
//		event.getContext().setColor(Color.yellow);
//		event.getContext().fillOval(
//				(int) (-tbc.getRadius()*camera.getScale()),
//				(int) (-tbc.getRadius()*camera.getScale()),
//				(int) (tbc.getRadius()*2*camera.getScale()),
//				(int) (tbc.getRadius()*2*camera.getScale())
//				);
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
	
	public IntelligentSteering getSteering()
	{
		return steering;
	}
	
	public void setSteering(IntelligentSteering steering)
	{
		this.steering = steering;
	}
	
	public SpaceBody getBody()
	{
		return body;
	}
	
	protected void setBody(SpaceBody body)
	{
		this.body = body;
	}
}
