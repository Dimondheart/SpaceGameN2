package xyz.digitalcookies.spacegame1.scene;

public class GalaxyRegionData
{
	private int resources;
	
	public GalaxyRegionData()
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
