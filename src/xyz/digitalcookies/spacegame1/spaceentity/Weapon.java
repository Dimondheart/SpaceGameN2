package xyz.digitalcookies.spacegame1.spaceentity;

import java.util.HashMap;

import xyz.digitalcookies.objective.scene.EntityUpdateEvent;
import xyz.digitalcookies.spacegame1.HullData.WeaponLinkProperty;

/** A module that can be attached to a sentient space object to allow
 * it to have an effect (usually harmful) to a space object in its
 * surroundings.
 * @author Bryan Charles Bettis
 */
public abstract class Weapon extends SpacecraftModule
{
	private boolean isActive;
	private HashMap<WeaponLinkProperty,Object> link;
	
	public Weapon(HashMap<WeaponLinkProperty,Object> link)
	{
		setActive(false);
		this.link = link;
	}
	
	@Override
	public void update(EntityUpdateEvent event)
	{
		super.update(event);
	}
	
	public boolean isActive()
	{
		return isActive;
	}
	
	public void setActive(boolean active)
	{
		isActive = active;
	}
	
	public HashMap<WeaponLinkProperty,Object> getLink()
	{
		return link;
	}
}
