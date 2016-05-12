package xyz.digitalcookies.spacegame1;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** Contains all necessary data for generating a bare-bones Hull.
 * @author Bryan Charles Bettis
 */
public class HullData
{
	/** Name used to refer to the hull in-game. */
	private String commonName;
	/** Name of the graphics folder containing the graphics for this
	 * hull.
	 */
	private String resName;
	/** The general description of this hull. */
	private String desc;
	/** The hull tier of this hull. */
	private HullTier tier;
	/** The maximum hull integrity. */
	private int maxHI;
	/** Space for non-weapon modules. */
	private int moduleSpace;
	/** The radius of this hull (bounds most of hull regardless of angle). */
	private double radius;
	/** The points where weapons can be attached to the hull. */
	private ArrayList<HashMap<WeaponLinkProperty,Object>> weaponLinks;
	/** Used for setting sub-properties of certain properties, such as
	 * weapon links.
	 */
	private Object settingPropsOf;
	
	/** The tier of a hull, meaning its classification.
	 * @author Bryan Charles Bettis
	 */
	public enum HullTier
	{
		SMALL_DRONE("smalldrone","small drone"),
		LARGE_DRONE("largedrone","large drone"),
		SIZE_3("size3","size three"),
		SIZE_4("size4","size four"),
		SIZE_5("size5","size five"),
		CAPITAL_SHIP("capitalship","capital ship"),
		STATION("station","station");
		
		private String resPath;
		private String commonName;
		
		private HullTier(String resPath, String commonName)
		{
			this.resPath = resPath;
			this.commonName = commonName;
		}
		
		public String getResDir()
		{
			return resPath;
		}
		
		public String getCommonName()
		{
			return commonName;
		}
	}
	
	/** Properties of a weapon link point, which is a spot on a hull where
	 * a weapon can be mounted.
	 * @author Bryan Charles Bettis
	 */
	public enum WeaponLinkProperty
	{
		X,
		Y,
		IS_FIXED;
	}
	
	/** Sets all properties to default values. */
	public HullData()
	{
		setCommonName("Unnamed Hull");
		setResName("testship");
		setDescription("A hull without a specified description.");
		setTier(HullTier.SMALL_DRONE);
		setMaxHI(1);
		setModuleSpace(0);
		setRadius(64);
		weaponLinks = new ArrayList<HashMap<WeaponLinkProperty,Object>>();
	}
	
	/** Get the name that this hull will be called in-game.
	 * @return the name of this hull as shown to the player
	 */
	public String getCommonName()
	{
		return commonName;
	}
	
	/** Get the name of the folder containing the resources for this
	 * hull.
	 * @return the name of the resource folder for the resources (graphics
	 * 		mainly) of this hull
	 */
	public String getResName()
	{
		return resName;
	}
	
	/** Get the relative path to the resources of this hull.
	 * @return the path to the resources for this hull within graphics/hulls/
	 */
	public String getResPath()
	{
		return getTier().getResDir() + "/" + getResName();
	}
	
	public String getDescription()
	{
		return desc;
	}
	
	public HullTier getTier()
	{
		return tier;
	}
	
	/** Get the max HI of this hull.
	 * @return the maximum/base HI of this hull
	 */
	public int getMaxHI()
	{
		return maxHI;
	}
	
	/** Get how many modules (other than weapons) that this hull can
	 * hold.
	 * @return the maximum number of modules this hull can carry
	 */
	public int getModuleSpace()
	{
		return moduleSpace;
	}
	
	public double getRadius()
	{
		return radius;
	}
	
	public ArrayList<HashMap<WeaponLinkProperty,Object>> getWeaponLinks()
	{
		return weaponLinks;
	}
	
