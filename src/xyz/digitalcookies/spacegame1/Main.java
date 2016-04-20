package xyz.digitalcookies.spacegame1;

import xyz.digitalcookies.objective.EngineSetupData;
import xyz.digitalcookies.objective.GameSession;
import xyz.digitalcookies.spacegame1.gamestate.MainMenu;

@SuppressWarnings("javadoc")
public class Main
{
	public static void main(String[] args)
	{
		EngineSetupData.setCodeSource(
				Main.class.getProtectionDomain().getCodeSource().getLocation()
				);
		GameSession.setup(MainMenu.class);
		GameSession.start();
	}
}
