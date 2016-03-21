package xyz.digitalcookies.ogetest;

import java.awt.Color;

import xyz.digitalcookies.objective.scene.Entity;
import xyz.digitalcookies.objective.scene.EntityUpdateEvent;
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
			case ShipData.TEST_DESTROYER_ID:
				body.setRadius(32);
				body.setMaxSpeed(24);
				color = Color.lightGray;
				break;
			case ShipData.TEST_FIGHTER_ID:
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
		// Render acceleration vector
		event.getContext().setColor(Color.red);
		int ax = (int) (x + getBody().getAccel().getCompX());
		int ay = (int) (y - getBody().getAccel().getCompY());
		event.getContext().drawLine(x, y, ax, ay);
		// Render velocity vector
		event.getContext().setColor(Color.green);
		int vx = (int) (x + getBody().getVelocity().getCompX());
		int vy = (int) (y - getBody().getVelocity().getCompY());
		event.getContext().drawLine(x, y, vx, vy);
		// Render direction facing
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
