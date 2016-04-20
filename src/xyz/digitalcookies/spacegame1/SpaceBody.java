package xyz.digitalcookies.spacegame1;

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
	private PlaneVector steering;
	private PlaneVector direction;
	private Circle region;
	private double mass;
	
	public SpaceBody()
	{
		region = new Circle(0, 0, 6);
		velocity = new PlaneVector();
		steering = new PlaneVector();
		direction = new PlaneVector(1.0, 0.0, true);
	}
	
	/** Get the shape object managing the location and bounds
	 * of this space body.
	 * @return the shape (a Circle) representing the spatial properties
	 * 		(location and size) of this body
	 */
	public Circle getRegion()
	{
		return region;
	}
	
	public PlaneVector getDirection()
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
	
	public PlaneVector getSteering()
	{
		return steering;
	}
	
	public void update(double elapsed)
	{
		/* Offset the steering force before using it as the acceleration
		 * vector. This makes the object accelerate quicker towards the
		 * desired velocity defined by the steering force.
		 */
		PlaneVector accel = new PlaneVector();
		// Determine the offset vector
		PlaneVector accelOffset = new PlaneVector();
		// Offset magnitude should be less than original steering magnitude
		accelOffset.setMagnitude(getSteering().getMagnitude()*2.0/3.0);
		// Offset direction is steering - (steering - current velocity)
		accelOffset.setDirection(
				2.0*getSteering().getDirectionDeg()
				- getVelocity().getDirectionDeg()
				);
		// Set the acceleration with offset
		accel.setVectorComp(
				getSteering().getX() + accelOffset.getX(),
				getSteering().getY() + accelOffset.getY()
				);
		// Apply the original magnitude of the steering force
		accel.setMagnitude(getSteering().getMagnitude());
		/* Update the rotation 'vector' to be turning towards the new 
		 * acceleration.
		 */
		double rotToAccel = 
				accel.getDirectionDeg()
				- getDirection().getDirectionDeg();
		if ((0 < rotToAccel && rotToAccel < 180) || (rotToAccel < -180))
		{
			setRotationVector(45);
		}
		else if ((-180 < rotToAccel && rotToAccel < 0) || (rotToAccel >= 180))
		{
			setRotationVector(-45);
		}
		else
		{
			setRotationVector(0);
		}
		/* Apply updates to the vectors and body region. */
		// Apply acceleration
		velocity.addVector(accel.getX()*elapsed, accel.getY()*elapsed);
		// Apply velocity
		region.getPosition().addVector(velocity.getX()*elapsed, velocity.getY()*elapsed);
		// Apply rotation
		direction.rotateDegrees(rv*elapsed);
		// Clear the previous steering forces
		getSteering().zeroVector();
	}
}
