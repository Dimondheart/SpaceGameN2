package xyz.digitalcookies.ogetest;

import xyz.digitalcookies.objective.GameSession;
import xyz.digitalcookies.objective.utility.SetupOperations;

@SuppressWarnings("javadoc")
public class Main
{
	public static void main(String[] args)
	{
		SetupOperations.setCodeSource(Main.class.getProtectionDomain().getCodeSource().getLocation());
		GameSession session = new GameSession(MainPlayMode.class);
		session.start();
	}
}
