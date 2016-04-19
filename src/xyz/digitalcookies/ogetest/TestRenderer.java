package xyz.digitalcookies.ogetest;

import java.awt.Color;

import xyz.digitalcookies.objective.graphics.BoundedRenderer;
import xyz.digitalcookies.objective.graphics.GraphicsManager;
import xyz.digitalcookies.objective.graphics.RenderEvent;

/** Just a simple implementation of Renderer that renders some useful
 * screen artifacts for testing alignment.
 * @author Bryan Charles Bettis
 *
 */
public class TestRenderer extends BoundedRenderer
{
	public TestRenderer()
	{
//		setEnforceBounds(true);
		setPos(20,20);
		setDims(500,500);
	}
	
	@Override
	public void render(RenderEvent event)
	{
		super.render(event);
		event.getContext().setColor(Color.blue);
		event.getContext().drawRect(0,
				0,
				GraphicsManager.getMainLayerSet().getWidth()-1,
				GraphicsManager.getMainLayerSet().getHeight()-1
				);
		event.getContext().fillOval(0, 0, 10, 10);
		event.getContext().fillOval(
				GraphicsManager.getMainLayerSet().getWidth()-10,
				GraphicsManager.getMainLayerSet().getHeight()-10,
				10,
				10
				);
		event.getContext().drawLine(
				0,
				0,
				GraphicsManager.getMainLayerSet().getWidth(),
				GraphicsManager.getMainLayerSet().getHeight()
				);
		event.getContext().drawLine(
				0,
				GraphicsManager.getMainLayerSet().getHeight(),
				GraphicsManager.getMainLayerSet().getWidth(),
				0
				);
		event.getContext().drawLine(
				GraphicsManager.getMainLayerSet().getWidth()/2,
				0,
				GraphicsManager.getMainLayerSet().getWidth()/2,
				GraphicsManager.getMainLayerSet().getHeight()
				);
		event.getContext().drawLine(
				0,
				GraphicsManager.getMainLayerSet().getHeight()/2,
				GraphicsManager.getMainLayerSet().getWidth(),
				GraphicsManager.getMainLayerSet().getHeight()/2
				);
	}
}
