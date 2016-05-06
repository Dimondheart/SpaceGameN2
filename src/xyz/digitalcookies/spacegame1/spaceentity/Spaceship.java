package xyz.digitalcookies.spacegame1.spaceentity;

import xyz.digitalcookies.objective.scene.EntityUpdateEvent;
import xyz.digitalcookies.spacegame1.HullData;

/** A unit that can be controlled directly by the player (or an AI.)
 * A spacecraft that is pilotable.
 * @author Bryan Charles Bettis
 */
public class Spaceship extends Spacecraft
{
	private IntelligentSteering normalSteering;
	
	public Spaceship(HullData hullData)
	{
		super(hullData);
		// Set default steering controller
		setSteering(new AIShipSteering());
	}

	@Override
	public void update(EntityUpdateEvent event)
	{
		super.update(event);
	}
	
	@Override
	public void setSteering(IntelligentSteering steering)
	{
		// Current steering is non-player and new steering is a player
		if (
			!(getSteering() instanceof PlayerSteering)
			&& steering instanceof PlayerSteering
		)
		{
			// Save the current steering to revert back to later
			normalSteering = getSteering();
		}
		super.setSteering(steering);
	}
	
	/** Removes the current steering (only if it is a player steering)
	 * and replaces it with the most recent non-player steering (none if there
	 * is no previously set non-player steering.)
	 */
	public void removePlayer()
	{
		if (getSteering() instanceof PlayerSteering)
		{
			setSteering(normalSteering);
		}
	}
}
