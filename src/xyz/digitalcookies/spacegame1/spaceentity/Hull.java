package xyz.digitalcookies.spacegame1.spaceentity;

import xyz.digitalcookies.spacegame1.HullData;

public class Hull extends SpaceBody
{
	private final HullData hullData;
	private SpacecraftModule[] modules;
	private double hi;
	
	public Hull(HullData hullData)
	{
		this.hullData = hullData;
		modules = new SpacecraftModule[hullData.getModuleSpace()];
		setHI(hullData.getMaxHI());
		setHP(getHI());
		getRegion().setRadius(hullData.getRadius());
	}
	
	@Override
	public void setHP(double hp)
	{
		// Hit points cannot exceed current hull integrity
		if (hp > getHI())
		{
			super.setHP(getHI());
		}
		else
		{
			super.setHP(hp);
		}
	}
	
	public HullData getHullData()
	{
		return hullData;
	}
	
	public double getHI()
	{
		return hi;
	}
	
	public void setHI(double hi)
	{
		if (hi > getHullData().getMaxHI())
		{
			this.hi = getHullData().getMaxHI();
		}
		else
		{
			this.hi = hi;
		}
	}
	
	public boolean applyHit(Hit hit)
	{
		setHI(getHI() - hit.getHIDamage());
		setHP(getHP() - hit.getHPDamage());
		System.out.println("~~Hit Applied~~");
		System.out.println("MaxHP: " + String.format("%.2f", getHullData().getMaxHI()));
		System.out.println("HI: " + String.format("%.2f", getHI()));
		System.out.println("HP: " + String.format("%.2f", getHP()));
		return true;
	}
}
