package xyz.digitalcookies.spacegame1.spaceentity;

public class Asteroid extends SpaceObject
{
	public Asteroid(int resources, double x, double y)
	{
		getBody().getRegion().setRadius(resources);
		getBody().setMass(resources);
		getBody().getPos().setVectorComp(x, y);
	}
	
	@Override
	public boolean applyHit(Hit hit)
	{
		return false;
	}
}
