package xyz.digitalcookies.spacegame1.scene;

import xyz.digitalcookies.objective.scene.SceneUpdateEvent;
import xyz.digitalcookies.objective.scene.Entity;
import xyz.digitalcookies.objective.scene.EntityContainer;
import xyz.digitalcookies.objective.scene.EntityUpdateEvent;
import xyz.digitalcookies.objective.scene.Scene;

import java.util.LinkedList;
import java.util.List;

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
	public static final String EVENT_CAMERA = "camera";
	public static final String EVENT_NEW_OBJECTS = "newObj";
	
	private EntityContainer objects;
	private int currTarget = 0;
	private RegionCamera camera;
	private double scale = 1.0;
	private Spaceship playerShip;
	private EntityContainer spaceDust;
	
	public GalaxyRegionScene(GalaxyRegionData data)
	{
		camera = new RegionCamera();
		objects = new EntityContainer();
		spaceDust = new EntityContainer();
		playerShip = new Spaceship();
		camera.follow(playerShip);
		objects.addEntity(new SpaceStation());
		objects.addEntity(new Asteroid(data.getResAmt(), 1600, 0));
		objects.addEntity(playerShip);
		objects.addEntity(new Spaceship());
		objects.addEntity(new Spaceship(300, -20));
//		objects.setCycleRemoveIf(
//				(Entity e)->
//				{
//					return 
//						(
//							e instanceof SpaceObject &&
//							((SpaceObject) e).isDestroyed()
//						);
//				}
//				);
	}
	
	@Override
	protected void updateScene(SceneUpdateEvent event)
	{
		EntityUpdateEvent eue = new EntityUpdateEvent();
		eue.setProperty(SpaceObject.EVENT_ELAPSED, event.getProperty(Scene.UPDATE_ELAPSED));
		eue.setProperty(SpaceObject.EVENT_ENTITIES, objects.getEntities());
		eue.setProperty(SpaceObject.EVENT_PLAYER_CTRL, playerShip);
		eue.setProperty(EVENT_NEW_OBJECTS, new LinkedList<SpaceObject>());
		objects.updateEntities(eue);
		// After primary updates, update the physics of each object
		objects.getEntities((Entity e)->{return e instanceof SpaceObject;}).forEach(
				(Entity e)->
				{
					((SpaceObject) e).updatePhysics(
							(double) event.getProperty(Scene.UPDATE_ELAPSED)
							);
				}
				);
		objects.addEntities(((List<Entity>) eue.getProperty(EVENT_NEW_OBJECTS)));
		for (Entity e : objects.getEntities((Entity e)->{return e instanceof SpaceObject;}))
		{
			if (((SpaceObject) e).isDestroyed())
			{
				objects.removeEntity(e);
			}
		}
		if (Mouse.getWheelChange() > 0)
		{
			camera.zoomIn();
		}
		else if (Mouse.getWheelChange() < 0)
		{
			camera.zoomOut();
		}
	}
	
	@Override
	public void renderScene(RenderEvent event)
	{
		ImageDrawer.drawGraphic(
				event.getContext(),
				"RegionBG/white_star_field_1.jpg",
				0,
				0,
				GraphicsManager.getMainLayerSet().getWidth(),
				GraphicsManager.getMainLayerSet().getHeight()
				);
//		AffineTransform origAT = event.getContext().getTransform();
		// Center over the camera
//		event.getContext().translate(
//				(GraphicsManager.getMainLayerSet().getWidth()/2-camera.getX()*scale),
//				(GraphicsManager.getMainLayerSet().getHeight()/2+camera.getY()*scale)
//				);
//		event.getContext().scale(scale, scale);
		// Add the camera to the event
		event.setProperty(GalaxyRegionScene.EVENT_CAMERA, camera);
		objects.render(event);
//		event.getContext().setTransform(origAT);
	}
}
