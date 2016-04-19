package xyz.digitalcookies.ogetest;

import xyz.digitalcookies.objective.scene.SceneUpdateEvent;
import xyz.digitalcookies.objective.scene.Scene;

import java.awt.Color;
import java.awt.Graphics2D;

import xyz.digitalcookies.objective.graphics.GraphicsManager;
import xyz.digitalcookies.objective.graphics.RenderEvent;
import xyz.digitalcookies.objective.graphics.Renderer;
import xyz.digitalcookies.objective.input.Mouse;

/** Main scene type, multiple of these scenes could be active at once and
 * up to 2 could be rendering at once (interwoven rendering.)
 * @author Bryan Charles Bettis
 */
public class GalaxyRegionScene extends Scene implements Renderer
{
	private SpaceBody testBody;
	private SpaceBody testBody2;
	private Circle targetPos;
	private double[][] targets = 
		{
			{600, -600},
			{850, -240},
			{1000, -800}
		};
	private int currTarget = 0;
	private double lastUpdate = 0;
	private PlaneVector camera;
	
	public GalaxyRegionScene()
	{
		camera = new PlaneVector();
		testBody = new SpaceBody();
		testBody2 = new SpaceBody();
		targetPos = new Circle(targets[currTarget][0], targets[currTarget][1], 24);
//		testBody.setDirection(-20.0);
		testBody.getRegion().getPosition().setVectorComp(450, -300);
		testBody.getRegion().setRadius(12);
		testBody2.getRegion().getPosition().setVectorComp(600, -400);
		testBody2.getRegion().setRadius(12);
		lastUpdate = getTimer().getTimeNano()/1000000.0/1000.0;
		camera = testBody.getRegion().getPosition();
	}
	
	@Override
	public void updateScene(SceneUpdateEvent event)
	{
		double currTime = getTimer().getTimeNano()/1000000.0/1000.0;
		double elapsed = currTime - lastUpdate;
		if (isPaused())
		{
		}
		else
		{
			updateTB(testBody, elapsed);
			updateTB(testBody2, elapsed);
		}
		lastUpdate = currTime;
	}
	
	@Override
	public void render(RenderEvent event)
	{
		Graphics2D nc = (Graphics2D) event.getContext().create();
		nc.translate(
				(int) (-camera.getX()+GraphicsManager.getMainLayerSet().getWidth()/2),
				(int) (camera.getY()+GraphicsManager.getMainLayerSet().getHeight()/2)
				);
		if (isRendering())
		{
			GraphicsManager.drawGraphic(
					nc,
					"RegionBG/bg1.jpg",
					0,
					0
					);
			nc.setColor(Color.blue);
			Circle tbc = testBody.getRegion();
			nc.fillOval(
					(int) (tbc.getX()-tbc.getRadius()),
					(int) ((-tbc.getY())-tbc.getRadius()),
					(int) (tbc.getRadius()*2),
					(int) (tbc.getRadius()*2)
					);
			nc.setColor(Color.red);
			PlaneVector di3 = testBody.getAcceleration().clone();
//			di3.setMagnitude(di3.getMagnitude()*3);
			nc.drawLine(
					(int) (tbc.getX()),
					(int) (-tbc.getY()),
					(int) (tbc.getX()+di3.getX()),
					(int) (-(tbc.getY()+di3.getY()))
					);
			nc.setColor(Color.green);
			PlaneVector di = testBody.getVelocity().clone();
//			di.setMagnitude(di.getMagnitude()*3);
			nc.drawLine(
					(int) (tbc.getX()),
					(int) (-tbc.getY()),
					(int) (tbc.getX()+di.getX()),
					(int) (-(tbc.getY()+di.getY()))
					);
			nc.setColor(Color.white);
			PlaneVector di2 = testBody.getDV().clone();
			di2.setMagnitude(15);
			nc.drawLine(
					(int) (tbc.getX()),
					(int) (-tbc.getY()),
					(int) (tbc.getX()+di2.getX()),
					(int) (-(tbc.getY()+di2.getY()))
					);
		}
		nc.setColor(Color.orange);
		nc.drawOval(
				(int)(targetPos.getX()-targetPos.getRadius()),
				(int) ((-targetPos.getY())-targetPos.getRadius()),
				(int) (targetPos.getRadius()*2),
				(int) (targetPos.getRadius()*2)
				);
		/* Test 2 */
		nc.setColor(Color.yellow);
		Circle tbc2 = testBody2.getRegion();
		nc.fillOval(
				(int) (tbc2.getX()-tbc2.getRadius()),
				(int) ((-tbc2.getY())-tbc2.getRadius()),
				(int) (tbc2.getRadius()*2),
				(int) (tbc2.getRadius()*2)
				);
		nc.setColor(Color.red);
		PlaneVector di20 = testBody2.getAcceleration().clone();
//		di20.setMagnitude(di20.getMagnitude()*3);
		nc.drawLine(
				(int) (tbc2.getX()),
				(int) (-tbc2.getY()),
				(int) (tbc2.getX()+di20.getX()),
				(int) (-(tbc2.getY()+di20.getY()))
				);
		nc.setColor(Color.green);
		PlaneVector di10 = testBody.getVelocity().clone();
//		di10.setMagnitude(di10.getMagnitude()*3);
		nc.drawLine(
				(int) (tbc2.getX()),
				(int) (-tbc2.getY()),
				(int) (tbc2.getX()+di10.getX()),
				(int) (-(tbc2.getY()+di10.getY()))
				);
		nc.setColor(Color.white);
		PlaneVector di30 = testBody.getDV().clone();
		di30.setMagnitude(15);
		nc.drawLine(
				(int) (tbc2.getX()),
				(int) (-tbc2.getY()),
				(int) (tbc2.getX()+di30.getX()),
				(int) (-(tbc2.getY()+di30.getY()))
				);
	}
	
