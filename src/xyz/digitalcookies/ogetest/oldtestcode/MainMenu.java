package xyz.digitalcookies.ogetest.oldtestcode;

import java.awt.Color;
import java.awt.Font;
import java.util.concurrent.ConcurrentHashMap;

import xyz.digitalcookies.objective.entity.Entity;
import xyz.digitalcookies.objective.entity.EntityContainer;
import xyz.digitalcookies.objective.entity.EntityUpdateEvent;
import xyz.digitalcookies.objective.gamestate.GameState;
import xyz.digitalcookies.objective.graphics.GraphicsManager;
import xyz.digitalcookies.objective.input.InputManager;
import xyz.digitalcookies.objective.input.gui.Button;
import xyz.digitalcookies.objective.input.gui.GUIPanel;

//import static java.awt.event.KeyEvent.*;
//import static java.awt.event.MouseEvent.*;

/** The main menu and default game state.
 * @author Bryan Charles Bettis
 */
@SuppressWarnings("javadoc")
public class MainMenu extends GameState
{
	private Button startTestBtn;
	private Button startSampleBtn;
	private Button quitBtn;
	private GUIPanel buttonPanel;
	private GUIPanelTester gpt;
	private EntityContainer entities;
	private Repulsor tokenRep;
	
	public MainMenu()
	{
		Font buttonFont = new Font("Serif", Font.ITALIC, 16);
		startTestBtn = new Button(150, 100, 105, 30, "Old Test Mode", buttonFont);
		startSampleBtn = new Button(150, 150, 105, 50, "New Play Mode", buttonFont);
		quitBtn = new Button(150, 200, 105, 30);
		quitBtn.setBGColor(null);
		// Alternate way to set text & font
		quitBtn.setText("Click to Quit");
		quitBtn.setFont(buttonFont);
		buttonPanel = new GUIPanel(150, 100);
		buttonPanel.addGUIObject(startTestBtn, "playMode1Btn");
		buttonPanel.addGUIObject(startSampleBtn, "playMode2Btn", GUIPanel.RelativePosition.RIGHT_OF, "playMode1Btn");
		buttonPanel.addGUIObject(quitBtn, "quitBtn", GUIPanel.RelativePosition.BELOW, "playMode1Btn");
		buttonPanel.addGUIObject(
				new Button(0, 0, 80, 30, "Test Button"),
				"testBtn",
				GUIPanel.RelativePosition.RIGHT_OF,
				"quitBtn"
				);
		buttonPanel.addGUIObject(
				new Button(0, 0, 95, 30, "Click to Remove"),
				"testBtn2",
				GUIPanel.RelativePosition.LEFT_OF,
				"quitBtn"
				);
		buttonPanel.addGUIObject(
				new Button(0, 0, 80, 30, "Test Button 3"),
				"testBtn3",
				GUIPanel.RelativePosition.ABOVE,
				startTestBtn
				);
//		buttonPanel.addGUIObj(
//				new Placeholder(0,0,100,25),
//				"testPH",
//				GUIPanel.RelativePosition.BELOW,
//				startTestBtn
//				);
		gpt= new GUIPanelTester(buttonPanel);
		buttonPanel.setBGColor(Color.blue);
		tokenRep = new Repulsor();
		tokenRep.getBody().setPos(200, 200);
		tokenRep.getBody().setVector(1.2, -0.6);
		entities = new EntityContainer();
	}
	
	@Override
	public void setupState(ConcurrentHashMap<String, Object> args)
	{
		// Testing frame animator image dimensioning setup
//		GfxManager.getMainLayerSet().addRenderer(new game.FrameAnimatorUT1(), 0);
		GraphicsManager.getMainLayerSet().addRenderer(buttonPanel, 9);
		GraphicsManager.getMainLayerSet().addRenderer(gpt, 9);
//		GfxManager.getMainLayerSet().addRenderer(tokenEntity, 1);
		entities.addEntity(new Enemy(100, 100, -1.3, 1.6, 200));
		entities.addEntity(tokenRep);
		entities.setCycleRemoveIf(
				(Entity e)->{
					if (e instanceof Enemy && ((Enemy) e).finishedDying())
					{
						e.destroy();
						return true;
					}
					else if (e instanceof Repulsor && ((Repulsor) e).getHealth() <= 0)
					{
						e.destroy();
						return true;
					}
					return false;
				}
				);
		GraphicsManager.getMainLayerSet().addRenderer(entities, 1);
	}

	@Override
	public void cycleState()
	{
		System.out.println(getClock().getTimeMS());
		if (entities.numEntities() <= 3)
		{
			entities.addEntity(new Enemy(100, 100, -1.3, 1.6, 200));
			entities.addEntity(new Repulsor(100, 100, -1.3, 1.6, 50));
		}
		EntityUpdateEvent event = new EntityUpdateEvent();
		event.setEntities(entities);
		entities.updateEntities(event);
		if (((Button) buttonPanel.getGUIObject("playMode1Btn")).justReleased())
		{
			changeState(SamplePlay.class);
			return;
		}
		else if (startSampleBtn.justReleased())
		{
			changeState(SamplePlay2.class);
			return;
		}
		else if (quitBtn.justReleased())
		{
			InputManager.quit();
			return;
		}
		if (buttonPanel.containsGUIObject("testBtn2"))
		{
			Button testBtn2 = (Button) buttonPanel.getGUIObject("testBtn2");
			if (testBtn2.justReleased())
			{
				buttonPanel.removeGUIObject("testBtn2");
			}
		}
		gpt.update();
	}

	@Override
	public void cleanupState()
	{
		buttonPanel.destroy();
		gpt.destroy();
		entities.destroy();
	}
}
