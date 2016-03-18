package xyz.digitalcookies.ogetest;

import java.util.concurrent.ConcurrentHashMap;

import xyz.digitalcookies.objective.Settings;
import xyz.digitalcookies.objective.entity.SceneUpdateEvent;
import xyz.digitalcookies.objective.graphics.GraphicsManager;
import xyz.digitalcookies.objective.sound.SoundManager;

/** The normal/original play mode. */
public class MainPlayMode extends xyz.digitalcookies.objective.gamestate.GameState
{
	private boolean paused = false;
	private Galaxy galaxy;
	private GalacticRegionScene region;
	private Player player;
	
	@Override
	protected void setupState(ConcurrentHashMap<String, Object> setupArgs)
	{
		Settings.setSetting(Settings.INVERT_SCROLL_WHEEL, true);
		SoundManager.playBGM("Into_the_Unknown.wav", SoundManager.BGMTransition.IMMEDIATE);
		galaxy = new Galaxy();
		region = new GalacticRegionScene();
		player = new Player(region);
		region.changeRegionData(galaxy.getInitialRegion());
		region.setPlayer(player);
		GraphicsManager.getMainLayerSet().addRenderer(region, 2);
		region.setActive(true);
	}

	@Override
	protected void cycleState()
	{
		region.updateScene(new SceneUpdateEvent());
	}
	
	@Override
	protected void cleanupState()
	{
		region.destroy();
	}
}
