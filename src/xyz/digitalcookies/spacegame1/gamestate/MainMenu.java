package xyz.digitalcookies.spacegame1.gamestate;

import java.util.concurrent.ConcurrentHashMap;

import xyz.digitalcookies.objective.gamestate.GameState;
import xyz.digitalcookies.objective.graphics.GraphicsManager;
import xyz.digitalcookies.objective.graphics.RendererPanel.RelativePosition;
import xyz.digitalcookies.objective.input.gui.Button;
import xyz.digitalcookies.objective.input.gui.GUIPanel;
import xyz.digitalcookies.objective.sound.SoundManager;
import xyz.digitalcookies.spacegame1.TitleRenderer;

//import static java.awt.event.KeyEvent.*;
//import static java.awt.event.MouseEvent.*;

/** The main menu shown on game startup. Gives access to the play modes,
 * general game settings, etc.
 * @author Bryan Charles Bettis
 */
public class MainMenu extends GameState
{
	/** The top of the main menu, has options to change to different play
	 * modes, get to the general settings menu, etc.
	 */
	private GUIPanel topMenu;
	/** The settings menu for changing general (gameplay independent)
	 * settings.
	 */
	private GUIPanel settingsMenu;
	
	@Override
	public void setupState(ConcurrentHashMap<String, Object> args)
	{
		// Create all instances
		topMenu = new GUIPanel(0,0);
		settingsMenu = new GUIPanel(0,0);
		// Setup stuff
		settingsMenu.setVisible(false);
		// Add to secondary containers
		topMenu.addRenderer(new Button(0,0,100,25,"Play"), "playOriginal");
		topMenu.addRenderer(new Button(0,0,100,25,"Settings"), "toSettings", RelativePosition.BELOW, "playOriginal");
		topMenu.addRenderer(new Button(0,0,100,25,"Quit"), "quitButton", RelativePosition.BELOW, "toSettings");
		topMenu.addRenderer(new TitleRenderer(), "title", RelativePosition.ABOVE, "playOriginal");
		settingsMenu.addRenderer(new Button(0,0,100,25,"Back"), "back");
		// More setup stuff
		topMenu.centerOverWindow(true);
		settingsMenu.centerOverWindow(true);
		// Add to primary containers
		GraphicsManager.getMainLayerSet().addRenderer(topMenu, 7);
		GraphicsManager.getMainLayerSet().addRenderer(settingsMenu, 7);
//		GraphicsManager.getMainLayerSet().addRenderer(new TestRenderer(), 7);
	}

	@Override
	public void cycleState()
	{
		if (topMenu.isVisible())
		{
			if (topMenu.getButton("playOriginal").justReleased())
			{
				changeState(OriginalMode.class);
				return;
			}
			else if (topMenu.getButton("toSettings").justReleased())
			{
				settingsMenu.setEnabled(true);
				topMenu.setVisible(false);
			}
			else if (topMenu.getButton("quitButton").justReleased())
			{
				changeState(null);
				return;
			}
		}
		if (settingsMenu.isEnabled())
		{
			if (settingsMenu.getButton("back").justReleased())
			{
				topMenu.setEnabled(true);
				settingsMenu.setVisible(false);
			}
		}
	}

	@Override
	public void cleanupState()
	{
		SoundManager.stopBGM();
		GraphicsManager.clearAll();
	}
}
