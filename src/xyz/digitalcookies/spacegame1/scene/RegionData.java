package xyz.digitalcookies.spacegame1.scene;

/** Contains all objects and data needed by a scene
 * to generate an individual scene.
 * @author Bryan Charles Bettis
 *
 */
public class RegionData
{
	private int resources;
	
	public RegionData()
	{
		setResAmt(0);
	}
	
	public int getResAmt()
	{
		return resources;
	}
	
	public void setResAmt(int amount)
	{
		if (amount > 0)
		{
			resources = amount;
		}
		else
		{
			resources = 0;
		}
	}
}
