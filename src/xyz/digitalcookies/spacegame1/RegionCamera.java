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
		double offset = GraphicsManager.getMainLayerSet().getWidth()/2;
		if (following != null)
		{
			offset -= following.getBody().getRegion().getX();
		}
		return (int) offset;
	}

	@Override
	public int getY()
	{
		double offset = -GraphicsManager.getMainLayerSet().getHeight()/2;
		if (following != null)
		{
			offset -= following.getBody().getRegion().getY()+getScale()*3/4;
		}
		return (int) -offset;
	}
	
	@Override
	public double getScale()
	{
		return scale;
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
