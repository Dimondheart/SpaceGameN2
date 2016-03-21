package xyz.digitalcookies.ogetest;

import xyz.digitalcookies.objective.EngineSetupData;
import xyz.digitalcookies.objective.GameSession;

@SuppressWarnings("javadoc")
public class Main
{
	public static void main(String[] args)
	{
		EngineSetupData.setCodeSource(
				Main.class.getProtectionDomain().getCodeSource().getLocation()
				);
		GameSession.setup(MainPlayMode.class);
		GameSession.start();
	}
}
