package xyz.digitalcookies.ogetest;

import xyz.digitalcookies.objective.scene.Body;
import xyz.digitalcookies.objective.scene.Entity;

/** Physical (interactable) portion of an entity within <b>1</b> scene.
 * This has been separated from the SpaceObject type because a space object
 * can have up to 2 bodies at once (one for each scene it is in.)
 * @author Bryan Charles Bettis
 */
public class SpaceBody extends Body
{
	private double rv;
	private PlaneVector velocity;
	private PlaneVector acceleration;
	private PlaneVector direction;
	private Circle region;
	private double mass;
	
	public SpaceBody()
	{
		super(null, null);
		region = new Circle(0, 0, 6);
		velocity = new PlaneVector();
		acceleration = new PlaneVector();
		direction = PlaneVector.I_VECTOR.clone();
	}
	
	/** Get the shape object managing the location and bounds
	 * of this space body
	 * @return the shape (a Circle) representing the spatial properties
	 * 		(location and size) of this body
	 */
	public Circle getRegion()
	{
		return region;
	}
	
	public double getDirection()
	{
		return direction.getDirectionDeg();
	}
	
	public void setDirection(double r)
	{
		double rotation = r % 360;
		if (rotation < 0)
		{
			rotation = 360 + rotation;
		}
		this.direction.setDirection(rotation);
	}
	
	public PlaneVector getDV()
	{
		return direction;
	}
	
	public double getRotationVector()
	{
		return rv;
	}
	
	public void setRotationVector(double rv)
	{
		this.rv = rv;
	}
	
	public PlaneVector getVelocity()
	{
		return velocity;
	}
	
	public PlaneVector getAcceleration()
	{
		return acceleration;
	}
	
	public void update(double elapsed)
	{
		velocity.addVector(acceleration.getX()*elapsed, acceleration.getY()*elapsed);
		region.getPosition().addVector(velocity.getX()*elapsed, velocity.getY()*elapsed);
		direction.rotateDegrees(rv*elapsed);
	}
	
//	public void update(Scene scene)
//	{
//		long currTime = scene.getTimer().getTimeNano();
//		double elapsed = (currTime - lastUpdate)/1000000.0/1000.0;
//		velocity.applyAccel(acceleration, elapsed);
//		area.setX(area.getX() + velocity.getCompX()*elapsed);
//		area.setY(area.getY() + velocity.getCompY()*elapsed);
//		setRotation(getRotation() + getRotationVector()*elapsed);
//		lastUpdate = currTime;
//	}
}
