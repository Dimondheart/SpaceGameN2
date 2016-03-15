package xyz.digitalcookies.ogetest;

import xyz.digitalcookies.objective.utility.ExtendedMath;

public class MoveVector
{
	private double magnitude;
	private double direction;
	
	public MoveVector()
	{
		setVector(0,0);
	}
	
	public void setVector(double magnitude, double direction)
	{
		setMagnitude(magnitude);
		setDirection(direction);
	}
	
	public void setVectorComp(double cx, double cy)
	{
		setCompX(cx);
		setCompY(cy);
	}
	
	public void zeroVector()
	{
		setVector(0,0);
	}
	
	public double getCompX()
	{
		return getMagnitude()*Math.cos(getDirectionRad());
	}
	
	public void setCompX(double cx)
	{
		double cy = getCompY();
		double newMag = 0;
		double newDir = 0;
		if (cx == 0)
		{
			newMag = cy;
			if (cy >= 0)
			{
				newDir = 90;
			}
			else
			{
				newDir = 270;
			}
		}
		else
		{
			newMag = Math.pow((Math.pow(cx, 2.0) + Math.pow(cy, 2.0)), 0.5);
			newDir = ExtendedMath.radToDeg(Math.asin(cy/newMag));
		}
		if (!Double.isFinite(newMag))
		{
			newMag = 0;
		}
		if (!Double.isFinite(newDir))
		{
			newDir = 0;
		}
		setVector(newMag, newDir);
	}
	
	public double getCompY()
	{
		return getMagnitude()*Math.sin(getDirectionRad());
	}
	
	public void setCompY(double cy)
	{
		double cx = getCompX();
		double newMag = 0;
		double newDir = 0;
		if (cy == 0)
		{
			newMag = cx;
			if (cx >= 0)
			{
				newDir = 0;
			}
			else
			{
				newDir = 180;
			}
		}
		else
		{
			newMag = Math.pow((Math.pow(cx, 2.0) + Math.pow(cy, 2.0)), 0.5);
			newDir = ExtendedMath.radToDeg(Math.acos(cx/newMag));
		}
		if (!Double.isFinite(newMag))
		{
			newMag = 0;
		}
		if (!Double.isFinite(newDir))
		{
			newDir = 0;
		}
		setVector(newMag, newDir);
	}
	
	public double getMagnitude()
	{
		return magnitude;
	}
	
	public void setMagnitude(double magnitude)
	{
		this.magnitude = magnitude;
	}
	
	public double getDirection()
	{
		return direction;
	}
	
	protected double getDirectionRad()
	{
		return ExtendedMath.degToRad(getDirection());
	}
	
	public void setDirection(double direction)
	{
		this.direction = direction % 360;
		if (this.direction < 0)
		{
			this.direction = 360 + this.direction;
		}
	}
	
	public void applyAccel(MoveVector accel, double elapsed)
	{
		double xAmt = accel.getCompX()*elapsed;
		double yAmt = accel.getCompY()*elapsed;
		double cx = getCompX();
		double cy = getCompY();
		double newCX = cx + xAmt;
		double newCY = cy + yAmt;
//		System.out.println(
//				"CX:"
//				+ String.format("%.4f", newCX)
//				+ ", CY:"
//				+ String.format("%.4f", newCY)
//				);
		double newMag = calcMagnitude(newCX, newCY);
		double newDir = calcDirection(newCX, newCY);
		setVector(newMag, newDir);
	}
	
	protected double calcMagnitude(double cx, double cy)
	{
		return Math.pow(
				(
					Math.pow(cx, 2.0)
					+ Math.pow(cy, 2.0)
				),
				0.5
				);
	}
	
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
		double dir = 0;
		dir = ExtendedMath.radToDeg(Math.atan(Math.abs(cy/cx)));
		if (cx > 0 && cy > 0)
		{
			// do nothing
		}
		else if (cx < 0 && cy > 0)
		{
			dir = 180 - dir;
		}
		else if (cx < 0 && cy < 0)
		{
			dir = 180 + dir;
		}
		else if (cx > 0 && cy < 0)
		{
			dir = 360 - dir;
		}
		return dir;
	}
}
