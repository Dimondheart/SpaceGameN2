package xyz.digitalcookies.ogetest;

import java.awt.Color;

import xyz.digitalcookies.objective.entity.EntityUpdateEvent;
import xyz.digitalcookies.objective.graphics.RenderEvent;
import xyz.digitalcookies.objective.graphics.TextDrawer;

public class Ship extends SpaceObject
{
	private ShipBody body;
	
	public Ship(GalacticRegionScene scene)
	{
		body = new ShipBody(scene);
	}
	
	@Override
	public void update(EntityUpdateEvent event)
	{
	}

	@Override
	public void render(RenderEvent event)
	{
		int x = (int) getBody().getScreenX();
		int y = (int) getBody().getScreenY();
		event.getContext().setColor(Color.cyan);
		event.getContext().fillOval(x-6, y-6, 12, 12);
		TextDrawer.drawText(
				event.getContext(),
				"("+Integer.toString((int)getBody().getX())+","+Integer.toString((int)getBody().getY())+")",
				x,
				y-23
				);
	}

	@Override
	public SpaceBody getBody()
	{
		return body;
	}
}
