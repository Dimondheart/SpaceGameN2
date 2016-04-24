package xyz.digitalcookies.spacegame1.scene;

import java.util.ArrayList;
import java.util.List;

import xyz.digitalcookies.objective.scene.EntityUpdateEvent;

/** Represents a scene object is non-ambient, meaning it is affected by
 * and can make decisions based on environmental factors (including other
 * space objects.) An example of a sentient space object would be a space
 * station or a space ship.
 * @author Bryan Charles Bettis
 */
public abstract class SentientSpaceObject extends SpaceObject
{
	private ArrayList<SentientObjectModule> modules;
	
	public SentientSpaceObject()
	{
		modules = new ArrayList<SentientObjectModule>();
	}
	
	@Override
	public void update(EntityUpdateEvent event)
	{
		super.update(event);
		// Add this object to the event for modules to access
		event.setProperty(SentientObjectModule.EVENT_PARENT, this);
		// Update the modules
		for (SentientObjectModule module : modules)
		{
			module.update(event);
		}
		
	}
	
	public List<SentientObjectModule> getModules()
	{
		return modules;
	}
}
