package xyz.digitalcookies.spacegame1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import xyz.digitalcookies.objective.resources.ResourceHandler;
import xyz.digitalcookies.objective.resources.ResourcePackManager;

public class HullResources extends ResourceHandler<HullData>
{
	public static final String DATA_LOCATION = "hulls";
	public static final String GRAPHICS_LOCATION = "hulls";
	
	public HullResources()
	{
		setSupportsBuffering(true);
	}
	
	@Override
	protected HullData loadResource(File load)
	{
		HullData data = new HullData();
		// Read the data lines from the file
		List<String> lines = null;
		try
		{
			lines = Files.readAllLines(load.toPath());
		}
		catch (IOException e)
		{
			System.out.println("Unable to load hull data from" + load.getPath());
			return data;
		}
		// Path is used to find the tier and graphics resources
		String path = load.getPath().replace(
				ResourcePackManager.getResPackDir(),
				""
				);
		path = path.substring(1);
		path = path.substring(path.indexOf(File.separator));
		path = path.replace(File.separator + "hulls" + File.separator, "");
		data.setup(lines, path);
		data.printDebug();
		return data;
	}
	
	@Override
	protected HullData getDefaultValue()
	{
		return new HullData();
	}
}
