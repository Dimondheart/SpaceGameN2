package xyz.digitalcookies.ogetest;

import java.awt.Color;

import xyz.digitalcookies.objective.graphics.GraphicsManager;
import xyz.digitalcookies.objective.graphics.RenderEvent;

public class Asteroid extends SpaceObject
{
	private SpaceBody body;
	
	public Asteroid(GalacticRegionScene scene)
	{
		body = new SpaceBody(scene);
	}
	
	@Override
	public void render(RenderEvent event)
	{
		int x = (int) (getBody().getScreenX()-getBody().getScreenRadius());
		int y = (int) (getBody().getScreenY()-getBody().getScreenRadius());
		int r = (int) getBody().getScreenRadius();
		GraphicsManager.drawGraphic(event.getContext(), "basic1.png", x-r, y-r);
	}

	@Override
	public SpaceBody getBody()
	{
		return body;
	}

}
