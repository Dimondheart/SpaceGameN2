package xyz.digitalcookies.ogetest;

import java.util.concurrent.ConcurrentHashMap;

import xyz.digitalcookies.objective.Settings;
import xyz.digitalcookies.objective.scene.SceneUpdateEvent;
import xyz.digitalcookies.objective.graphics.GraphicsManager;
import xyz.digitalcookies.objective.input.InputManager;
import xyz.digitalcookies.objective.sound.SoundManager;

import static java.awt.event.KeyEvent.*;
//import static java.awt.event.MouseEvent.*;

/** The normal/original play mode. */
public class MainPlayMode extends xyz.digitalcookies.objective.gamestate.GameState
{
	private boolean scenePaused = false;
	private boolean pauseMenuActive = false;
	private Galaxy galaxy;
	private GalacticRegionScene region;
	private Player player;
	
	@Override
	protected void setupState(ConcurrentHashMap<String, Object> setupArgs)
	{
		Settings.setSetting(Settings.INVERT_SCROLL_WHEEL, true);
		SoundManager.playBGM("Into_the_Unknown.wav", SoundManager.BGMTransition.IMMEDIATE);
		galaxy = new Galaxy();
		region = new GalacticRegionScene(galaxy.getInitialRegion());
		player = new Player(region);
		region.setPlayer(player);
		GraphicsManager.getMainLayerSet().addRenderer(region, 2);
		region.setPaused(false);
		region.setRendering(true);
	}

	@Override
	protected void cycleState()
	{
		if (InputManager.getKB().justReleased(VK_ESCAPE))
		{
			pauseMenuActive = !pauseMenuActive;
		}
		if (InputManager.getKB().justReleased(VK_CONTROL) && !pauseMenuActive)
		{
			scenePaused = !scenePaused;
		}
		if (InputManager.getKB().justReleased(VK_1))
		{
			Settings.setSetting(Settings.MASTER_VOLUME, 10);
		}
		else if (InputManager.getKB().justReleased(VK_2))
		{
			Settings.setSetting(Settings.MASTER_VOLUME, 20);
		}
		else if (InputManager.getKB().justReleased(VK_3))
		{
			Settings.setSetting(Settings.MASTER_VOLUME, 30);
		}
		else if (InputManager.getKB().justReleased(VK_4))
		{
			Settings.setSetting(Settings.MASTER_VOLUME, 40);
		}
		else if (InputManager.getKB().justReleased(VK_5))
		{
			Settings.setSetting(Settings.MASTER_VOLUME, 50);
		}
		else if (InputManager.getKB().justReleased(VK_6))
		{
			Settings.setSetting(Settings.MASTER_VOLUME, 60);
		}
		else if (InputManager.getKB().justReleased(VK_7))
		{
			Settings.setSetting(Settings.MASTER_VOLUME, 70);
		}
		else if (InputManager.getKB().justReleased(VK_8))
		{
			Settings.setSetting(Settings.MASTER_VOLUME, 80);
		}
		else if (InputManager.getKB().justReleased(VK_9))
		{
			Settings.setSetting(Settings.MASTER_VOLUME, 90);
		}
		else if (InputManager.getKB().justReleased(VK_0))
		{
			Settings.setSetting(Settings.MASTER_VOLUME, 0);
		}
		else if (InputManager.getKB().justReleased(VK_TAB))
		{
			Settings.setSetting(Settings.MASTER_VOLUME, 100);
		}
		region.setPaused(pauseMenuActive || scenePaused);
		region.updateScene(new SceneUpdateEvent());
	}
	
	@Override
	protected void cleanupState()
	{
		region.destroy();
	}
}
