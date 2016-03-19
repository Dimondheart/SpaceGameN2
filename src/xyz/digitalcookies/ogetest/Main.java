package xyz.digitalcookies.ogetest;

import java.net.URI;
import xyz.digitalcookies.objective.GameSession;
import xyz.digitalcookies.objective.resources.ResourcePackManager;
import xyz.digitalcookies.objective.utility.SetupOperations;

@SuppressWarnings("javadoc")
public class Main
{
	public static void main(String[] args)
	{
		URI codeSource = null;
		try
		{
			codeSource = Main.class.getProtectionDomain().getCodeSource()
					.getLocation().toURI();
		}
		catch (java.net.URISyntaxException e)
		{
			System.out.println("ERROR GETTING CODE SOURCE LOCATION");
			System.exit(0);
		}
		SetupOperations.setResDir(codeSource, "resources");
		ResourcePackManager.setBufferResources(true);
		GameSession session = new GameSession(
				MainPlayMode.class,
				SetupOperations.getOJGEProperties(codeSource, "ojge.properties")
				);
		session.start();
	}
}
