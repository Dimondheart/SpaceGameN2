package xyz.digitalcookies.ogetest;

import xyz.digitalcookies.objective.GameSession;

@SuppressWarnings("javadoc")
public class Main
{
	public static void main(String[] args)
	{
		GameSession session = new GameSession(MainMenu.class);
		session.start();
	}
}
