package xyz.digitalcookies.spacegame1;

import xyz.digitalcookies.objective.scene.Body;

/** Physical aspects common to all space objects, including mass, direction,
 * velocity, steering force, and the occupied region.
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
	
	/** Basic constructor. */
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
	
	/** Get the vector representing the direction this body is
	 * facing.
	 * @return the vector representing the direction this body
	 * 		is facing
	 */
	public PlaneVector getDirection()
	{
		return direction;
	}
	
	/** Get the angular velocity of this body.
	 * 
	 * @return the angular velocity that the direction of this body
	 * 		will turn at, in degrees per second
	 */
	public double getRotation()
	{
		return rv;
	}
	
	/** Set the angular velocity of this body.
	 * @param rv the angular velocity this body should turn at,
	 * 		in degrees per second
	 */
	public void setRotation(double rv)
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
		 * vector. This makes the velocity change towards the
		 * desired direction, defined by the steering force, faster.
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