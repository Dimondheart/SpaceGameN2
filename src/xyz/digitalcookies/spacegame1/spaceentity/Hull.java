package xyz.digitalcookies.spacegame1.spaceentity;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import xyz.digitalcookies.objective.graphics.GraphicsManager;
import xyz.digitalcookies.objective.graphics.ImageDrawer;
import xyz.digitalcookies.objective.graphics.RenderEvent;
import xyz.digitalcookies.objective.graphics.Renderer;
import xyz.digitalcookies.objective.scene.EntityUpdateEvent;
import xyz.digitalcookies.spacegame1.HullData;
import xyz.digitalcookies.spacegame1.HullData.WeaponLinkProperty;
import xyz.digitalcookies.spacegame1.scene.RegionCamera;
import xyz.digitalcookies.spacegame1.scene.RegionScene;

public class Hull extends SpaceBody implements Renderer
{
	private final HullData hullData;
	private SpacecraftModule[] modules;
	private double hi;
	private Weapon[] weapons;
	
	public Hull(HullData hullData)
	{
		this.hullData = hullData;
		modules = new SpacecraftModule[hullData.getModuleSpace()];
		weapons = new Weapon[hullData.getWeaponLinks().size()];
		for (int i = 0; i < weapons.length; ++i)
		{
			weapons[i] = new Railgun(hullData.getWeaponLinks().get(i));
		}
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
	
	public SpacecraftModule[] getModules()
	{
		return modules;
	}
	
	public Weapon[] getWeapons()
	{
		return weapons;
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
	
	@Override
	public void update(EntityUpdateEvent event)
	{
		super.update(event);
		for (SpacecraftModule m : getModules())
		{
			if (m == null)
			{
				continue;
			}
			m.update(event);
		}
		for (Weapon w : getWeapons())
		{
			if (w == null)
			{
				continue;
			}
			w.update(event);
		}
	}
	
	@Override
	public void render(RenderEvent event)
	{
		RegionCamera camera =
				(RegionCamera) event.getProperty(RegionScene.REP_CAMERA);
		BufferedImage img =
				GraphicsManager.getResManager().getRes(
						"hulls/" + getHullData().getResPath()+"/base.png"
						);
		double iw = getRegion().getRadius()*2.0*camera.getScale();
		double ih = img.getHeight()*iw/img.getWidth();
		ImageDrawer.drawGraphic(
				event.getGC(),
				img,
				-(int) (iw/2),
				-(int) (ih/2),
				(int) iw,
				(int) ih
				);
		event.getGC().setColor(Color.blue);
		// Draw rectangles to represent the weapon link points
		for (HashMap<WeaponLinkProperty,Object> link : getHullData().getWeaponLinks())
		{
			int w = (int) (40*camera.getScale());
			int h = (int) (10*camera.getScale());
			int x = (int)
					(
						-(iw/2)
						+((double) link.get(WeaponLinkProperty.X))*iw/img.getWidth()
						-w/2
					);
			int y = (int)
					(
						-(ih/2)
						+((double) link.get(WeaponLinkProperty.Y))*ih/img.getHeight()
						-h/2
					);
			event.getGC().fillRect(x, y, w, h);
		}
	}
}
