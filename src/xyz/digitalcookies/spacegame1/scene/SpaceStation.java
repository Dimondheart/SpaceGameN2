package xyz.digitalcookies.spacegame1.scene;

/** Cannot be directly controlled by a player. A spacecraft or artificial
 * structure with minimal or no means of direct propulsion.
 * @author Bryan Charles Bettis
 */
public class SpaceStation extends SentientSpaceObject
{
	public SpaceStation()
	{
		this(0, 0);
	}
	
	public SpaceStation(double x, double y)
	{
		getBody().getRegion().setRadius(800);
		getBody().getRegion().getPosition().setVectorComp(x, y);
	}
	
	@Override
	public String getMainImage()
	{
		return "starbase-tex.png";
	}
}
