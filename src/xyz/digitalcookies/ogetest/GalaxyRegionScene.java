package xyz.digitalcookies.ogetest;

import xyz.digitalcookies.objective.scene.EntityContainer;
import xyz.digitalcookies.objective.scene.SceneUpdateEvent;
import xyz.digitalcookies.objective.scene.Scene;
import xyz.digitalcookies.objective.graphics.RenderEvent;
import xyz.digitalcookies.objective.graphics.Renderer;

/** Main scene type, multiple of these scenes could be active at once and
 * up to 2 could be rendering at once (interwoven rendering.)
 * @author Bryan Charles Bettis
 */
public class GalaxyRegionScene extends Scene implements Renderer
{
	public GalaxyRegionScene()
	{
	}
	
	@Override
	public void updateScene(SceneUpdateEvent event)
	{
		if (!isPaused())
		{
//			int ox = -(int) (player.getUnit().getBody().getScreenX()-getOffsetX()-GraphicsManager.getMainLayerSet().getLayerSetWidth()/2);
//			int oy = -(int) (GraphicsManager.getMainLayerSet().getLayerSetHeight()/2-getOffsetY()-player.getUnit().getBody().getScreenY());
//			setOffset(ox, oy);
		}
	}
	
	@Override
	public void render(RenderEvent event)
	{
		if (isRendering())
		{
		}
	}
}
