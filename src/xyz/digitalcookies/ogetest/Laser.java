package xyz.digitalcookies.ogetest;

import java.awt.Color;

import xyz.digitalcookies.objective.entity.EntityUpdateEvent;
import xyz.digitalcookies.objective.graphics.RenderEvent;

public class Laser extends Weapon
{
	public Laser(Ship ship)
	{
		super(ship);
	}
	
	@Override
	public void update(EntityUpdateEvent event)
	{
		
	}

	@Override
	public boolean utilizesBody()
	{
		return false;
	}

	@Override
	public void render(RenderEvent event)
	{
		int x = (int) (getShip().getBody().getScreenX()-getShip().getBody().getScreenRadius()/4);
		int y = (int) (getShip().getBody().getScreenY()-getShip().getBody().getScreenRadius()/4);
		event.getContext().setColor(Color.white);
		event.getContext().fillOval(
				x,
				y,
				(int) getShip().getBody().getScreenRadius()/2,
				(int) getShip().getBody().getScreenRadius()/2
				);
	}

	@Override
	public boolean fire()
	{
		return false;
	}

}
