package xyz.digitalcookies.ogetest;

import xyz.digitalcookies.objective.entity.Entity;
import xyz.digitalcookies.objective.entity.EntityUpdateEvent;
import xyz.digitalcookies.objective.input.InputManager;

import static java.awt.event.KeyEvent.*;
//import static java.awt.event.MouseEvent.*;

/** The player of the game. */
public class Player implements Entity
{
	private Ship mainUnit;
	
	public Player(GalacticRegionScene scene)
	{
		mainUnit = new Ship(scene);
	}

	@Override
	public void update(EntityUpdateEvent event)
	{
		int rotation = 0;
		int vm = 0;
//		if (InputManager.getKB().isDown(VK_A))
//		{
//			rotation -= 1;
//		}
//		if (InputManager.getKB().isDown(VK_D))
//		{
//			rotation += 1;
//		}
//		if (InputManager.getKB().isDown(VK_W))
//		{
//			vm += 1;
//		}
//		if (InputManager.getKB().isDown(VK_S))
//		{
//			vm -= 1;
//		}
		if (InputManager.getKB().justReleased(VK_A))
		{
			rotation -= 1;
		}
		if (InputManager.getKB().justReleased(VK_D))
		{
			rotation += 1;
		}
		if (InputManager.getKB().justReleased(VK_W))
		{
			vm += 1;
		}
		if (InputManager.getKB().justReleased(VK_S))
		{
			vm -= 1;
		}
//		mainUnit.getBody().setRotationVector(rotation);
		mainUnit.getBody().setPos(mainUnit.getBody().getX() + rotation, mainUnit.getBody().getY() + vm);
		mainUnit.getBody().applyVector(vm);
	}

	@Override
	public boolean utilizesBody()
	{
		return false;
	}
	
	public Ship getUnit()
	{
		return mainUnit;
	}
}
