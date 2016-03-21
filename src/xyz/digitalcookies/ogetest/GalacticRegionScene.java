package xyz.digitalcookies.ogetest;

import xyz.digitalcookies.objective.scene.EntityContainer;
import xyz.digitalcookies.objective.scene.EntityUpdateEvent;
import xyz.digitalcookies.objective.scene.SceneUpdateEvent;
import xyz.digitalcookies.objective.scene.Scene;
import xyz.digitalcookies.objective.graphics.GraphicsManager;
import xyz.digitalcookies.objective.graphics.RenderEvent;
import xyz.digitalcookies.objective.graphics.Renderer;

/** A scene for a region in the galaxy map. */
public class GalacticRegionScene extends Scene implements Renderer
{
	private GalacticRegionData regionData;
	private EntityContainer entities;
	private Player player;
	
	public GalacticRegionScene(GalacticRegionData data)
	{
		regionData = data;
		entities = new EntityContainer();
		ShipData enemyDestroyers =  new ShipData(ShipData.TEST_DESTROYER_ID);
		entities.addEntity(new Ship(this, enemyDestroyers));
		entities.addEntity(new Asteroid(this));
	}
	
	@Override
	public void updateScene(SceneUpdateEvent event)
	{
		if (!isPaused())
		{
			player.update(new EntityUpdateEvent(entities));
			entities.updateEntities(new EntityUpdateEvent(entities));
			int ox = -(int) (player.getUnit().getBody().getScreenX()-getOffsetX()-GraphicsManager.getMainLayerSet().getLayerSetWidth()/2);
			int oy = -(int) (GraphicsManager.getMainLayerSet().getLayerSetHeight()/2-getOffsetY()-player.getUnit().getBody().getScreenY());
			setOffset(ox, oy);
		}
	}
	
	@Override
	public void render(RenderEvent event)
	{
		if (isRendering())
		{
			entities.render(event);
		}
	}
	
	/** Set the player character. */
	public void setPlayer(Player player)
	{
		this.player = player;
		entities.addEntity(player.getUnit());
	}
	
	public void removePlayer()
	{
		player = null;
	}
}
