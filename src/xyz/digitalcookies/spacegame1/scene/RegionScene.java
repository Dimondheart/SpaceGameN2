package xyz.digitalcookies.spacegame1.scene;

import java.util.LinkedList;
import java.util.List;

import xyz.digitalcookies.objective.scene.SceneUpdateEvent;
import xyz.digitalcookies.objective.scene.EntityContainer;
import xyz.digitalcookies.objective.scene.EntityUpdateEvent;
import xyz.digitalcookies.objective.scene.Scene;
import xyz.digitalcookies.objective.graphics.GraphicsManager;
import xyz.digitalcookies.objective.graphics.ImageDrawer;
import xyz.digitalcookies.objective.graphics.RenderEvent;
import xyz.digitalcookies.objective.input.Mouse;
import xyz.digitalcookies.spacegame1.spaceentity.SpaceEntity;
import xyz.digitalcookies.spacegame1.spaceentity.SpaceObject;

/** Scene representing a generated region in the game.
 * @author Bryan Charles Bettis
 */
public class RegionScene extends Scene
{
	public static final String REP_CAMERA = "camera";
	public static final String UEP_ADD_SPACE_ENTITY = "newSE";
	public static final String UEP_ELAPSED = "uelapsed";
	public static final String UEP_ENTITIES = "IEntities";
	
	private EntityContainer<SpaceEntity> entities;
	private RegionCamera camera;
	
	public RegionScene(RegionData data)
	{
		camera = new RegionCamera();
		entities = new EntityContainer<SpaceEntity>();
	}
	
	@Override
	protected void updateScene(SceneUpdateEvent event)
	{
		EntityUpdateEvent eue = new EntityUpdateEvent();
		eue.setProperty(UEP_ELAPSED, event.getProperty(Scene.UPDATE_ELAPSED));
		eue.setProperty(UEP_ENTITIES, entities.getEntities());
		eue.setProperty(UEP_ADD_SPACE_ENTITY, new LinkedList<SpaceObject>());
		entities.updateEntities(eue);
		entities.addEntities(((List<SpaceEntity>) eue.getProperty(UEP_ADD_SPACE_ENTITY)));
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
		// Add the camera to the event
		event.setProperty(RegionScene.REP_CAMERA, camera);
		entities.render(event);
	}
}
