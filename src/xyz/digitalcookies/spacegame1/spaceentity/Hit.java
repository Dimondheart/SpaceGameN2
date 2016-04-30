package xyz.digitalcookies.spacegame1.spaceentity;

public class Hit
{
	public static final String DT_KINETIC = "Kinetic";
	public static final String DT_PHOTON = "Photon";
	public static final String DT_CORROSIVE = "Corrosive";
	public static final String DT_EXPLOSIVE = "Explosive";
	public static final String DT_ION = "Ion";
	
	private double hp;
	private double hi;
	private final String[] types;
	
	public Hit(double hp, double hi, String... types)
	{
		setHPDamage(hp);
		setHIDamage(hi);
		this.types = types;
	}
	
	public double getHPDamage()
	{
		return hp;
	}
	
	public void setHPDamage(double damage)
	{
		hp = damage;
	}
	
	public double getHIDamage()
	{
		return hi;
	}
	
	public void setHIDamage(double damage)
	{
		hi = damage;
	}
	
	public String[] getAllDT()
	{
		return types.clone();
	}
	
	public boolean hasDT(String type)
	{
		for (String t : types)
		{
			if (type.equals(t))
			{
				return true;
			}
		}
		return false;
	}
}
