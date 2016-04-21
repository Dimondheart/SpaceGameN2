package xyz.digitalcookies.spacegame1.gamestate;

import java.util.concurrent.ConcurrentHashMap;

import xyz.digitalcookies.objective.graphics.GraphicsManager;
import xyz.digitalcookies.objective.graphics.RendererPanel.RelativePosition;
import xyz.digitalcookies.objective.input.Keyboard;
import xyz.digitalcookies.objective.input.gui.Button;
import xyz.digitalcookies.objective.input.gui.GUIPanel;
import xyz.digitalcookies.objective.scene.SceneUpdateEvent;
import xyz.digitalcookies.objective.sound.SoundManager;
import xyz.digitalcookies.spacegame1.GalaxyRegionScene;

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
	/** A scene for testing/development purposes. */
	private GalaxyRegionScene testScene;
	
	@Override
	protected void setupState(ConcurrentHashMap<String, Object> setupArgs)
	{
		// Basic setup stuff
		SoundManager.playBGM("Into_the_Unknown.wav", SoundManager.BGMTransition.IMMEDIATE);
		// Make instances
		pauseMenu = new GUIPanel(0,0);
		settingsMenu = new GUIPanel(0,0);
		testScene = new GalaxyRegionScene();
		// Add to secondary containers
		pauseMenu.addRenderer(new Button(0,0,100,25,"Quit to Main Menu"), "mainMenu");
		pauseMenu.addRenderer(new Button(0,0,100,25,"Settings"), "settings", RelativePosition.ABOVE, "mainMenu");
		pauseMenu.addRenderer(new Button(0,0,100,25,"Quit"), "quit", RelativePosition.BELOW, "mainMenu");
		settingsMenu.addRenderer(new Button(0,0,100,25,"Back"), "back");
		// Other setup stuff
		pauseMenu.centerOverWindow(true);
		pauseMenu.setVisible(false);
		pauseMenu.setEnabled(false);
		settingsMenu.centerOverWindow(true);
		settingsMenu.setVisible(false);
		settingsMenu.setEnabled(false);
		// Add to primary containers
		GraphicsManager.getMainLayerSet().addRenderer(pauseMenu, 7);
		GraphicsManager.getMainLayerSet().addRenderer(settingsMenu, 7);
		GraphicsManager.getMainLayerSet().addRenderer(testScene, 4);
		// Finalize states before starting
		testScene.setUpdating(true);
		testScene.setRendering(true);
	}

	@Override
	protected void cycleState()
	{
		// Handle pause menu if open
		if (pauseMenu.isEnabled())
		{
			// Return to main menu
			if (pauseMenu.getButton("mainMenu").justReleased())
			{
				changeState(MainMenu.class);
				return;
			}
			// Go to settings menu
			else if (pauseMenu.getButton("settings").justReleased())
			{
				pauseMenu.setVisible(false);
				pauseMenu.setEnabled(false);
				settingsMenu.setEnabled(true);
				settingsMenu.setVisible(true);
			}
			// Quit game
			else if (pauseMenu.getButton("quit").justReleased())
			{
				changeState(null);
			}
			// Disable pause menu
			else if (Keyboard.justReleased(VK_ESCAPE))
			{
				pauseMenu.setVisible(false);
				pauseMenu.setEnabled(false);
			}
		}
		// Handle settings menu if open
		else if (settingsMenu.isEnabled())
		{
			// Go back to pause menu
			if (
				settingsMenu.getButton("back").justReleased() ||
				Keyboard.justReleased(VK_ESCAPE)
				)
			{
				pauseMenu.setEnabled(true);
				pauseMenu.setVisible(true);
				settingsMenu.setVisible(false);
				settingsMenu.setEnabled(false);
			}
		}
		// Enable pause menu
		else if (Keyboard.justReleased(VK_ESCAPE))
		{
			pauseMenu.setEnabled(true);
			pauseMenu.setVisible(true);
		}
		// If either of the menus are open, pause the test scene
		if (pauseMenu.isEnabled() || settingsMenu.isEnabled())
		{
			testScene.setUpdating(false);
		}
		// Resume the test scene
		else
		{
			testScene.setUpdating(true);
		}
		// Update the test scene
		testScene.updateScene(new SceneUpdateEvent());
	}
	
	@Override
	protected void cleanupState()
	{
		SoundManager.stopBGM();
		GraphicsManager.clearAll();
	}
}