package xyz.digitalcookies.ogetest;

import xyz.digitalcookies.objective.entity.EntityContainer;
import xyz.digitalcookies.objective.entity.EntityUpdateEvent;
import xyz.digitalcookies.objective.entity.SceneUpdateEvent;
import xyz.digitalcookies.objective.graphics.GraphicsManager;
import xyz.digitalcookies.objective.graphics.RenderEvent;
import xyz.digitalcookies.objective.graphics.Renderer;

/** A single region in the galactic map. */
public class GalacticRegionScene extends xyz.digitalcookies.objective.entity.Scene implements Renderer
{
	private GalacticRegionData currSceneData;
	private GalacticRegionData newSceneData;
	private EntityContainer entities;
	private Player player;
	
	public GalacticRegionScene()
	{
		entities = new EntityContainer();
		ShipData enemyDestroyers =  new ShipData();
		enemyDestroyers.setShipID("Destroyer");
		entities.addEntity(new Ship(this, enemyDestroyers));
		entities.addEntity(new Asteroid(this));
	}
	
	@Override
	public void updateScene(SceneUpdateEvent event)
	{
		if (isActive())
		{
			if (newSceneData != null)
			{
				currSceneData = newSceneData;
				newSceneData = null;
			}
			int ox = -(int) (player.getUnit().getBody().getScreenX()-getOffsetX()-GraphicsManager.getMainLayerSet().getLayerSetWidth()/2);
			int oy = -(int) (GraphicsManager.getMainLayerSet().getLayerSetHeight()/2-getOffsetY()-player.getUnit().getBody().getScreenY());
			setOffset(ox, oy);
			player.update(new EntityUpdateEvent(entities));
			entities.updateEntities(new EntityUpdateEvent(entities));
		}
	}
	
	@Override
	public void render(RenderEvent event)
	{
		if (isActive())
		{
			entities.render(event);
		}
	}
	
	/** Set the player character and add their units to this scene. */
	public void setPlayer(Player player)
	{
		this.player = player;
		entities.addEntity(player.getUnit());
	}
	
	public void changeRegionData(GalacticRegionData data)
	{
		newSceneData = data;
	}
	
	public void removePlayer()
	{
		player = null;
	}
}
