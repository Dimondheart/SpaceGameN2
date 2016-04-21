package xyz.digitalcookies.spacegame1;

import xyz.digitalcookies.objective.scene.Entity;
import xyz.digitalcookies.objective.scene.EntityUpdateEvent;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;

import xyz.digitalcookies.objective.graphics.GraphicsManager;
import xyz.digitalcookies.objective.graphics.ImageDrawer;
import xyz.digitalcookies.objective.graphics.RenderEvent;
import xyz.digitalcookies.objective.input.Keyboard;
import xyz.digitalcookies.objective.input.Mouse;

import static java.awt.event.KeyEvent.*;
//import static java.awt.event.MouseEvent.*;

/** A unit that can be controlled directly by the player (or an AI.)
 * A spacecraft that is pilotable.
 * @author Bryan Charles Bettis
 */
public class Spaceship extends SentientSpaceObject
{
	public Spaceship()
	{
		this(0, 0);
	}
	
	public Spaceship(double x, double y)
	{
		getBody().getRegion().setRadius(70);
		getBody().getRegion().getPosition().setVectorComp(x, y);
	}
	
	@Override
	public void update(EntityUpdateEvent event)
	{
		super.update(event);
		// Player controlled ship update
		if (event.getProperty(SpaceObject.EVENT_PLAYER_CTRL) == this)
		{
			updatePlayer(event);
		}
		// Non-player-controlled ship update
		else
		{
			updateNPC(event);
		}
		// Clip velocity to max
		if (getBody().getVelocity().getMagnitude() > 400)
		{
			getBody().getVelocity().setMagnitude(400);
		}
	}
	
	@Override
	public void render(RenderEvent event)
	{
		super.render(event);
		AffineTransform orig = event.getContext().getTransform();
		event.getContext().rotate(-getBody().getDirection().getDirectionRad(), 0, 0);
		BufferedImage img =
				GraphicsManager.getResManager().getRes("blueships1.png");
		event.getContext().scale(
				getBody().getRegion().getRadius()
					*2
					/img.getWidth(),
				getBody().getRegion().getRadius()
					*2
					/img.getWidth()
				);
		ImageDrawer.drawGraphic(
				event.getContext(),
				img,
				-img.getWidth()/2,
				-img.getHeight()/2
				);
		event.getContext().setTransform(orig);
	}
	
	protected void updatePlayer(EntityUpdateEvent event)
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
		if (Keyboard.isDown(VK_X) && getBody().getVelocity().getMagnitude() != 0)
		{
			PlaneVector reqVector = getBody().getVelocity().getUnitVector();
			reqVector.setMagnitude(-100);
			getBody().getSteering().addVector(reqVector);
		}
		// If net steering force applied by keys is > 0, increase magnitude
		if (steering.getMagnitude() > 0)
		{
			steering.setMagnitude(100);
		}
		// Rotate steering to be relative to the direction of the ship
		steering.rotateDegrees(getBody().getDirection().getDirectionDeg());
		// Calculate the vector from center screen to mouse cursor
		if (Keyboard.isDown(VK_SHIFT))
		{
			getBody().setRotation(0);
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
			updateRotation((int) toMouse.getDirectionDeg());
		}
		// Apply the player-controlled steering forces
		getBody().getSteering().addVector(steering);
	}
	
	/** Update this ship a a non-player controlled ship.
	 * @param event the original entity update event passed in to the
	 * 		update method
	 */
	protected void updateNPC(EntityUpdateEvent event)
	{
		updateRotation((int) getBody().getVelocity().getDirectionDeg());
		// Get the list of entities that can possibly be interracted with
		List<Entity> entities = 
				(List<Entity>)
				event.getProperty(SpaceObject.EVENT_ENTITIES);
		// Address each entity we can interact with
		for (Entity e : entities)
		{
			if (e instanceof SpaceObject)
			{
				if (e instanceof Spaceship)
				{
					if (e == this)
					{
						continue;
					}
					Spaceship other = (Spaceship) e;
					PlaneVector oPos =
							other.getBody().getRegion().getPosition();
					PlaneVector toShip = new PlaneVector(
							oPos.getX()
								- getBody().getRegion()
									.getPosition().getX(),
							oPos.getY()
								- getBody().getRegion()
									.getPosition().getY(),
							PlaneVector.AS_XY_COMP
							);
					if (
							getBody().getRegion().intersects(
									other.getBody().getRegion()
									)
						)
					{
						toShip.setMagnitude(-80);
					}
					else if (
							other ==
							event.getProperty(
									SpaceObject.EVENT_PLAYER_CTRL
									)
							)
					{
						toShip.setMagnitude(toShip.getMagnitude()/10.0);
					}
					else
					{
						toShip.setMagnitude(0);
					}
					getBody().getSteering().addVector(toShip);
				}
			}
		}
	}
	
	/** Update the rotation 'vector' (how many degrees to rotate
	 * per second.)
	 * @param target the desired direction to face in degrees
	 */
	protected void updateRotation(int target)
	{
		int rotation = 
				(int)
				(
					target
					- getBody().getDirection().getDirectionDeg()
				);
		// Rotate CCW
		if ((0 < rotation && rotation < 180) || (rotation < -180))
		{
			getBody().setRotation(60);
		}
		// Rotate CW
		else if ((-180 < rotation && rotation < 0) || (rotation >= 180))
		{
			getBody().setRotation(-60);
		}
		// No rotation
		else
		{
			getBody().setRotation(0);
		}
	}
}
