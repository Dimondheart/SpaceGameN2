package xyz.digitalcookies.spacegame1.scene;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import xyz.digitalcookies.objective.scene.SceneUpdateEvent;
import xyz.digitalcookies.objective.scene.EntityContainer;
import xyz.digitalcookies.objective.scene.EntityUpdateEvent;
import xyz.digitalcookies.objective.scene.Scene;
import xyz.digitalcookies.objective.graphics.GraphicsManager;
import xyz.digitalcookies.objective.graphics.ImageDrawer;
import xyz.digitalcookies.objective.graphics.RenderEvent;
import xyz.digitalcookies.objective.input.Keyboard;
import xyz.digitalcookies.objective.input.Mouse;
import xyz.digitalcookies.spacegame1.Main;
import xyz.digitalcookies.spacegame1.spaceentity.PlayerSteering;
import xyz.digitalcookies.spacegame1.spaceentity.SpaceEntity;
import xyz.digitalcookies.spacegame1.spaceentity.SpaceObject;
import xyz.digitalcookies.spacegame1.spaceentity.Spaceship;

//import static java.awt.event.MouseEvent.*;
import static java.awt.event.KeyEvent.*;

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
	private Spaceship playerControlled;
	private ArrayList<Spaceship> playerShips;
	
	public RegionScene(RegionData data)
	{
		camera = new RegionCamera();
		entities = new EntityContainer<SpaceEntity>();
		playerShips = new ArrayList<Spaceship>();
		Spaceship s =
				new Spaceship(Main.HULL_RESOURCES.getRes("smalldrone/testdrone.txt"));
		Spaceship s2 =
				new Spaceship(Main.HULL_RESOURCES.getRes("largedrone/testdrone2.txt"));
		s2.getBody().getPos().setVectorComp(200, 0);
		Spaceship s3 =
				new Spaceship(Main.HULL_RESOURCES.getRes("size3/tests3.txt"));
		s3.getBody().getPos().setVectorComp(550, 0);
		Spaceship s4 =
				new Spaceship(Main.HULL_RESOURCES.getRes("size4/tests4.txt"));
		s4.getBody().getPos().setVectorComp(1200, 0);
		Spaceship s5 =
				new Spaceship(Main.HULL_RESOURCES.getRes("size5/tests5.txt"));
		s5.getBody().getPos().setVectorComp(2450, 0);
		Spaceship cap =
				new Spaceship(Main.HULL_RESOURCES.getRes("capitalship/testcapital.txt"));
		cap.getBody().getPos().setVectorComp(8100, 0);
		Spaceship cap2 =
				new Spaceship(Main.HULL_RESOURCES.getRes("capitalship/testenemycap.txt"));
		cap2.getBody().getPos().setVectorComp(22550, 0);
		entities.addEntities(cap2, cap, s5, s4, s3, s2, s);
		playerShips.add(s);
		playerShips.add(s2);
		playerShips.add(s3);
		playerShips.add(s4);
		playerShips.add(s5);
		setPlayerControlled(s);
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
		int playerCtrl = -1;
		if (Keyboard.justPressed(VK_1))
		{
			playerCtrl = 0;
		}
		else if (Keyboard.justPressed(VK_2))
		{
			playerCtrl = 1;
		}
		else if (Keyboard.justPressed(VK_3))
		{
			playerCtrl = 2;
		}
		else if (Keyboard.justPressed(VK_4))
		{
			playerCtrl = 3;
		}
		else if (Keyboard.justPressed(VK_5))
		{
			playerCtrl = 4;
		}
		if (playerCtrl >= 0 && playerCtrl < playerShips.size())
		{
			setPlayerControlled(playerShips.get(playerCtrl));
		}
	}
	
	@Override
	public void renderScene(RenderEvent event)
	{
		ImageDrawer.drawGraphic(
				event.getGC(),
				"RegionBG/white_star_field_1.jpg",
				0,
				0,
				GraphicsManager.getMainLayerSet().getWidth(),
				GraphicsManager.getMainLayerSet().getHeight()
				);
		// Add the camera to the event
		event.setProperty(RegionScene.REP_CAMERA, camera);
		int x =
				camera.getX(0);
		int y = 
				camera.getY(0);
		BufferedImage img =
				GraphicsManager.getResManager().getRes("moon.png");
		double w = 1736482*camera.getScale();
		double h = 1736482*camera.getScale();
		ImageDrawer.drawGraphic(
				event.getGC(),
				img,
				x-(int)(w/2),
				y-(int)(h/2),
				(int) w,
				(int) h
				);
		entities.render(event);
	}
	
	private void setPlayerControlled(Spaceship ship)
	{
		// Ship already set
		if (ship == playerControlled)
		{
			return;
		}
		// Remove the player from the previously controlled ship
		try
		{
			playerControlled.removePlayer();
		}
		catch (NullPointerException e)
		{
		}
		// Set the new controlled ship
		playerControlled = ship;
		// Update the camera & ship steering
		if (ship == null)
		{
			camera.follow(null);
		}
		else
		{
			ship.setSteering(new PlayerSteering());
			camera.follow(ship);
		}
	}
}
