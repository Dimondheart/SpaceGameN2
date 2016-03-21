package xyz.digitalcookies.ogetest;

import xyz.digitalcookies.objective.scene.Entity;

public abstract class Weapon implements Entity
{
	private double rotation;
	private double rv;
	private Ship ship;
	
	protected Weapon(Ship ship)
	{
		this.ship = ship;
	}
	
	protected Ship getShip()
	{
		return ship;
	}
	
	public abstract boolean fire();
	
	public double getRotation()
	{
		return rotation;
	}
	
	public void setRotation(double r)
	{
		rotation = r % 360;
		if (rotation < 0)
		{
			rotation = 360 + rotation;
		}
	}
	
	public double getRotationVector()
	{
		return rv;
	}
	
	public void setRotationVector(double rv)
	{
		this.rv = rv;
	}
}
