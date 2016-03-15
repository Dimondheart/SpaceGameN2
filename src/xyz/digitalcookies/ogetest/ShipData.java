package xyz.digitalcookies.ogetest;

public class ShipData
{
	public static final String FIGHTER_ID = "Fighter";
	public static final String DESTROYER_ID = "Destroyer";
	private String shipID = "Fighter";
	public ShipData()
	{
		
	}
	
	public String getShipID()
	{
		return shipID;
	}
	
	public void setShipID(String id)
	{
		shipID = id;
	}
}
