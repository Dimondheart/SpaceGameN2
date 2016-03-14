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
	private int offsetX;
	private int offsetY;
	
	public GalacticRegionScene()
	{
		entities = new EntityContainer();
		entities.addEntity(new Ship(this));
		setOffset(0, 0);
	}
	
	public void changeRegionData(GalacticRegionData data)
	{
		newSceneData = data;
	}
	
	public double getOffsetX()
	{
		return offsetX;
	}
	
	public double getOffsetY()
	{
		return offsetY;
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
			int ox = -(int) (player.getUnit().getBody().getX()-GraphicsManager.getMainLayerSet().getLayerSetWidth()/2);
			int oy = -(int) (player.getUnit().getBody().getY()-GraphicsManager.getMainLayerSet().getLayerSetHeight()/2);
			setOffset(ox, oy);
			player.update(new EntityUpdateEvent(entities));
			entities.updateEntities(new EntityUpdateEvent(entities));
		}
	}
	
	/** Set the player character and add their units to this scene. */
	public void setPlayer(Player player)
	{
		this.player = player;
		entities.addEntity(player.getUnit());
	}
	
	public void removePlayer()
	{
		player = null;
	}

	@Override
	public void render(RenderEvent event)
	{
		if (isActive())
		{
			entities.render(event);
		}
	}
	
	private void setOffset(int ox, int oy)
	{
		offsetX = ox;
		offsetY = oy;
	}
}
