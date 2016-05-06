package xyz.digitalcookies.spacegame1.gamestate;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import xyz.digitalcookies.objective.gamestate.GameState;
import xyz.digitalcookies.objective.graphics.GraphicsManager;
import xyz.digitalcookies.objective.graphics.ImageDrawer;
import xyz.digitalcookies.objective.graphics.RenderEvent;
import xyz.digitalcookies.objective.graphics.Renderer;
import xyz.digitalcookies.objective.input.Mouse;
import xyz.digitalcookies.spacegame1.scene.RegionData;

import static java.awt.event.MouseEvent.*;

/** Used to test some map generation ideas.
 * @author Bryan Charles Bettis
 */
public class MapTest extends GameState implements Renderer
{
	/** Used to select random numbers. */
	Random rand;
	
	@Override
	protected void setupState(ConcurrentHashMap<String, Object> setupArgs)
	{
		GraphicsManager.getMainLayerSet().addRenderer(this, 4);
		rand = new Random();
	}

	@Override
	protected void cycleState()
	{
		if (!Mouse.justReleased(BUTTON1))
		{
			return;
		}
		int r = 11;
		int cx = Mouse.getUnpolledX();
		int cy = Mouse.getUnpolledY();
		int x1 = cx - r;
		int y1 = cy - r;
		int x2 = cx + r;
		int y2 = cy + r;
		BufferedImage img =
				GraphicsManager.getResManager().getRes("galaxy_cloud_1.jpg");
		if (x1 < 0)
		{
			x1 = 0;
		}
		if (y1 < 0)
		{
			y1 = 0;
		}
		if (x2 >= img.getWidth())
		{
			x2 = img.getWidth()-1;
		}
		if (y2 >= img.getHeight())
		{
			y2 = img.getHeight()-1;
		}
		BufferedImage sub = img.getSubimage(x1, y1, x2-x1, y2-y1);
		int sum = 0;
		int num = sub.getWidth()*sub.getHeight();
		for (int x = 0; x < sub.getWidth(); ++x)
		{
			for (int y = 0; y < sub.getHeight(); ++y)
			{
				Color c = new Color(sub.getRGB(x, y));
				sum += c.getRed() + c.getGreen() + c.getBlue();
			}
		}
		int avg = (int)(sum/(double)num);
		int ra = (int)((1.0 + rand.nextGaussian()/2)*avg);
		System.out.print("Mean: ");
		System.out.print(avg);
		System.out.print(", Randomized (Normal Distribution): ");
		System.out.println(ra);
		RegionData regData = new RegionData();
		regData.setResAmt(ra);
		getNewStateArgs().put("regionData", regData);
		changeState(OriginalMode.class);
	}
	
	@Override
	protected void cleanupState()
	{
	}
	
	@Override
	public void render(RenderEvent event)
	{
		ImageDrawer.drawGraphic(event.getGC(), "galaxy_cloud_1.jpg", 0, 0);
	}
}
