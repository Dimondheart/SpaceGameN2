package xyz.digitalcookies.spacegame1.spaceentity;

import xyz.digitalcookies.spacegame1.HullData;

/** Cannot be directly controlled by a player. A spacecraft or artificial
 * structure with minimal or no means of direct propulsion.
 * @author Bryan Charles Bettis
 */
public class SpaceStation extends Spacecraft
{
	public SpaceStation(HullData hullData)
	{
		super(hullData);
	}
}
