package xyz.digitalcookies.spacegame1.spaceentity;

import static java.awt.event.KeyEvent.*;
import static java.awt.event.MouseEvent.*;

import xyz.digitalcookies.objective.graphics.GraphicsManager;
import xyz.digitalcookies.objective.input.Keyboard;
import xyz.digitalcookies.objective.input.Mouse;
import xyz.digitalcookies.spacegame1.PlaneVector;

public class PlayerSteering implements IntelligentSteering
{
	@Override
	public void applySteering(SpaceObject obj)
	{
		PlaneVector steering = new PlaneVector();
		// Apply steering forward
		if (Keyboard.isDown(VK_W))
		{
			steering.addVector(1, 0);
		}
		// Apply steering in reverse
		if (Keyboard.isDown(VK_S))
		{
			steering.addVector(-1, 0);
		}
		// Apply steering to the left
		if (Keyboard.isDown(VK_A))
		{
			steering.addVector(0, 1);
		}
		// Apply steering to the right
		if (Keyboard.isDown(VK_D))
		{
			steering.addVector(0, -1);
		}
		// Apply steering to slow down the ship
		if (Keyboard.isDown(VK_X) && obj.getBody().getVelocity().getMagnitude() != 0)
		{
			PlaneVector reqVector = obj.getBody().getVelocity().getUnitVector();
			reqVector.setMagnitude(-obj.getBody().getVelocity().getMagnitude()*2);
			obj.getBody().getSteering().addVector(reqVector);
		}
		// If net steering force applied by keys is > 0, increase magnitude
		if (steering.getMagnitude() > 0)
		{
			steering.setMagnitude(1000);
		}
		// Rotate steering to be relative to the direction of the ship
		steering.rotateDegrees(obj.getBody().getDirection().getDirectionDeg());
		// Calculate the vector from center screen to mouse cursor
		if (Keyboard.isDown(VK_SHIFT))
		{
			obj.getBody().setRotation(0);
		}
		else
		{
			PlaneVector toMouse = new PlaneVector();
			toMouse.setVectorComp(Mouse.getUnpolledX(), -Mouse.getUnpolledY());
			toMouse.addVector(
					-GraphicsManager.getMainLayerSet().getWidth()/2,
					GraphicsManager.getMainLayerSet().getHeight()/2
					);
			// Calculate angle from current ship direction to mouse
			int rotation = 
					(int)
					(
						(int) toMouse.getDirectionDeg()
						- obj.getBody().getDirection().getDirectionDeg()
					);
			// Rotate CCW
			if ((0 < rotation && rotation < 180) || (rotation < -180))
			{
				obj.getBody().setRotation(60);
			}
			// Rotate CW
			else if ((-180 < rotation && rotation < 0) || (rotation >= 180))
			{
				obj.getBody().setRotation(-60);
			}
			// No rotation
			else
			{
				obj.getBody().setRotation(0);
			}
		}
		// Apply the player-controlled steering forces
		obj.getBody().getSteering().addVector(steering);
		// Activate/deactivate weapons
		for (Weapon w : ((Spacecraft) obj).getHull().getWeapons())
		{
			if (w == null)
			{
				continue;
			}
			w.setActive(Mouse.isDown(BUTTON1));
		}
	}
}
