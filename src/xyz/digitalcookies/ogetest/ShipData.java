package xyz.digitalcookies.ogetest;

public class ShipData
{
	public static final String TINY_HULL = "Tiny";
	public static final String SMALL_HULL = "Small";
	public static final String MEDIUM_HULL = "Medium";
	public static final String LARGE_HULL = "Large";
	public static final String HUGE_HULL = "Huge";
	public static final String CAPITAL_HULL = "Capital";
	public static final String TEST_FIGHTER_ID = "Fighter";
	public static final String TEST_DESTROYER_ID = "Destroyer";
	private HullSize hullSize = HullSize.TINY;
	private String shipID = "Fighter";
	
	/** The possible hull sizes of ships.
	 * @author Bryan Charles Bettis
	 */
	public enum HullSize
	{
		TINY,
		SMALL,
		MEDIUM,
		LARGE,
		HUGE,
		CAPITAL
	}
	
	public ShipData(String shipType)
	{
		setShipType(shipType);
	}
	
	public String getShipID()
	{
		return shipID;
	}
	
	public HullSize getHullSize()
	{
		return hullSize;
	}
	
	private void setShipType(String type)
	{
		if (type == null)
		{
			System.out.println("WARNING: No ship type specified.");
		}
		shipID = type;
		switch (type)
		{
			case TEST_FIGHTER_ID:
				hullSize = HullSize.TINY;
				break;
			case TEST_DESTROYER_ID:
				hullSize = HullSize.MEDIUM;
				break;
			default:
				System.out.println("Unknown ship ID: " + type);
				shipID = TEST_FIGHTER_ID;
				hullSize = HullSize.TINY;
				break;
		}
	}
}
