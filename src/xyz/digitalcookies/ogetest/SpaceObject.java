package xyz.digitalcookies.ogetest;

import xyz.digitalcookies.objective.entity.Entity;

public abstract class SpaceObject implements Entity
{
	@Override
	public boolean utilizesBody()
	{
		return true;
	}
	
	public abstract SpaceBody getBody();
}
