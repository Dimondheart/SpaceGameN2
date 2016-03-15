package xyz.digitalcookies.ogetest;

import java.awt.Color;

import xyz.digitalcookies.objective.entity.Entity;
import xyz.digitalcookies.objective.entity.EntityUpdateEvent;
import xyz.digitalcookies.objective.graphics.RenderEvent;
import xyz.digitalcookies.objective.graphics.TextDrawer;

public class Ship extends SpaceObject
{
	public boolean testU = false;
	private ShipBody body;
	private ShipData baseData;
	private Color color;
	private Laser laser;
	
	public Ship(GalacticRegionScene scene, ShipData data)
	{
		body = new ShipBody(scene);
		baseData = data;
		switch (data.getShipID())
		{
			case ShipData.DESTROYER_ID:
				body.setRadius(32);
				body.setMaxSpeed(24);
				color = Color.lightGray;
				break;
			case ShipData.FIGHTER_ID:
			default:
				body.setRadius(16);
				body.setMaxSpeed(48);
				color = Color.blue;
				break;
		}
		laser = new Laser(this);
	}
	
	@Override
	public void update(EntityUpdateEvent event)
	{
		super.update(event);
		for(Entity entity : event.getEntities().getEntities())
		{
			if (entity == this)
			{
				continue;
			}
			if (entity instanceof Ship)
			{
				laser.fire();
				Ship ship = (Ship) entity;
				if (ship.getShipData().getShipID().equals("Fighter"))
				{
					double dx = ship.getBody().getX()-getBody().getX();
					double dy = ship.getBody().getY()-getBody().getY();
					
					getBody().getAccel().setVector(getBody().distanceTo(ship.getBody(), true), getBody().directionTo(ship.getBody()));
				}
			}
		}
	}

	@Override
	public void render(RenderEvent event)
	{
		int x = (int) getBody().getScreenX();
		int y = (int) getBody().getScreenY();
		int r = (int) getBody().getScreenRadius();
		event.getContext().setColor(color);
		event.getContext().fillOval(x-r, y-r, r*2, r*2);
		laser.render(event);
		TextDrawer.drawText(
				event.getContext(),
				"("+Integer.toString((int)getBody().getX())+","+Integer.toString((int)getBody().getY())+")",
				x,
				y-23
				);
//		System.out.println(
//		"vm:"
//		+ String.format("%.4f", body.getVector().getMagnitude())
//		+ ", vd:"
//		+ String.format("%.4f", body.getVector().getDirection())
//		);
		//System.out.println("Rotation:" + String.format("%.1f", body.getRotation()));
		event.getContext().setColor(Color.red);
		int ax = (int) (x + getBody().getAccel().getCompX());
		int ay = (int) (y - getBody().getAccel().getCompY());
		event.getContext().drawLine(x, y, ax, ay);
		event.getContext().setColor(Color.green);
		int vx = (int) (x + getBody().getVector().getCompX());
		int vy = (int) (y - getBody().getVector().getCompY());
		event.getContext().drawLine(x, y, vx, vy);
		event.getContext().setColor(Color.white);
		int rx = (int) (x + Math.cos(getBody().getRotation()*Math.PI/180.0)*10);
		int ry = (int) (y - Math.sin(getBody().getRotation()*Math.PI/180.0)*10);
		event.getContext().drawLine(x, y, rx, ry);
	}

	@Override
	public SpaceBody getBody()
	{
		return body;
	}
	
	public ShipData getShipData()
	{
		return baseData;
	}
}
