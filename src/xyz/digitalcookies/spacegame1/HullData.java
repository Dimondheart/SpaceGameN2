package xyz.digitalcookies.spacegame1;

import java.io.File;
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
	private double radius;
	
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
	
	/** Setup hull data from the specified datablock lines and path indicating
	 * the tier of the hull.
	 * @param lines the lines from the hull data file
	 * @param path the relative path of the data file; containing the hull tier
	 * 		folder and the data file
	 */
	public void setup(List<String> lines, String path)
	{
		// Set the tier of the hull
		for (HullTier tier : HullTier.values())
		{
			if (path.contains(tier.getResDir()))
			{
				setTier(tier);
			}
		}
		// Set the name of the resource folder
		int start = path.indexOf(File.separator);
		// Leave as default if there was an issue excluding the folder
		if (start >= 0)
		{
			// Exclude the file extension
			int end = path.indexOf(".");
			// Doesn't have a file extension; go to end of string
			if (end < 0)
			{
				end = path.length();
			}
			setResName(path.substring(start+1, end));
		}
		for (String raw : lines)
		{
			// Remove padding whitespace
			String line = raw.trim();
			// Comment line
			if (line.contains("#!!"))
			{
				continue;
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
			else if (line.startsWith("weapon_links="))
			{
			}
			// Weapon link point configuration
			else if (line.startsWith("weapon_links["))
			{
				int link = Integer.parseInt(
						line.substring(
								line.indexOf("[")+1,
								line.indexOf("]")
								)
						);
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
}
