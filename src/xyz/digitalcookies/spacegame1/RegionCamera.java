package xyz.digitalcookies.spacegame1;

import xyz.digitalcookies.objective.graphics.GraphicsManager;
import xyz.digitalcookies.objective.scene.Camera;

/** The camera for a galaxy region scene.
 * @author Bryan Charles Bettis
 */
public class RegionCamera implements Camera
{
	/** The object the camera will follow, if set. */
	private SpaceObject following;
	private double scale = 1.0;
	
	
	@Override
	public int getX()
	{
		double offset = 0.0;
		if (following != null)
		{
			PlaneVector scaled = following.getBody().getRegion().getPosition().clone();
			scaled.setMagnitude(scaled.getMagnitude()*getScale());
			offset -= scaled.getX();
		}
		offset += GraphicsManager.getMainLayerSet().getWidth()/2;
		return (int) offset;
	}
	
	@Override
	public int getX(double x)
	{
		return (int)(x*getScale() + getX());
	}

	@Override
	public int getY()
	{
		double offset = 0.0;
		if (following != null)
		{
			PlaneVector scaled = following.getBody().getRegion().getPosition().clone();
			scaled.setMagnitude(scaled.getMagnitude()*getScale());
			offset -= scaled.getY();
		}
		offset -= GraphicsManager.getMainLayerSet().getHeight()/2;
		return -(int) offset;
	}
	
	@Override
	public int getY(double y)
	{
		return (int)(y*getScale() + getY());
	}
	
	@Override
	public double getScale()
	{
		return scale;
	}
	
	@Override
	public void setScale(double scale)
	{
		this.scale = scale;
	}
	
	public void follow(SpaceObject object)
	{
		following = object;
	}
	
	public void zoomIn()
	{
		scale *= 1.05;
	}
	
	public void zoomOut()
	{
		scale *= 0.95;
	}
}
