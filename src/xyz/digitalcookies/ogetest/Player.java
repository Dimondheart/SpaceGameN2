package xyz.digitalcookies.ogetest;

import xyz.digitalcookies.objective.scene.Entity;
import xyz.digitalcookies.objective.scene.EntityUpdateEvent;
import xyz.digitalcookies.objective.input.Keyboard;

import static java.awt.event.KeyEvent.*;
//import static java.awt.event.MouseEvent.*;

/** The player of the game. */
public class Player implements Entity
{
	@Override
	public void update(EntityUpdateEvent event)
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
//		mainUnit.getBody().setRotationVector(rotVect*2.8);
		int linear = 0;
		int lateral = 0;
		if (Keyboard.isDown(VK_A))
		{
			lateral -= 1;
		}
		if (Keyboard.isDown(VK_D))
		{
			lateral += 1;
		}
		if (Keyboard.isDown(VK_W))
		{
			linear += 1;
		}
		if (Keyboard.isDown(VK_S))
		{
			linear -= 1;
		}
		if (linear != 0 || lateral != 0)
		{
			double offset = 0;
//			mainUnit.getBody().getAccel().setMagnitude(42);
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
//			mainUnit.getBody().getAccel().setDirection(offset + mainUnit.getBody().getRotation());
		}
		else
		{
//			mainUnit.getBody().getAccel().setMagnitude(0);
		}
	}

	@Override
	public boolean utilizesBody()
	{
		return false;
	}
	
	protected double calcAngleMousePlayer()
	{
		double cx = 0;
		double cy = 0;
//		double cx = Mouse.getUnpolledX()-mainUnit.getBody().getScreenX();
//		double cy = -(Mouse.getUnpolledY()-mainUnit.getBody().getScreenY());
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
		thetaMouse =
				xyz.digitalcookies.objective
				.utility.ExtendedMath.radToDeg(Math.atan(Math.abs(cy/cx)));
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
		return 0;
//		return mainUnit.getBody().getRotation() - thetaMouse;
		
	}
}