	private void updateTB(SpaceBody body, double elapsed)
	{
		if (body.getVelocity().getMagnitude() > 60)
		{
			body.getVelocity().setMagnitude(60);
		}
		if (targetPos.intersects(body.getRegion()))
		{
			currTarget += 1;
			if (currTarget >= targets.length)
			{
				currTarget = 0;
			}
			targetPos.getPosition().setVectorComp(
					targets[currTarget][0],
					targets[currTarget][1]
					);
		}
		PlaneVector toTarget = new PlaneVector(
				targetPos.getX()-body.getRegion().getX(),
				targetPos.getY()-body.getRegion().getY(),
				true
				);
		body.getAcceleration().setDirection(toTarget.getDirectionDeg());
		body.getAcceleration().setMagnitude(18);
//		body.getAcceleration().setVectorComp(
//				Mouse.getUnpolledX()-GraphicsManager.getMainLayerSet().getWidth()/2,
//				-(Mouse.getUnpolledY()-GraphicsManager.getMainLayerSet().getHeight()/2)
//				);
		PlaneVector accelTotal = new PlaneVector();
		accelTotal.setDirection(toTarget.getDirectionDeg());
		accelTotal.setMagnitude(10);
		double pm = body.getAcceleration().getMagnitude();
		PlaneVector accelOffset = new PlaneVector();
		accelOffset.setMagnitude(accelTotal.getMagnitude()*2.0/3.0);
		accelOffset.setDirection(
				2*accelTotal.getDirectionDeg()
				- body.getVelocity().getDirectionDeg()
				);
		body.getAcceleration().setVectorComp(
				accelTotal.getX() + accelOffset.getX(),
				accelTotal.getY() + accelOffset.getY()
				);
		body.getAcceleration().setMagnitude(18);
		double btw = 
				body.getAcceleration().getDirectionDeg()
				- body.getDV().getDirectionDeg();
		if ((0 < btw && btw < 180) || (btw < -180))
		{
			body.setRotationVector(45);
		}
		else if ((-180 < btw && btw < 0) || (btw >= 180))
		{
			body.setRotationVector(-45);
		}
		else
		{
			body.setRotationVector(0);
		}
		body.update(elapsed);
	}
}
