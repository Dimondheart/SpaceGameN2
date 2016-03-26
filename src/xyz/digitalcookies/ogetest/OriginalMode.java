package xyz.digitalcookies.ogetest;

import java.util.concurrent.ConcurrentHashMap;

import xyz.digitalcookies.objective.graphics.GraphicsManager;
import xyz.digitalcookies.objective.graphics.RendererPanel.RelativePosition;
import xyz.digitalcookies.objective.input.Keyboard;
import xyz.digitalcookies.objective.input.gui.Button;
import xyz.digitalcookies.objective.input.gui.GUIPanel;
import xyz.digitalcookies.objective.sound.SoundManager;

import static java.awt.event.KeyEvent.*;
//import static java.awt.event.MouseEvent.*;

/** The "classic"/original play mode. */
public class OriginalMode extends xyz.digitalcookies.objective.gamestate.GameState
{
	/** The pause menu; pauses the game and shows a special GUI panel to
	 * the player.
	 */
	private GUIPanel pauseMenu;
	/** The settings menu for changing gameplay and general settings. */
	private GUIPanel settingsMenu;
	
	@Override
	protected void setupState(ConcurrentHashMap<String, Object> setupArgs)
	{
		// Basic setup stuff
		SoundManager.playBGM("Into_the_Unknown.wav", SoundManager.BGMTransition.IMMEDIATE);
		// Make instances
		pauseMenu = new GUIPanel(0,0);
		settingsMenu = new GUIPanel(0,0);
		// Add to secondary containers
		pauseMenu.addRenderer(new Button(0,0,100,25,"Quit to Main Menu"), "mainMenu");
		pauseMenu.addRenderer(new Button(0,0,100,25,"Settings"), "settings", RelativePosition.ABOVE, "mainMenu");
		pauseMenu.addRenderer(new Button(0,0,100,25,"Quit"), "quit", RelativePosition.BELOW, "mainMenu");
		settingsMenu.addRenderer(new Button(0,0,100,25,"Back"), "back");
		// Other setup stuff
		pauseMenu.centerOverWindow(true);
		pauseMenu.setVisible(false);
		settingsMenu.centerOverWindow(true);
		settingsMenu.setVisible(false);
		// Add to primary containers
		GraphicsManager.getMainLayerSet().addRenderer(pauseMenu, 7);
		GraphicsManager.getMainLayerSet().addRenderer(settingsMenu, 7);
	}

	@Override
	protected void cycleState()
	{
		if (Keyboard.justReleased(VK_ESCAPE))
		{
			pauseMenu.setVisible(!pauseMenu.isVisible());
			pauseMenu.setEnabled(pauseMenu.isVisible());
		}
		if (pauseMenu.isEnabled())
		{
			if (pauseMenu.getButton("mainMenu").justReleased())
			{
				changeState(MainMenu.class);
				return;
			}
			else if (pauseMenu.getButton("settings").justReleased())
			{
				pauseMenu.setVisible(false);
				settingsMenu.setEnabled(true);
			}
			else if (pauseMenu.getButton("quit").justReleased())
			{
				changeState(null);
			}
		}
		if (settingsMenu.isEnabled())
		{
			if (settingsMenu.getButton("back").justReleased())
			{
				pauseMenu.setEnabled(true);
				settingsMenu.setVisible(false);
			}
		}
	}
	
	@Override
	protected void cleanupState()
	{
		SoundManager.stopBGM();
		GraphicsManager.clearAll();
	}
}
