package xyz.digitalcookies.ogetest;

import xyz.digitalcookies.objective.scene.Entity;
import xyz.digitalcookies.objective.scene.EntityUpdateEvent;
import xyz.digitalcookies.objective.input.InputManager;

import static java.awt.event.KeyEvent.*;
//import static java.awt.event.MouseEvent.*;

/** The player of the game. */
public class Player implements Entity
{
	private Ship mainUnit;
	private boolean linLatControl;
	
	public Player(GalacticRegionScene scene)
	{
		mainUnit = new Ship(scene, new ShipData(ShipData.TEST_FIGHTER_ID));
		linLatControl = true;
		mainUnit.testU = true;
	}

	@Override
	public void update(EntityUpdateEvent event)
	{
		if (InputManager.getMS().getWheelChange() != 0)
		{
			mainUnit.getBody().getScene().zoom(InputManager.getMS().getWheelChange());
		}
		if (InputManager.getKB().isDown(VK_1))
		{
			mainUnit.getBody().setRotationVector(0);
			linLatControl = true;
		}
		else if (InputManager.getKB().isDown(VK_2))
		{
			linLatControl = false;
		}
		if (linLatControl)
		{
			double rotVect = 0;
			double dTheta = calcAngleMousePlayer();
			if (dTheta > 0)
			{
				if (dTheta > 180)
				{
					rotVect = 60;
				}
				else
				{
					rotVect = -60;
				}
			}
			else if (dTheta < 0)
			{
				if (dTheta < -180)
				{
					rotVect = -60;
				}
				else
				{
					rotVect = 60;
				}
			}
			mainUnit.getBody().setRotationVector(rotVect);
			double linear = 0;
			double lateral = 0;
			if (InputManager.getKB().isDown(VK_A))
			{
				lateral -= 1;
			}
			if (InputManager.getKB().isDown(VK_D))
			{
				lateral += 1;
			}
			if (InputManager.getKB().isDown(VK_W))
			{
				linear += 1;
			}
			if (InputManager.getKB().isDown(VK_S))
			{
				linear -= 1;
			}
			if (linear != 0 || lateral != 0)
			{
				double offset = 0;
				mainUnit.getBody().getAccel().setMagnitude(10);
				if (linear < 0)
				{
					if (lateral < 0)
					{
						offset = 135;
					}
					else if (lateral == 0)
					{
						offset = 180;
					}
					else
					{
						offset = -135;
					}
				}
				else if (linear == 0)
				{
					if (lateral < 0)
					{
						offset = 90;
					}
					else if (lateral == 0)
					{
						offset = 0;
					}
					else
					{
						offset = -90;
					}
				}
				else
				{
					if (lateral < 0)
					{
						offset = 45;
					}
					else if (lateral == 0)
					{
						offset = 0;
					}
					else
					{
						offset = -45;
					}
				}
				mainUnit.getBody().getAccel().setDirection(offset + mainUnit.getBody().getRotation());
			}
			else
			{
				mainUnit.getBody().getAccel().setMagnitude(0);
			}
		}
		else
		{
			int rotation = 0;
			int vm = 0;
			if (InputManager.getKB().isDown(VK_A))
			{
				rotation += 1;
			}
			if (InputManager.getKB().isDown(VK_D))
			{
				rotation -= 1;
			}
			if (InputManager.getKB().isDown(VK_W))
			{
				vm += 1;
			}
			if (InputManager.getKB().isDown(VK_S))
			{
				vm -= 1;
			}
			mainUnit.getBody().setRotationVector(rotation*36);
			mainUnit.getBody().applyAccel(vm*10);
		}
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
	
	protected double calcAngleMousePlayer()
	{
		double cx = InputManager.getMS().getUnpolledX()-mainUnit.getBody().getScreenX();
		double cy = -(InputManager.getMS().getUnpolledY()-mainUnit.getBody().getScreenY());
		// Handle the special angles that could result in invalid calcs
		if (cx == 0)
		{
			if (cy > 0)
			{
				return 90;
			}
			else if (cy < 0)
			{
				return 270;
			}
			return 0;
		}
		else if (cy == 0)
		{
			if (cx >= 0)
			{
				return 0;
			}
			else if (cx < 0)
			{
				return 180;
			}
			return 0;
		}
		double thetaMouse = 0;
		thetaMouse = radToDeg(Math.atan(Math.abs(cy/cx)));
		if (cx > 0 && cy > 0)
		{
			// do nothing
		}
		else if (cx < 0 && cy > 0)
		{
			thetaMouse = 180 - thetaMouse;
		}
		else if (cx < 0 && cy < 0)
		{
			thetaMouse = 180 + thetaMouse;
		}
		else if (cx > 0 && cy < 0)
		{
			thetaMouse = 360 - thetaMouse;
		}
		return mainUnit.getBody().getRotation() - thetaMouse;
		
	}
	
	protected double radToDeg(double rad)
	{
		return rad * 180.0 / Math.PI;
	}
}