	/** Setup hull data from the specified datablock lines and path indicating
	 * the tier of the hull.
	 * @param lines the lines from the hull data file
	 */
	public void setup(List<String> lines)
	{
		for (String raw : lines)
		{
			// Remove padding whitespace
			String line = raw.trim();
			// Comment line
			if (line.contains("#!!"))
			{
				continue;
			}
			// The tier and resource name indicator
			else if (line.startsWith("resource_name="))
			{
				String resPathText = line.replace("resource_name=", "");
				// Set the tier of the hull
				for (HullTier tier : HullTier.values())
				{
					if (resPathText.contains(tier.getResDir()))
					{
						setTier(tier);
						break;
					}
				}
				// Set the name of the resource folder
				int start = resPathText.indexOf("/");
				// Leave as default if there was an issue excluding the folder
				if (start >= 0)
				{
					// Exclude the file extension
					int end = resPathText.indexOf(".");
					// Doesn't have a file extension; go to end of string
					if (end < 0)
					{
						end = resPathText.length();
					}
					setResName(resPathText.substring(start+1, end));
				}
			}
			else if (line.startsWith("common_name="))
			{
				setCommonName(line.replace("common_name=", ""));
			}
			else if (line.startsWith("description="))
			{
				setDescription(line.replace("description=", ""));
			}
			else if (line.startsWith("max_hi="))
			{
				setMaxHI(Integer.parseInt(line.replace("max_hi=", "")));
			}
			// Space for non-weapon modules
			else if (line.startsWith("module_space="))
			{
				setModuleSpace(Integer.parseInt(line.replace("module_space=", "")));
			}
			else if (line.startsWith("radius="))
			{
				setRadius(Double.parseDouble(line.replace("radius=", "")));
			}
			// Number of weapon link points on this ship
			else if (line.startsWith("weapon_link="))
			{
				weaponLinks.add(newWeaponLink());
				settingPropsOf = weaponLinks;
				System.out.println("new wl");
			}
			// Setting a sub-property of a hull property
			else if (line.startsWith("."))
			{
				// Setting a sub-property of a weapon link
				if (settingPropsOf == weaponLinks)
				{
					HashMap<WeaponLinkProperty, Object> currLink =
							weaponLinks.get(weaponLinks.size()-1);
					String val = line.substring(line.indexOf("=")+1);
					if (line.startsWith(".x="))
					{
						currLink.put(
								WeaponLinkProperty.X,
								Double.parseDouble(val)
								);
					}
					else if (line.startsWith(".y="))
					{
						currLink.put(
								WeaponLinkProperty.Y,
								Double.parseDouble(val)
								);
					}
					else if (line.startsWith(".is_fixed="))
					{
						currLink.put(
								WeaponLinkProperty.IS_FIXED,
								Boolean.parseBoolean(val)
								);
					}
				}
			}
		}
	}
	
	/** Print all properties of this hull to the system output stream. */
	@Override
	public String toString()
	{
		return "Hull Data:["
		+ "Common Name:" + getCommonName()
		+ ", Tier:" + getTier()
		+ ", Resource Name:" + getResName()
		+ ", Max HI:" + getMaxHI()
		+ ", Module Space:" + getModuleSpace()
		+ ", Description:" + getDescription()
		+ "]";
	}
	
	/** Set the common name of this hull (the name players will be shown.)
	 * @param name the hull's common name as it will appear displayed
	 * 		in-game
	 */
	protected void setCommonName(String name)
	{
		if (name != null)
		{
			commonName = name;
		}
	}
	
	protected void setDescription(String desc)
	{
		if (desc != null)
		{
			this.desc = desc;
		}
	}
	
	/** Set the tier of this hull.
	 * @param tier the tier (currently the name of the directory containing
	 * 		the hull data file
	 */
	protected void setTier(HullTier tier)
	{
		if (tier != null)
		{
			this.tier = tier;
		}
	}
	
	protected void setMaxHI(int max)
	{
		maxHI = max;
	}
	
	protected void setResName(String name)
	{
		if (name != null)
		{
			resName = name;
		}
	}
	
	/** Set the amount of module space on this hull.
	 * @param space the number of spaces for modules on this hull
	 */
	protected void setModuleSpace(int space)
	{
		moduleSpace = space;
	}
	
	protected void setRadius(double radius)
	{
		this.radius = radius;
	}
	
	private HashMap<WeaponLinkProperty, Object> newWeaponLink()
	{
		HashMap<WeaponLinkProperty, Object> hm =
				new HashMap<WeaponLinkProperty, Object>();
		// Set default values
		hm.put(WeaponLinkProperty.X, 0);
		hm.put(WeaponLinkProperty.Y, 0);
		hm.put(WeaponLinkProperty.IS_FIXED, true);
		return hm;
	}
}
