package xyz.digitalcookies.spacegame1;

import java.awt.Color;
import java.awt.Font;

import xyz.digitalcookies.objective.graphics.BoundedRenderer;
import xyz.digitalcookies.objective.graphics.RenderEvent;
import xyz.digitalcookies.objective.graphics.TextDrawer;

public class TitleRenderer extends BoundedRenderer
{
	private String toDraw = "Space Game";
	
	public TitleRenderer()
	{
		setPos(0,0);
		setDims(100,100);
	}
	
	@Override
	public void render(RenderEvent event)
	{
//		super.render(event);
		event.getGC().setColor(Color.white);
		Font font = TextDrawer.getDefaultFont().deriveFont(32.0f);
		int[] center = new int[2];
		center[0] = getX() + getWidth()/2;
		center[1] = getY() + getHeight()/2;
		TextDrawer.drawText(event.getGC(),
				toDraw,
				TextDrawer.centerOverPoint(
						event.getGC(),
						toDraw,
						center,
						font
						),
				font
				);
	}
}
