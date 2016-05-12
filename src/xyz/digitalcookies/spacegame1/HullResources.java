package xyz.digitalcookies.spacegame1;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import xyz.digitalcookies.objective.resources.ResourceHandler;
import xyz.digitalcookies.objective.resources.ResourceManager;

public class HullResources extends ResourceHandler<HullData>
{
	public static final String DATA_LOCATION = "hulls";
	public static final String GRAPHICS_LOCATION = "hulls";
	
	public HullResources()
	{
		setSupportsBuffering(true);
	}
	
	@Override
	protected HullData loadResource(InputStream load)
	{
		HullData data = new HullData();
		// Read the data lines from the file
		List<String> lines = null;
		Scanner root = new Scanner(load);
		Scanner s = root.useDelimiter("\\A");
		String text = s.hasNext() ? s.next() : "";
		lines = Arrays.asList(text.split("\\n"));
		data.setup(lines);
		System.out.println(data.toString());
		root.close();
		s.close();
		return data;
	}
	
	@Override
	protected HullData getDefaultValue()
	{
		return new HullData();
	}
}
