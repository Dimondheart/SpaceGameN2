package xyz.digitalcookies.spacegame1;

import xyz.digitalcookies.objective.scene.SceneUpdateEvent;
import xyz.digitalcookies.objective.scene.Entity;
import xyz.digitalcookies.objective.scene.EntityContainer;
import xyz.digitalcookies.objective.scene.EntityUpdateEvent;
import xyz.digitalcookies.objective.scene.Scene;

import java.awt.Color;
import java.awt.Graphics2D;

import xyz.digitalcookies.objective.graphics.GraphicsManager;
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
	private Circle targetPos;
	private double[][] targets = 
		{
			{600, -600},
			{850, -240},
			{1000, -800}
		};
	private int currTarget = 0;
	private double lastUpdate = 0;
	private PlaneVector camera;
	
	public GalaxyRegionScene()
	{
		camera = new PlaneVector();
		objects = new EntityContainer();
		objects.addEntity(new Spaceship());
		objects.addEntity(new Spaceship());
		((Spaceship) objects.getEntities().get(0))
			.getBody().getRegion().getPosition().setVectorComp(1300, -1300);
		((Spaceship) objects.getEntities().get(0))
			.getBody().getRegion().setRadius(12);
		((Spaceship) objects.getEntities().get(0)).getBody().getVelocity().setVector(60, 45);
		((Spaceship) objects.getEntities().get(1))
		.getBody().getRegion().getPosition().setVectorComp(1300, -1300);
	((Spaceship) objects.getEntities().get(1))
		.getBody().getRegion().setRadius(12);
		((Spaceship) objects.getEntities().get(1)).getBody().getVelocity().setVector(-12, 5);
//		((Spaceship) objects.getEntities().get(0)).getBody().getDirection().rotateDegrees(45);
		targetPos = new Circle(targets[currTarget][0], targets[currTarget][1], 24);
		lastUpdate = getTimer().getTimeSec();
		camera = ((Spaceship) objects.getEntities().get(0)).getBody().getRegion().getPosition();
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
			eue.setProperty(SpaceObject.EVENT_OTHER_ENTITIES, objects);
			((SpaceObject) objects.getEntities().get(0)).getBody().getVelocity().rotateDegrees(64*elapsed);
			objects.updateEntities(eue);
			objects.getEntities().forEach(
					(Entity e)->
					{
						((SpaceObject) e).updatePhysics(elapsed);
					}
					);
		}
		lastUpdate = currTime;
	}
	
	@Override
	public void render(RenderEvent event)
	{
		if (isRendering())
		{
			event.getContext().translate(
					(int) (-camera.getX()+GraphicsManager.getMainLayerSet().getWidth()/2),
					(int) (camera.getY()+GraphicsManager.getMainLayerSet().getHeight()/2)
					);
			GraphicsManager.drawGraphic(
					event.getContext(),
					"RegionBG/bg1.jpg",
					0,
					0
					);
			objects.getEntities().forEach(
					(Entity e)->
					{
						SpaceObject o = (SpaceObject) e;
						RenderEvent event2 = event.clone();
						event2.getContext().translate(
								(int) (o.getBody().getRegion().getX()-camera.getX()),
								(int) (-o.getBody().getRegion().getY()+camera.getY())
								);
						o.render(event2);
					}
					);
		}
	}
}
