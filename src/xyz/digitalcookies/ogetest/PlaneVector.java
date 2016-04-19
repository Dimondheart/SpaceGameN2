package xyz.digitalcookies.ogetest;

import xyz.digitalcookies.objective.utility.ExtendedMath;

public class PlaneVector implements Cloneable
{
	/** The zero vector <0,0>. TODO make immutable. */
	public static final PlaneVector ZERO_VECTOR =
			new PlaneVector(0.0, 0.0, false);
	/** The unit vector <1,0>. TODO make immutable. */
	public static final PlaneVector I_VECTOR =
			new PlaneVector(1.0, 0.0, true);
	/** The unit vector <0,1>. TODO make immutable. */
	public static final PlaneVector J_VECTOR =
			new PlaneVector(0.0, 1.0, true);
	
	private double magnitude;
	private double direction;
	
	/** Constructor, initializes vector as a zero vector <0,0>. */
	public PlaneVector()
	{
		setVector(0,0);
	}
	
	public PlaneVector(double cxOrMag, double cyOrDir, boolean asXYComponents)
	{
		if (asXYComponents)
		{
			setVectorComp(cxOrMag, cyOrDir);
		}
		else
		{
			setVector(cxOrMag, cyOrDir);
		}
	}
	
	@Override
	public PlaneVector clone()
	{
		try
		{
			return (PlaneVector) super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			// Something went terribly wrong...
			e.printStackTrace();
			System.out.println("ERROR cloning PlaneVector.");
			return null;
		}
	}
	
	/** Get the unit vector of this vector.
	 * @return a unit vector pointing in the same direction as this
	 * 		vector
	 */
	public PlaneVector getUnitVector()
	{
		return new PlaneVector(
				getX()/getMagnitude(),
				getY()/getMagnitude(),
				true
				);
	}
	
	public void addVector(double cx, double cy)
	{
		setVectorComp(
				getX() + cx,
				getY() + cy
				);
	}
	
	public void setVector(double magnitude, double direction)
	{
		setMagnitude(magnitude);
		setDirection(direction);
	}
	
	public void setVectorComp(double cx, double cy)
	{
		setVector(calcMagnitude(cx, cy), calcDirection(cx, cy));
//		setX(cx);
//		setY(cy);
	}
	
	/** Sets this vector to <0,0>. */
	public void zeroVector()
	{
		setVector(0,0);
	}
	
	public double getX()
	{
		return getMagnitude()*Math.cos(getDirectionRad());
	}
	
	public void setX(double cx)
	{
		setVector(calcMagnitude(cx, getY()), calcDirection(cx, getY()));
	}
	
	public double getY()
	{
		return getMagnitude()*Math.sin(getDirectionRad());
	}
	
	public void setY(double cy)
	{
		setVector(calcMagnitude(getX(), cy), calcDirection(getX(), cy));
	}
	
	public double getMagnitude()
	{
		return magnitude;
	}
	
	public void setMagnitude(double magnitude)
	{
		this.magnitude = magnitude;
	}
	
	public double getDirectionDeg()
	{
		return direction;
	}
	
	protected double getDirectionRad()
	{
		return ExtendedMath.degToRad(getDirectionDeg());
	}
	
	public void setDirection(double direction)
	{
		this.direction = direction % 360;
		if (this.direction < 0)
		{
			this.direction = 360 + this.direction;
		}
	}
	
	public void rotateDegrees(double degrees)
	{
		setDirection(getDirectionDeg() + degrees);
	}
	
	/** Calculate the magnitude that would result from the specified
	 * x and y vector components.
	 * @param cx the x vector component
	 * @param cy the y vector component
	 * @return the magnitude of a vector with the specified components
	 */
	protected double calcMagnitude(double cx, double cy)
	{
		return Math.hypot(cx, cy);
	}
	
	/** Calculate the angle resulting from the specified x and y
	 * vector components.
	 * @param cx the x vector component
	 * @param cy the y vector component
	 * @return the direction in degrees resulting from the specified vector
	 * 		components
	 */
	protected double calcDirection(double cx, double cy)
	{
		// Handle the special angles that could result in invalid calcs
		if (cx == 0)
		{
			if (cy > 0)
			{
				return 90;
			}
			else if (cy < 0)
			{
				return 270;
			}
			return 0;
		}
		else if (cy == 0)
		{
			if (cx >= 0)
			{
				return 0;
			}
			else if (cx < 0)
			{
				return 180;
			}
			return 0;
		}
		double dir = ExtendedMath.radToDeg(Math.atan(Math.abs(cy/cx)));
		if (cx > 0 && cy > 0)
		{
			return dir;
		}
		else if (cx < 0 && cy > 0)
		{
			return 180 - dir;
		}
		else if (cx < 0 && cy < 0)
		{
			return 180 + dir;
		}
		else if (cx > 0 && cy < 0)
		{
			return 360 - dir;
		}
		else
		{
			return dir;
		}
	}
}
