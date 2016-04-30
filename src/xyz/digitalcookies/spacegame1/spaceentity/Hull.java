package xyz.digitalcookies.spacegame1.spaceentity;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Hull extends SpaceBody
{
	private ArrayList<SpacecraftModule> modules;
	private double maxHI;
	private double hi;
	
	public Hull()
	{
		modules = new ArrayList<SpacecraftModule>();
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
	
	/** The maximum hull integrity value. */
	public double getMaxHI()
	{
		return maxHI;
	}
	
	/** Change the maximum hull integrity value of this hull. */
	public void setMaxHI(double maxHI)
	{
		this.maxHI = maxHI;
		// Hull integrity should not exceed max
		if (maxHI < getHI())
		{
			setHI(getMaxHI());
		}
	}
	
	public double getHI()
	{
		return hi;
	}
	
	public void setHI(double hi)
	{
		if (hi > getMaxHI())
		{
			this.hi = getMaxHI();
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
		System.out.println("MaxHP: " + String.format("%.2f", getMaxHI()));
		System.out.println("HI: " + String.format("%.2f", getHI()));
		System.out.println("HP: " + String.format("%.2f", getHP()));
		return true;
	}
}
