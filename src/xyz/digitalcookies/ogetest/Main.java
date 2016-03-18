package xyz.digitalcookies.ogetest;

import java.io.IOException;
import java.net.URI;
import java.util.Properties;

import xyz.digitalcookies.objective.GameSession;
import xyz.digitalcookies.objective.resources.ResourcePackManager;

@SuppressWarnings("javadoc")
public class Main
{
	public static void main(String[] args)
	{
		URI resPackDir = URI.create("<TODO make this part findable>/ObjectiveTesting/resources");
		ResourcePackManager.indexResourcePacks(resPackDir);
		ResourcePackManager.setBufferResources(true);
		GameSession session = new GameSession(
				MainMenu.class,
				getOJGEProperties("ojge.properties")
				);
		session.start();
	}
	
	/** Get the properties file for the game engine to use. Calls
	 * System.exit(0) if the properties failed to load (to prevent
	 * this issue from only showing up in the game engine classes.)
	 * @param file relative path to the file
	 * @return the Properties object for the game engine to use
	 */
	private static Properties getOJGEProperties(String file)
	{
		Main.class.getClassLoader();
		Properties ojgeProps = new Properties();
		try
		{
			ojgeProps.load(ClassLoader.getSystemResource("ojge.properties").openStream());
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		return ojgeProps;
	}
}
