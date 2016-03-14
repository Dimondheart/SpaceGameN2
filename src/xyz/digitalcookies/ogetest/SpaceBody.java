package xyz.digitalcookies.ogetest;

import xyz.digitalcookies.objective.entity.Scene;
import xyz.digitalcookies.objective.entity.entitymodule.Body;
import xyz.digitalcookies.objective.graphics.GraphicsManager;

/** Basic body for all bodies in space. */
public class SpaceBody extends Body
{
	private GalacticRegionScene scene;
	private double x;
	private double y;
	private double rotation;
	private double rv;
	private double vm;
	private double vd;
	private double am;
	private double ad;
	protected long lastUpdate;
	
	public SpaceBody(GalacticRegionScene scene)
	{
		setPos(0, 0);
		setVector(0, 0);
		clearAccel();
		setScene(scene);
	}
	
	@Override
	public boolean setScene(Scene scene)
	{
		if (scene instanceof GalacticRegionScene)
		{
			this.scene = (GalacticRegionScene) scene;
			return true;
		}
		return false;
	}
	
	public void setPos(double x, double y)
	{
		setX(x);
		setY(y);
	}
	
	public double getX()
	{
		return x;
	}
	
	public int getScreenX()
	{
		System.out.println(scene.getOffsetX());
		return (int) (getX() + scene.getOffsetX());
	}
	
	public void setX(double x)
	{
		this.x = x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public int getScreenY()
	{
		return (int) ((int) GraphicsManager.getMainLayerSet().getLayerSetHeight() - (getY() + scene.getOffsetY()));
	}
	
	public void setY(double y)
	{
		this.y = y;
	}
	
	public double getRotation()
	{
		return rotation;
	}
	
	public void setRotation(double r)
	{
		rotation = r % 360;
		if (rotation < 0)
		{
			rotation = 360 + rotation;
		}
	}
	
	public double getRotationVector()
	{
		return rv;
	}
	
	public void setRotationVector(double rv)
	{
		this.rv = rv;
	}
	
	public void setVector(double vm, double direction)
	{
		this.vm = vm;
		this.vd = direction;
	}
	
	public void applyVector(double vm)
	{
		setVector(vm, rotation);
	}
	
	public double getVectorX()
	{
		return 0;
	}
	
	public double getVectorY()
	{
		return 0;
	}
	
	public void setAccel(double am, double direction)
	{
		this.am = am;
		ad = direction;
	}
	
	public void applyAccel(double am)
	{
		setAccel(am, rotation);
	}
	
	public void clearAccel()
	{
		am = 0;
	}
	
	public double getAccelX()
	{
		return 0;
	}
	
	public double getAccelY()
	{
		return 0;
	}
	
	public void update()
	{
		long currTime = scene.getTimer().getTimeNano();
		double elapsed = (currTime - lastUpdate)/1000000.0/1000.0;
		setX(getX() + getVectorX()*elapsed);
		setY(getY() + getVectorY()*elapsed);
		setRotation(getRotation() + getRotationVector()*elapsed);
		lastUpdate = currTime;
	}
}
