package xyz.digitalcookies.spacegame1;

import xyz.digitalcookies.objective.scene.SceneUpdateEvent;
import xyz.digitalcookies.objective.scene.Entity;
import xyz.digitalcookies.objective.scene.EntityContainer;
import xyz.digitalcookies.objective.scene.EntityUpdateEvent;
import xyz.digitalcookies.objective.scene.Scene;

import java.awt.Color;
import java.awt.geom.AffineTransform;

import xyz.digitalcookies.objective.graphics.GraphicsManager;
import xyz.digitalcookies.objective.graphics.ImageDrawer;
import xyz.digitalcookies.objective.graphics.RenderEvent;
import xyz.digitalcookies.objective.graphics.Renderer;
import xyz.digitalcookies.objective.input.Mouse;

/** Main scene type, multiple of these scenes could be active at once and
 * up to 2 could be rendering at once (interwoven rendering.)
 * @author Bryan Charles Bettis
 */
public class GalaxyRegionScene extends Scene implements Renderer
{
	private EntityContainer objects;
	private int currTarget = 0;
	private double lastUpdate = 0;
	private PlaneVector camera;
	private double scale = 1.0;
	private Spaceship playerShip;
	
	public GalaxyRegionScene()
	{
		camera = new PlaneVector();
		objects = new EntityContainer();
		playerShip = new Spaceship();
		objects.addEntity(playerShip);
		objects.addEntity(new Spaceship());
		objects.addEntity(new Spaceship());
		playerShip
			.getBody().getRegion().getPosition().setVectorComp(0, 0);
		playerShip.getBody().getRegion().setRadius(70);
		((Spaceship) objects.getEntities().get(1))
			.getBody().getRegion().getPosition().setVectorComp(200, -120);
		((Spaceship) objects.getEntities().get(1))
			.getBody().getRegion().setRadius(70);
		((Spaceship) objects.getEntities().get(2))
			.getBody().getRegion().getPosition().setVectorComp(300, -20);
		((Spaceship) objects.getEntities().get(2))
			.getBody().getRegion().setRadius(70);
		lastUpdate = getTimer().getTimeSec();
		camera = playerShip.getBody().getRegion().getPosition();
	}
	
	@Override
	public void updateScene(SceneUpdateEvent event)
	{
		double currTime = getTimer().getTimeSec();
		double elapsed = currTime - lastUpdate;
		if (isUpdating())
		{
			EntityUpdateEvent eue = new EntityUpdateEvent();
			eue.setProperty(SpaceObject.EVENT_ELAPSED, elapsed);
			eue.setProperty(SpaceObject.EVENT_ENTITIES, objects.getEntities());
			eue.setProperty(SpaceObject.EVENT_PLAYER_CTRL, playerShip);
			objects.updateEntities(eue);
			// After primary updates, update the physics of each object
			objects.getEntities().forEach(
					(Entity e)->
					{
						((SpaceObject) e).updatePhysics(elapsed);
					}
					);
			if (Mouse.getWheelChange() > 0)
			{
				scale *= 0.95;
			}
			else if (Mouse.getWheelChange() < 0)
			{
				scale *= 1.05;
			}
			if (scale > 1.0)
			{
				scale = 1.0;
			}
		}
		lastUpdate = currTime;
	}
	
	@Override
	public void render(RenderEvent event)
	{
		if (isRendering())
		{
			ImageDrawer.drawGraphic(
					event.getContext(),
					"RegionBG/bg2.jpg",
					0,
					0,
					GraphicsManager.getMainLayerSet().getWidth(),
					GraphicsManager.getMainLayerSet().getHeight()
					);
			AffineTransform origAT = event.getContext().getTransform();
//			AffineTransform newAT = new AffineTransform(orig);
			// Center over the camera
			event.getContext().translate(
					(GraphicsManager.getMainLayerSet().getWidth()/2-camera.getX()*scale),
					(GraphicsManager.getMainLayerSet().getHeight()/2+camera.getY()*scale)
					);
			event.getContext().scale(scale, scale);
			renderGrid(event);
			objects.render(event);
			event.getContext().setTransform(origAT);
		}
	}
	
	private void renderGrid(RenderEvent event)
	{
		// Draw the x axis
		event.getContext().setColor(Color.white);
		event.getContext().fillRect(
				-100000,
				-1,
				200000,
				2
				);
		// Draw y axis
		event.getContext().fillRect(
				-1,
				-100000,
				2,
				200000
				);
	}
}
