package xyz.digitalcookies.spacegame1;

import xyz.digitalcookies.objective.EngineSetupData;
import xyz.digitalcookies.objective.GameSession;
import xyz.digitalcookies.spacegame1.gamestate.MainMenu;

@SuppressWarnings("javadoc")
public class Main
{
	public static final HullResources HULL_RESOURCES = new HullResources();
	
	public static void main(String[] args)
	{
		EngineSetupData.setCodeSource(
				Main.class.getProtectionDomain().getCodeSource().getLocation()
				);
		GameSession.setup(MainMenu.class);
		HULL_RESOURCES.initialize(HullResources.DATA_LOCATION, ".txt");
		GameSession.start();
	}
}
