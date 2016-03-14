package xyz.digitalcookies.ogetest;

import java.util.concurrent.ConcurrentHashMap;

import xyz.digitalcookies.objective.gamestate.GameState;

//import static java.awt.event.KeyEvent.*;
//import static java.awt.event.MouseEvent.*;

/** The main menu. */
public class MainMenu extends GameState
{
	@Override
	public void setupState(ConcurrentHashMap<String, Object> args)
	{
	}

	@Override
	public void cycleState()
	{
		changeState(MainPlayMode.class);
	}

	@Override
	public void cleanupState()
	{
	}
}
